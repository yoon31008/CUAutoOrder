import java.io.File;

import net.sourceforge.tess4j.Tesseract;

public class OCR {
	static Tesseract instance = Tesseract.getInstance();

	public static String process(String filename) {
		File inputFile = new File(filename);
		String result = "";
		try {
			result = instance.doOCR(inputFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
