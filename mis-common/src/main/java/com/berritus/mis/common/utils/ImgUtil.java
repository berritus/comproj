package com.berritus.mis.common.utils;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

/**
 * @Copyright:
 * @Description:
 * @Author: Qin Guihe
 * @Create: 2020-03-13
 */
public class ImgUtil {
	private static final Logger logger = LoggerFactory.getLogger(ImgUtil.class);

	public static void imgToPdf(String imageFolderPath, String pdfPath) {
		long t1 = System.currentTimeMillis();
		long t2 = System.currentTimeMillis();

		Document doc = null;
		try {
			// 图片地址
			String imagePath = null;
			// 输入流
			FileOutputStream fos = new FileOutputStream(pdfPath);
			// 创建文档
			doc = new Document(null, 0, 0, 0, 0);
			// 写入PDF文档
			PdfWriter.getInstance(doc, fos);
			// 读取图片流
			BufferedImage img = null;
			// 实例化图片
			Image image = null;
			// 获取图片文件夹对象
			File file = new File(imageFolderPath);
			File[] files = file.listFiles();
			// 循环获取图片文件夹内的图片
			for (File file1 : files) {
				if (file1.getName().endsWith(".png") || file1.getName().endsWith(".jpg")
						|| file1.getName().endsWith(".gif")
						|| file1.getName().endsWith(".jpeg")
						|| file1.getName().endsWith(".tif")) {
					imagePath = imageFolderPath + file1.getName();
					logger.info("合并：" + file1.getName());
					// 读取图片流
					img = ImageIO.read(new File(imagePath));
					doc.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));
					// 实例化图片
					image = Image.getInstance(imagePath);
					// 添加图片到文档
					doc.open();
					doc.add(image);
				}
			}
		} catch (Exception e) {
			logger.error("合并异常：{}", e);
		} finally {
			if (doc != null && doc.isOpen()) {
				// 关闭文档
				doc.close();
			}
			t2 = System.currentTimeMillis();
			logger.info("合并总耗时 times= {} ms", (t2 - t1));
		}
	}
}
