package com.minstone.common.utils;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;

import com.minstone.oa.common.thread.OaExecutors;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Copyright:
 * @Description:
 * @Author: Qin Guihe
 * @Create: 2020-03-11
 */
public class PDFUtils {
	private static final Logger logger = LoggerFactory.getLogger(PDFUtils.class);
	public static final float DEFAULT_DPI = 105;
	private static final int THREAD_PAGE_COUNT = 40;
	/**
	 * 转换全部的pdf
	 * @param fileAddress 文件地址
	 * @param filename PDF文件名
	 * @param type 图片类型
	 */
	public static void pdf2png(String fileAddress,String filename,String type) {
		// 将pdf装图片 并且自定义图片得格式大小
		File file = new File(fileAddress+"\\"+filename+".pdf");
		try {
			PDDocument doc = PDDocument.load(file);
			PDFRenderer renderer = new PDFRenderer(doc);
			int pageCount = doc.getNumberOfPages();
			for (int i = 0; i < pageCount; i++) {
				BufferedImage image = renderer.renderImageWithDPI(i, 300); // Windows native DPI
				BufferedImage srcImage = resizeBufferedImage(image,160, 220,false);//产生缩略图
				ImageIO.write(srcImage, type, new File(fileAddress+"\\"+filename+"_"+(i+1)+"."+type));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 *自由确定起始页和终止页
	 * @param fileAddress 文件地址
	 * @param filename pdf文件名
	 * @param indexOfStart 开始页  开始转换的页码，从0开始
	 * @param indexOfEnd 结束页  停止转换的页码，-1为全部
	 * @param type 图片类型
	 */
	public static void pdf2png(String fileAddress,String filename,int indexOfStart,int indexOfEnd,String type) {
		// 将pdf装图片 并且自定义图片得格式大小
		File file = new File(fileAddress+"\\"+filename+".pdf");
		try {
			PDDocument doc = PDDocument.load(file);
			PDFRenderer renderer = new PDFRenderer(doc);
			int pageCount = doc.getNumberOfPages();
			for (int i = indexOfStart; i < indexOfEnd; i++) {
				BufferedImage image = renderer.renderImageWithDPI(i, 160); // Windows native DPI
				BufferedImage srcImage = resizeBufferedImage(image, 0, 0,true);//产生缩略图
				ImageIO.write(srcImage, type, new File(fileAddress+"\\"+filename+"_"+(i+1)+"."+type));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	/**
	 * 调整bufferedimage大小
	 * @param source BufferedImage 原始image
	 * @param targetW int  目标宽
	 * @param targetH int  目标高
	 * @param flag boolean 是否同比例调整
	 * @return BufferedImage  返回新image
	 */
	private static BufferedImage resizeBufferedImage(BufferedImage source, int targetW, int targetH, boolean flag) {
		int type = source.getType();
		BufferedImage target = null;
		double sx = (double) targetW / source.getWidth();
		double sy = (double) targetH / source.getHeight();
		if (flag && sx > sy) {
			sx = sy;
			targetW = (int) (sx * source.getWidth());
		} else if(flag && sx <= sy){
			sy = sx;
			targetH = (int) (sy * source.getHeight());
		}
		if (type == BufferedImage.TYPE_CUSTOM) { // handmade
			ColorModel cm = source.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			target = new BufferedImage(cm, raster, alphaPremultiplied, null);
		} else {
			target = new BufferedImage(targetW, targetH, type);
		}
		Graphics2D g = target.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
		g.dispose();
		return target;
	}

	/**
	 * 绘制水印的方法
	 * @param content
	 * @param gs
	 * @param pageRect
	 * @param watermark
	 */
	public static void  addWatermark(PdfContentByte content, PdfGState gs, Rectangle pageRect,
									 BaseFont font, String watermark) {
		try {
			float x = pageRect.getWidth() / 2.0F;
			float y = pageRect.getHeight() / 2.0F;
			content.setGState(gs);
			content.beginText();
			content.setColorFill(BaseColor.GRAY);
			content.setFontAndSize(font, 60.0F);

			content.showTextAligned(PdfContentByte.ALIGN_CENTER, watermark, x, y, 45.0F);
			content.endText();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			content = null;
			font = null;
			pageRect = null;
		}
	}

	public static void main(String[] args)throws Exception {
		//for (int i = 0; i <= 10; i++) {
			// 351 32
			pdf2png3("E:\\pdf","32","png");
			//pdf2png3old("E:\\pdf","ceshi","png");

			// pdfToImage2("E:\\pdf\\32.pdf");
		//}
	}

	/**
	 * 转换全部的pdf
	 * @param fileAddress 文件地址
	 * @param filename PDF文件名
	 * @param type 图片类型
	 */
	public static void pdf2png3(final String fileAddress,final String filename,
								final String type) throws Exception {

		long t1 = System.currentTimeMillis();
		long t2 = System.currentTimeMillis();
		// 将pdf装图片 并且自定义图片得格式大小
		File file = new File(fileAddress+"\\"+filename+".pdf");
		CountDownLatch latch = null;
		try {
			PDDocument doc = PDDocument.load(file);
			final PDFRenderer renderer = new PDFRenderer(doc);
			Integer pageCount = doc.getNumberOfPages();

			//pageCount = pageCount - 1;
			if (pageCount >= 30) {
				latch = new CountDownLatch(pageCount);
				int processNum = Runtime.getRuntime().availableProcessors();
				ExecutorService executorService = OaExecutors.newFixedThreadPool(processNum - 1);
				//ExecutorService executorService = Executors.newFixedThreadPool(10);
				for (int i = 0; i < pageCount; i++) {
					final int pageIndex = i ;
					ThreadPdf threadPdf = new ThreadPdf(renderer, doc, pageIndex, latch, fileAddress, filename, type);
					executorService.submit(threadPdf);
				}
				executorService.shutdown();
			} else {
				for (int i = 0; i < pageCount; i++) {
					final int pageIndex = i ;
					pdf2pngHandler(renderer, doc, pageIndex, fileAddress, filename, type);
				}
			}


		} catch (IOException e) {
			logger.error("异常{}", e);
		}

		if (latch != null) {
			latch.await();
		}

		t2 = System.currentTimeMillis();
		logger.info("pdf2png3 total times = {}ms", (t2 - t1));
	}

	/**
	 * @Description:
	 * @Author: Qin Guihe
	 * @Create: 2020/3/11
	 * @return:
	 */
	static class ThreadPdf implements Runnable {
		private CountDownLatch latch;
		private PDFRenderer renderer;
		private PDDocument doc;
		private int pageIndex;
		private String fileAddress;
		private String filename;
		private String type;

		public ThreadPdf(PDFRenderer renderer, PDDocument doc, int pageIndex,
						 CountDownLatch latch,String fileAddress, String filename, String type) {
			this.renderer = renderer;
			this.doc = doc;
			this.pageIndex = pageIndex;
			this.latch = latch;
			this.fileAddress = fileAddress;
			this.filename = filename;
			this.type = type;
		}

		@Override
		public void run() {
			long t1 = System.currentTimeMillis();
			long t2 = System.currentTimeMillis();
			try {
				pdf2pngHandler(renderer, doc, pageIndex, fileAddress, filename, type);
			} catch (Exception e) {
				logger.error("ThreadPdf异常{}", e);
			} finally {
				latch.countDown();
				t2 = System.currentTimeMillis();
				logger.info("thread={},total use times={}ms", Thread.currentThread().getName(), t2 - t1);
			}
		}
	}

	/**
	 * @Description:
	 * @Author: Qin Guihe
	 * @Create: 2020/3/11
	 * @return:
	 */
	private static void pdf2pngHandler(PDFRenderer renderer, PDDocument doc, int pageIndex,
									   String fileAddress,String filename, String type) throws Exception{
		long t1 = System.currentTimeMillis();
		long t2 = System.currentTimeMillis();
		long t3 = System.currentTimeMillis();
		long t4 = System.currentTimeMillis();
		// Windows native DPI
		BufferedImage image = renderer.renderImageWithDPI(pageIndex, 144, ImageType.RGB);
		t2 = System.currentTimeMillis();
		//产生缩略图
		BufferedImage srcImage = resizeBufferedImage(image,160, 220,false);
		t3 = System.currentTimeMillis();
		ImageIO.write(srcImage, type, new File(fileAddress+"\\"+filename+"_"+(pageIndex+1)+"."+type));
		t4 = System.currentTimeMillis();

		logger.info("thread={} pageIndex={},t2 - t1 use times={}ms", Thread.currentThread().getName(), pageIndex, t2 - t1);
		logger.info("thread={} pageIndex={},t3 - t2 use times={}ms", Thread.currentThread().getName(), pageIndex, t3 - t2);
		logger.info("thread={} pageIndex={},t4 - t3 use times={}ms", Thread.currentThread().getName(), pageIndex, t4 - t3);
		logger.info("thread={} pageIndex={},t4 - t1 use times={}ms", Thread.currentThread().getName(), pageIndex, t4 - t1);
	}

	public static void pdf2png3old(final String fileAddress,final String filename,final String type) {
		long t1 = System.currentTimeMillis();
		long t2 = System.currentTimeMillis();
		long t3 = System.currentTimeMillis();
		long t4 = System.currentTimeMillis();
		long t5 = System.currentTimeMillis();
		long t6 = System.currentTimeMillis();
		// 将pdf装图片 并且自定义图片得格式大小
		File file = new File(fileAddress+"\\"+filename+".pdf");
		try {
			PDDocument doc = PDDocument.load(file);
			final PDFRenderer renderer = new PDFRenderer(doc);
			Integer pageCount = doc.getNumberOfPages();

			for (int i = 0; i < pageCount; i++) {
				final int pageIndex = i ;
				try {
					// 144 105
					t3 = System.currentTimeMillis();
					BufferedImage image = renderer.renderImageWithDPI(pageIndex, 105); // Windows native DPI
					t4 = System.currentTimeMillis();
					logger.info("=============t4-t3={}", t4-t3);
					BufferedImage srcImage = resizeBufferedImage(image,160, 220,false);//产生缩略图
					t5 = System.currentTimeMillis();
					logger.info("=============t5-t4={}", t5-t4);
					ImageIO.write(srcImage, type, new File(fileAddress+"\\"+filename+"_"+(pageIndex+1)+"."+type));
					t6 = System.currentTimeMillis();
					logger.info("=============t6-t5={}", t6-t5);
				}catch (Exception e) {
					return;
				}
			}

//			ExecutorService executorService = Executors.newFixedThreadPool(10);
//			for (int i = 0; i < pageCount; i++) {
//				final int pageIndex = i ;
//				executorService.submit(new Runnable() {
//					@Override
//					public void run() {
//						try {
//							BufferedImage image = renderer.renderImageWithDPI(pageIndex, 144); // Windows native DPI
//							BufferedImage srcImage = resizeBufferedImage(image,160, 220,false);//产生缩略图
//							ImageIO.write(srcImage, type, new File(fileAddress+"\\"+filename+"_"+(pageIndex+1)+"."+type));
//						}catch (Exception e) {
//							return;
//						}
//					}
//				});
//
//			}
//			executorService.shutdown();
		} catch (IOException e) {
			e.printStackTrace();
		}

		t2 = System.currentTimeMillis();
		System.out.println("============timesss=" + (t2 - t1));
	}

	private static void pdfToImage2(String pdfPath) {
		long t1 = System.currentTimeMillis();
		long t2 = System.currentTimeMillis();
		long t3 = System.currentTimeMillis();
		long t4 = System.currentTimeMillis();
		long t5 = System.currentTimeMillis();

		try {
			if (pdfPath == null || "".equals(pdfPath) || !pdfPath.endsWith(".pdf")) {
				return;
			}

			// 图像合并使用参数
			int width = 0; // 总宽度
			int[] singleImgRGB; // 保存一张图片中的RGB数据
			int shiftHeight = 0;
			BufferedImage imageResult = null;// 保存每张图片的像素值
			// 利用PdfBox生成图像
			PDDocument pdDocument = PDDocument.load(new File(pdfPath));
			PDFRenderer renderer = new PDFRenderer(pdDocument);
			// 循环每个页码
			for (int i = 0, len = pdDocument.getNumberOfPages(); i < len; i++) {
				//BufferedImage srcImage = resizeBufferedImage(image,160, 220,false);/
				t2 = System.currentTimeMillis();
				BufferedImage image = renderer.renderImageWithDPI(i, DEFAULT_DPI, ImageType.RGB);
				t3 = System.currentTimeMillis();
				logger.info("============t3-t2={}", t3 - t2);
				int imageHeight = image.getHeight();
				int imageWidth = image.getWidth();

				imageResult = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
				singleImgRGB = image.getRGB(0, 0, imageWidth, imageHeight, null, 0, imageWidth);
				//imageResult.setRGB(0, 0, imageWidth, imageHeight, singleImgRGB, 0, imageWidth); // 写入流中

				imageResult.setRGB(0, 0, imageWidth, imageHeight, singleImgRGB, 0, imageWidth); // 写入流中

				File outFile = new File(pdfPath.replace(".pdf", "_" + DEFAULT_DPI + "_" + i + ".jpg"));
				ImageIO.write(imageResult, "jpg", outFile);// 写图片
				//break;
				// old
//				if (i == 0) {// 计算高度和偏移量
//					width = imageWidth;// 使用第一张图片宽度;
//					// 保存每页图片的像素值
//					imageResult = new BufferedImage(width, imageHeight * len, BufferedImage.TYPE_INT_RGB);
//					//imageResult = new BufferedImage(160, 220, BufferedImage.TYPE_INT_RGB);
//				} else {
//					shiftHeight += imageHeight; // 计算偏移高度
//				}
//				singleImgRGB = image.getRGB(0, 0, width, imageHeight, null, 0, width);
//				imageResult.setRGB(0, shiftHeight, width, imageHeight, singleImgRGB, 0, width); // 写入流中
//				//break;
			}

			pdDocument.close();
			// old
//			File outFile = new File(pdfPath.replace(".pdf", "_" + DEFAULT_DPI + ".jpg"));
//			ImageIO.write(imageResult, "jpg", outFile);// 写图片
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			t2 = System.currentTimeMillis();
			logger.info("============timesss={}", t2 - t1);
		}
	}
}
