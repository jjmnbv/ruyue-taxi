package com.wx;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QrcodeUtil {
	
   /**
	* 设置二维码的格式参数
	* @return 
	*/
	public static Map<EncodeHintType, Object> getDecodeHintType(){
		// 用于设置QR二维码参数
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		// 设置QR二维码的纠错级别（H为最高级别）具体级别信息
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		// 设置编码方式
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hints.put(EncodeHintType.MAX_SIZE, 350);
		hints.put(EncodeHintType.MIN_SIZE, 100);
		return hints;
	}

   /**
	* 构建初始化二维码
	* @param bm
	* @return 
	*/
	private static BufferedImage fileToBufferedImage(BitMatrix bm){
		BufferedImage image = null;
		try{
			int w = bm.getWidth(), h = bm.getHeight();
			image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			for (int x = 0; x < w; x++){
				for (int y = 0; y < h; y++){
					image.setRGB(x, y, bm.get(x, y) ? 0xFF000000 : 0xFFCCDDEE);
				}
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return image;
	}


   /**
	* 生成二维码bufferedImage图片
	* @param content 编码内容
	* @param barcodeFormat 编码类型
	* @param width 图片宽度
	* @param height 图片高度
	* @param hints 设置参数
	* @return 
	*/
	public static BufferedImage getQR_CODEBufferedImage(String content, BarcodeFormat barcodeFormat, int width, int height, Map<EncodeHintType, ?> hints){
		MultiFormatWriter multiFormatWriter = null;
		BitMatrix bm = null;
		BufferedImage image = null;
		try{
			multiFormatWriter = new MultiFormatWriter();
			// 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
			bm = multiFormatWriter.encode(content, barcodeFormat, width, height, hints);
			int w = bm.getWidth();
			int h = bm.getHeight();
			image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			// 开始利用二维码数据创建Bitmap图片，分别设为黑（0xFFFFFFFF）白（0xFF000000）两色
			for (int x = 0; x < w; x++){
				for (int y = 0; y < h; y++){
					image.setRGB(x, y, bm.get(x, y) ? 0xFF000000 : 0xf7f7f7f7);
				}
			}
		}catch(WriterException e){
			e.printStackTrace();
		}
		return image;
	}

	/**
	* 将二维码生成为文件
	* 
	* @param bm
	* @param imageFormat
	* @param file
	*/
	public static void decodeQR_CODE2ImageFile(BitMatrix bm, String imageFormat, File file){
		try{
			if(null == file || file.getName().trim().isEmpty()){
				throw new IllegalArgumentException("文件异常，或扩展名有问题！");
			}
			BufferedImage bi = fileToBufferedImage(bm);
			ImageIO.write(bi, "jpeg", file);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

   /**
	* 将二维码生成为输出流
	* 
	* @param content
	* @param imageFormat
	* @param os
	*/
	public static void decodeQR_CODE2OutputStream(BitMatrix bm, String imageFormat, OutputStream os){
		try{
			BufferedImage image = fileToBufferedImage(bm);
			ImageIO.write(image, imageFormat, os);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

   /**
	* 给二维码图片添加Logo
	* @param qrPic
	* @param logoPic
	*/
	public static void addLogo_QRCode(File qrPic, File logoPic, LogoConfig logoConfig){
		try{
			if (!qrPic.isFile() || !logoPic.isFile()){
				System.out.print("file not find !");
				System.exit(0);
			}
			/**
			* 读取二维码图片，并构建绘图对象
			*/
			BufferedImage image = ImageIO.read(qrPic);
			Graphics2D g = image.createGraphics();
			/**
			* 读取Logo图片
			*/
			BufferedImage logo = ImageIO.read(logoPic);
			int widthLogo = logo.getWidth(), heightLogo = logo.getHeight();
			// 计算图片放置位置
			int x = (image.getWidth() - widthLogo) / 2;
			int y = (image.getHeight() - logo.getHeight()) / 2;
			//开始绘制图片
			g.drawImage(logo, x, y, widthLogo, heightLogo, null);
			g.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);
			g.setStroke(new BasicStroke(logoConfig.getBorder()));
			g.setColor(logoConfig.getBorderColor());
			g.drawRect(x, y, widthLogo, heightLogo);
			g.dispose();
			ImageIO.write(image, "jpeg", new File("D:/newPic.jpg"));
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public static class LogoConfig{
		// logo默认边框颜色
		public static final Color DEFAULT_BORDERCOLOR = Color.WHITE;
		// logo默认边框宽度
		public static final int DEFAULT_BORDER = 2;
		// logo大小默认为照片的1/5
		public static final int DEFAULT_LOGOPART = 5;
		
		private final int border = DEFAULT_BORDER;
		private final Color borderColor;
		private final int logoPart;


	  /**
		* Creates a default config with on color {@link #BLACK} and off color
		* {@link #WHITE}, generating normal black-on-white barcodes.
		*/
		public LogoConfig(){
			this(DEFAULT_BORDERCOLOR, DEFAULT_LOGOPART);
		}

		public LogoConfig(Color borderColor, int logoPart){
			this.borderColor = borderColor;
			this.logoPart = logoPart;
		}

		public Color getBorderColor(){
			return borderColor;
		}
		
		public int getBorder(){
			return border;
		}

		public int getLogoPart(){
			return logoPart;
		}
	}
}
