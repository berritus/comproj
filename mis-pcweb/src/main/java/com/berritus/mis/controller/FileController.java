package com.berritus.mis.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.berritus.mis.bean.mybatis.SysFiles;
import com.berritus.mis.dubbo.api.QrySysService;
import com.berritus.mis.dubbo.api.SysService;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.gridfs.GridFSDBFile;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    private GridFsTemplate gridFsTemplate;
    @Autowired
    private MongoDbFactory mongoDbFactory;
    @Reference(timeout = 30000)
    private SysService sysService;
    @Reference(timeout = 30000)
    private QrySysService qrySysService;



    //http://localhost:8083/upload
    @RequestMapping("/upload")
    public String fileUpload(Model model, MultipartFile file, HttpServletRequest request){
        try {
            //String newTitle = request.getParameter("newTitle");

            String fileName = file.getOriginalFilename();
            String type = fileName.indexOf(".") != -1
                    ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;
            if (type != null) {
                Part part = request.getPart("file");
                InputStream ins = part.getInputStream();
                String fileType = part.getContentType();
                ObjectId objectId = gridFsTemplate.store(ins, fileName, fileType);

                SysFiles record = new SysFiles();
                record.setContentType(fileType);
                record.setFileName(fileName);
                record.setMongoFileId(objectId.toString());
                record.setUseType(1);
                record.setOwner(100003);
                int fileId = sysService.insertSysFiles(record);
                model.addAttribute("message", "文件上传成功, ID:" + fileId);
            } else {
                model.addAttribute("message", "文件类型为空");
            }
        } catch (IllegalStateException | IOException e) {
            model.addAttribute("message", e.getMessage());
        }catch (ServletException ee){
            model.addAttribute("message", ee.getMessage());
        }

        return "upload";
    }

//    public MongoDatabase mongoDatabase() throws Exception{
//        MongoClient mongoClient = new MongoClient("localhost", 27017);
//        return mongoClient.getDatabase("img");
//    }

    //http://localhost:8083/file/download?fileId=1000008
    @RequestMapping("/download")
    public void downloadFile(Model model, Integer fileId, HttpServletRequest request, HttpServletResponse response){
        try{
            SysFiles sysFiles = qrySysService.qrySysFiles(fileId);
            if(sysFiles == null){
                return;
            }

            Query query = Query.query(Criteria.where("_id").is(sysFiles.getMongoFileId()));
            GridFSFile gridFSFile = gridFsTemplate.findOne(query);
            if(gridFSFile == null){
                return;
            }
            GridFsResource gridFsResource = new GridFsResource(gridFSFile, GridFSBuckets.create(mongoDbFactory.getDb())
                    .openDownloadStream(gridFSFile.getObjectId()));

            String fileName = gridFSFile.getFilename().replace(",", "");

            //处理中文文件名乱码
            if (request.getHeader("User-Agent").toUpperCase().contains("MSIE") ||
                    request.getHeader("User-Agent").toUpperCase().contains("TRIDENT")
                    || request.getHeader("User-Agent").toUpperCase().contains("EDGE")) {
                fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
            } else {
                //非IE浏览器的处理：
                fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            }

            model.addAttribute("message", "文件下载成功");
            response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
            IOUtils.copy(gridFsResource.getInputStream(),response.getOutputStream());
        }catch (Exception e){
            model.addAttribute("message", "文件下载失败");
        }
        //return "upload";
    }

    //http://localhost:8083/file/delete?fileId=1000007
    @RequestMapping("/delete")
    public String deleteFile(Model model, Integer fileId){
        try{
            SysFiles sysFiles = qrySysService.qrySysFiles(fileId);
            if(sysFiles == null){
                throw new RuntimeException("文件不存在");
            }

            Query query = Query.query(Criteria.where("_id").is(sysFiles.getMongoFileId()));
            GridFSFile gridFSFile = gridFsTemplate.findOne(query);
            if(gridFSFile == null){
                throw new RuntimeException("文件不存在");
            }

            gridFsTemplate.delete(query);

            try{
                sysService.deleteSysFiles(fileId);
            }catch (Exception e){

            }

            model.addAttribute("message", "文件删除成功");
        }catch (Exception e){
            model.addAttribute("message", "文件删除失败，" + e.getMessage());
        }

        return "upload";
    }
}
