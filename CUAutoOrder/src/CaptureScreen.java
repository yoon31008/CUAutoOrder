import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CaptureScreen {
	{
		// make sure we have exactly two arguments,
		// a waiting period and a file name
		/*
		 * if (args.length != 2) { System.err.println("Usage: java Screenshot " +
		 * "WAITSECONDS OUTFILE.png"); System.exit(1); } // check if file name is valid
		 * String outFileName = args[1]; if
		 * (!outFileName.toLowerCase().endsWith(".png")) {
		 * System.err.println("Error: output file name must " + "end with \".png\".");
		 * System.exit(1); } // wait for a user-specified time try { long time =
		 * Long.parseLong(args[0]) * 1000L; System.out.println("Waiting " + (time /
		 * 1000L) + " second(s)..."); Thread.sleep(time); } catch(NumberFormatException
		 * nfe) { System.err.println(args[0] + " does not seem to be a " +
		 * "valid number of seconds."); System.exit(1); }
		 */
		// determine current screen size
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		Rectangle screenRect = new Rectangle(screenSize);
		// create screen shot
		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedImage image = robot.createScreenCapture(screenRect);
	
		BufferedImage expireDateImage = cropImage(image, 828, 356, 881-828, 377-356);
		BufferedImage currentStockImage = cropImage(image, 887 , 356, 928 - 887, 377 - 356);
		BufferedImage averageSoldImage = cropImage(image, 1153, 736, 1222-1153, 759-736);
		
		try {
			ImageIO.write(expireDateImage, "png", new File("C:\\Users\\Administrator\\Desktop\\expireDateImage.png"));
			ImageIO.write(currentStockImage, "png", new File("C:\\Users\\Administrator\\Desktop\\currentStockImage.png"));
			ImageIO.write(averageSoldImage, "png", new File("C:\\Users\\Administrator\\Desktop\\averageSoldImage.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	private BufferedImage cropImage(BufferedImage originalImgage, int x, int y, int w, int h){

	    BufferedImage subImage = originalImgage.getSubimage(x, y, w, h);

		return subImage;
	}
}
