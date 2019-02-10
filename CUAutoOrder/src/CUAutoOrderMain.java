import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class CUAutoOrderMain {
	
	public static void main(String[] args) throws Exception {
		new CaptureScreen();
		OCR ocr = new OCR();
		
		System.out.println(ocr.process("C:\\Users\\Administrator\\Desktop\\expireDateImage.png"));
		System.out.println(ocr.process("C:\\Users\\Administrator\\Desktop\\currentStockImage.png"));
		System.out.println(ocr.process("C:\\Users\\Administrator\\Desktop\\averageSoldImage.png"));
	}
}
