package com.berritus.mis.common.utils;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.Document;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.lowagie.text.Rectangle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Image;

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

	public static void main(String[] args) throws Exception {
		 String savepath = "E:\\pdf\\all.pdf";
		//for (int i = 0; i <= 10; i++) {
		// 351 32
		//pdf2png3("E:\\pdf", "32", "png");
		//pdf2png3old("E:\\pdf","ceshi","png");

		//pdfToImage("E:\\pdf\\WebServices.pdf");
		//}

		//imgToPdf("E:\\pdf\\", "E:\\pdf\\hebing.pdf");

		List<String> fileList = getFiles();
		morePdfTopdf(fileList, savepath);
	}


	public static List<String> getFiles() {
		List<String> fileList = new ArrayList<String>();
		fileList.add("E:\\pdf\\24.pdf");
		fileList.add("E:\\pdf\\32.pdf");
		fileList.add("E:\\pdf\\38.pdf");
		//fileList.add("E:\\pdf\\351.pdf");
		return fileList;
	}

	public static void morePdfTopdf(List<String> fileList, String savepath) {
		long t1 = System.currentTimeMillis();
		long t2 = System.currentTimeMillis();
		Document document = null;
		try {
			document = new Document(new PdfReader(fileList.get(0)).getPageSize(1));
			PdfCopy copy = new PdfCopy(document, new FileOutputStream(savepath));
			document.open();
			for (int i = 0; i < fileList.size(); i++) {
				PdfReader reader = new PdfReader(fileList.get(i));
				// 获得总页码
				int n = reader.getNumberOfPages();
				for (int j = 1; j <= n; j++) {
					document.newPage();
					// 从当前Pdf,获取第j页
					PdfImportedPage page = copy.getImportedPage(reader, j);
					PdfCopy.PageStamp stamp = copy.createPageStamp(page);
					PdfContentByte underContent = stamp.getOverContent();
					com.itextpdf.text.Rectangle rect = reader.getPageSize(j);
					com.itextpdf.text.Rectangle pageRect = page.getBoundingBox();
					copy.setPageSize(rect);
					underContent.saveState();
					underContent.restoreState();
					stamp.alterContents();

					copy.addPage(page);
				}
				System.out.println(i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} finally {
			if (document != null) {
				document.close();
			}
			t2 = System.currentTimeMillis();
			logger.info("finish use time={} ms", t2 - t1);
		}
	}

	/**
	 * @Description:
	 * @Author: Qin Guihe
	 * @Create: 2020/3/11
	 * @return:
	 */
	private static void pdfToImage(String pdfPath) throws Exception{
		long t1 = System.currentTimeMillis();
		long t2 = System.currentTimeMillis();
		long t3 = System.currentTimeMillis();
		long t4 = System.currentTimeMillis();
		long t5 = System.currentTimeMillis();

		CountDownLatch latch = null;
		PDDocument pdDocument = null;
		try {
			if (pdfPath == null || "".equals(pdfPath) || !pdfPath.endsWith(".pdf")) {
				return;
			}

			// 保存一张图片中的RGB数据
			int[] singleImgRGB;
			// 保存每张图片的像素值
			BufferedImage imageResult = null;
			// 利用PdfBox生成图像
			pdDocument = PDDocument.load(new File(pdfPath));
			PDFRenderer renderer = new PDFRenderer(pdDocument);
			int total = pdDocument.getNumberOfPages();

			if (total > 30) {
				int processNum = Runtime.getRuntime().availableProcessors();
				ExecutorService executorService = Executors.newFixedThreadPool(processNum - 1);
				latch = new CountDownLatch(total);
				for (int i = 0; i < total; i++) {
					ThreadOriginPdf threadOriginPdf = new ThreadOriginPdf(renderer, i, latch, pdfPath);
					executorService.submit(threadOriginPdf);
				}
				latch.await();
				executorService.shutdown();
			} else {
				// 循环每个页码
				for (int i = 0; i < total; i++) {
					pdfToOriginImg(pdfPath, renderer, i);
				}
			}
		} catch (Exception e) {
			logger.error("转换异常{}", e);
		} finally {
			if (pdDocument != null) {
				pdDocument.close();
			}
			t2 = System.currentTimeMillis();
			logger.info("转换总耗时 times={}", t2 - t1);
		}
	}

	/**
	 * @Description:
	 * @Author: Qin Guihe
	 * @Create: 2020/3/11
	 * @return:
	 */
	private static void pdfToOriginImg(String pdfPath, PDFRenderer renderer, Integer pageIndex) throws Exception{
		long t1 = System.currentTimeMillis();
		long t2 = System.currentTimeMillis();
		long t3 = System.currentTimeMillis();

		BufferedImage image = renderer.renderImageWithDPI(pageIndex, DEFAULT_DPI, ImageType.RGB);
		t2 = System.currentTimeMillis();

		int imageHeight = image.getHeight();
		int imageWidth = image.getWidth();
		BufferedImage imageResult = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
		int[] singleImgRGB = image.getRGB(0, 0, imageWidth, imageHeight, null, 0, imageWidth);
		// 写入流中
		imageResult.setRGB(0, 0, imageWidth, imageHeight, singleImgRGB, 0, imageWidth);
		File outFile = new File(pdfPath.replace(".pdf", "_" + pageIndex + ".jpg"));
		// 写图片
		ImageIO.write(imageResult, "jpg", outFile);
		t3 = System.currentTimeMillis();

		logger.info("pdfToOriginImg ============ t2-t1={}", t2 - t1);
		logger.info("pdfToOriginImg ============ t3-t2={}", t3 - t2);
		logger.info("pdfToOriginImg ============ t3-t1={}", t3 - t1);

	}

	/**
	 * @Description:
	 * @Author: Qin Guihe
	 * @Create: 2020/3/11
	 * @return:
	 */
	static class ThreadOriginPdf implements Runnable {
		private CountDownLatch latch;
		private PDFRenderer renderer;
		private int pageIndex;
		private String pdfPath;

		public ThreadOriginPdf(PDFRenderer renderer, int pageIndex,
						 CountDownLatch latch, String pdfPath) {
			this.renderer = renderer;
			this.pageIndex = pageIndex;
			this.latch = latch;
			this.pdfPath = pdfPath;
		}

		@Override
		public void run() {
			long t1 = System.currentTimeMillis();
			long t2 = System.currentTimeMillis();
			try {
				pdfToOriginImg(pdfPath, renderer, pageIndex);
			} catch (Exception e) {
				logger.error("ThreadOriginPdf 异常{}, pageIndex={}", e, pageIndex);
			} finally {
				latch.countDown();
				t2 = System.currentTimeMillis();
				logger.info("ThreadOriginPdf thread={}, use times={}ms", Thread.currentThread().getName(), t2 - t1);
			}
		}
	}

	/**
	 * 转换全部的pdf
	 *
	 * @param fileAddress 文件地址
	 * @param filename    PDF文件名
	 * @param type        图片类型
	 */
	public static void pdf2png(String fileAddress, String filename, String type) {
		// 将pdf装图片 并且自定义图片得格式大小
		File file = new File(fileAddress + "\\" + filename + ".pdf");
		try {
			PDDocument doc = PDDocument.load(file);
			PDFRenderer renderer = new PDFRenderer(doc);
			int pageCount = doc.getNumberOfPages();
			for (int i = 0; i < pageCount; i++) {
				BufferedImage image = renderer.renderImageWithDPI(i, 300); // Windows native DPI
				BufferedImage srcImage = resizeBufferedImage(image, 160, 220, false);//产生缩略图
				ImageIO.write(srcImage, type, new File(fileAddress + "\\" + filename + "_" + (i + 1) + "." + type));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 自由确定起始页和终止页
	 *
	 * @param fileAddress  文件地址
	 * @param filename     pdf文件名
	 * @param indexOfStart 开始页  开始转换的页码，从0开始
	 * @param indexOfEnd   结束页  停止转换的页码，-1为全部
	 * @param type         图片类型
	 */
	public static void pdf2png(String fileAddress, String filename, int indexOfStart, int indexOfEnd, String type) {
		// 将pdf装图片 并且自定义图片得格式大小
		File file = new File(fileAddress + "\\" + filename + ".pdf");
		try {
			PDDocument doc = PDDocument.load(file);
			PDFRenderer renderer = new PDFRenderer(doc);
			int pageCount = doc.getNumberOfPages();
			for (int i = indexOfStart; i < indexOfEnd; i++) {
				BufferedImage image = renderer.renderImageWithDPI(i, 160); // Windows native DPI
				BufferedImage srcImage = resizeBufferedImage(image, 0, 0, true);//产生缩略图
				ImageIO.write(srcImage, type, new File(fileAddress + "\\" + filename + "_" + (i + 1) + "." + type));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	/**
	 * 调整bufferedimage大小
	 *
	 * @param source  BufferedImage 原始image
	 * @param targetW int  目标宽
	 * @param targetH int  目标高
	 * @param flag    boolean 是否同比例调整
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
		} else if (flag && sx <= sy) {
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
	 *
	 * @param content
	 * @param gs
	 * @param pageRect
	 * @param watermark
	 */
	public static void addWatermark(PdfContentByte content, PdfGState gs, Rectangle pageRect,
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
		} finally {
			content = null;
			font = null;
			pageRect = null;
		}
	}

	/**
	 * 转换全部的pdf
	 *
	 * @param fileAddress 文件地址
	 * @param filename    PDF文件名
	 * @param type        图片类型
	 */
	public static void pdf2png3(final String fileAddress, final String filename,
								final String type) throws Exception {
		long t1 = System.currentTimeMillis();
		long t2 = System.currentTimeMillis();
		// 将pdf装图片 并且自定义图片得格式大小
		File file = new File(fileAddress + "\\" + filename + ".pdf");
		CountDownLatch latch = null;
		try {
			PDDocument doc = PDDocument.load(file);
			final PDFRenderer renderer = new PDFRenderer(doc);
			Integer pageCount = doc.getNumberOfPages();

			//pageCount = pageCount - 1;
			if (pageCount >= 40) {
				latch = new CountDownLatch(pageCount);
				int processNum = Runtime.getRuntime().availableProcessors();
				ExecutorService executorService = Executors.newFixedThreadPool(processNum - 1);
				//ExecutorService executorService = Executors.newFixedThreadPool(10);
				for (int i = 0; i < pageCount; i++) {
					ThreadPdf threadPdf = new ThreadPdf(renderer, doc, i, latch, fileAddress, filename, type);
					executorService.submit(threadPdf);
				}
				executorService.shutdown();
			} else {
				for (int i = 0; i < pageCount; i++) {
					pdf2pngHandler(renderer, doc, i, fileAddress, filename, type);
				}

				//pdf2pngHandler(renderer, doc, 0, fileAddress, filename, type);
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
						 CountDownLatch latch, String fileAddress, String filename, String type) {
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
									   String fileAddress, String filename, String type) throws Exception {
		long t1 = System.currentTimeMillis();
		long t2 = System.currentTimeMillis();
		long t3 = System.currentTimeMillis();
		long t4 = System.currentTimeMillis();
		// Windows native DPI
		BufferedImage image = renderer.renderImageWithDPI(pageIndex, 144, ImageType.RGB);
		t2 = System.currentTimeMillis();
		//产生缩略图
		BufferedImage srcImage = resizeBufferedImage(image, 160, 220, false);
		t3 = System.currentTimeMillis();
		ImageIO.write(srcImage, type, new File(fileAddress + "\\" + filename + "_" + (pageIndex + 1) + "." + type));
		t4 = System.currentTimeMillis();

		logger.info("thread={} pageIndex={},t2 - t1 use times={}ms", Thread.currentThread().getName(), pageIndex, t2 - t1);
		logger.info("thread={} pageIndex={},t3 - t2 use times={}ms", Thread.currentThread().getName(), pageIndex, t3 - t2);
		logger.info("thread={} pageIndex={},t4 - t3 use times={}ms", Thread.currentThread().getName(), pageIndex, t4 - t3);
		logger.info("thread={} pageIndex={},t4 - t1 use times={}ms", Thread.currentThread().getName(), pageIndex, t4 - t1);
	}
}
