package com.berritus.mis.study.english;

import com.alibaba.dubbo.common.utils.StringUtils;
import org.hibernate.validator.internal.util.StringHelper;
import org.junit.Test;

import java.io.*;
import java.util.List;

public class SdudyEnglish {
    @Test
    public void test1() {
        String pathFile = "F:\\spring-proj\\字幕\\狂暴巨兽.ass";
        File file = new File(pathFile);
        String fileEncode = getCode(pathFile);
        readFile(file, "F:\\spring-proj\\字幕\\狂暴巨兽.txt", fileEncode);
    }

    public void readFile(File infile, String outfile, String fileEncode) {
        try {
            String encoding = fileEncode;
            if(StringUtils.isEmpty(fileEncode)){
                encoding = "GBK";
            }

            if (infile.isFile() && infile.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(infile),
                        encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);

                String lineTxt = null;
                FileWriter fw = new FileWriter(outfile, true);
                String str = "";

                while ((lineTxt = bufferedReader.readLine()) != null) {
                    int pos = lineTxt.indexOf(",,");
                    if (pos == -1) {
                        continue;
                    }

                    String strTemp = lineTxt.substring(pos + 2, lineTxt.length());
                    int poso = strTemp.indexOf(",,");
                    while(poso != -1){
                        strTemp = strTemp.substring(poso + 2, strTemp.length());
                        poso = strTemp.indexOf(",,");
                    }

                    pos = strTemp.indexOf("{");
                    while(pos != -1){
                        int pos2 = strTemp.indexOf("}");
                        if(pos2 == -1){
                            break;
                        }

                        String part1 = strTemp.substring(pos, pos2+ 1);
                        strTemp = strTemp.replace(part1, "");
                        pos = strTemp.indexOf("{");
                    }

                    strTemp = strTemp + "\n";
                    strTemp = strTemp.replace("\\N", "  ");
                    strTemp = strTemp.replace("*", " ");
                    fw.write(strTemp);
                }

                fw.flush();
                read.close();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public String getCode(String path) {
        try {
            InputStream inputStream = new FileInputStream(path);
            byte[] head = new byte[3];
            inputStream.read(head);
            String code = "gb2312"; // 或GBK
            if (head[0] == -1 && head[1] == -2)
                code = "UTF-16";
            else if (head[0] == -2 && head[1] == -1)
                code = "Unicode";
            else if (head[0] == -17 && head[1] == -69 && head[2] == -65)
                code = "UTF-8";
            inputStream.close();
            return code;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
