import static java.awt.event.KeyEvent.VK_0;
import static java.awt.event.KeyEvent.VK_1;
import static java.awt.event.KeyEvent.VK_2;
import static java.awt.event.KeyEvent.VK_3;
import static java.awt.event.KeyEvent.VK_4;
import static java.awt.event.KeyEvent.VK_5;
import static java.awt.event.KeyEvent.VK_6;
import static java.awt.event.KeyEvent.VK_7;
import static java.awt.event.KeyEvent.VK_8;
import static java.awt.event.KeyEvent.VK_9;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
//** ��ȭ��ǰ��, ���ִ� ���� �����ؾߵ�, �������� ���̽��帵ũ ��ŵ Ǯ��ߵ�

//�Ѱ͵�
//�� ��(��=1\n-|) ����Ʈ ���Ϻ���Ű ������ �ø��� ���ݸ� �� ĵ�� �Ϲݾ��̽�ũ�� RI���̽�ũ��(��0-0|��ũ��) �������ַ�(��_��0~7ȣ��\nl- |_-��-����) ��ī����       
//���б���� ������� �׼������� ���̷�� Ŀ�Ƕ��(Ŀ������) ���ܷ� ���(���\n-����) ����Ｎ��ing(��- ��^��^|\n�ٵ��� -l-l) �õ��Ｎ��(�ĵ����\n:l:>-l -l-l)
//�����Ｎ��(������\n:l:>-l -l-l) �Ǿ��ǰ(��ǰ\n����) �ǰ����(�������) ��������Ǿ�ǰ(�����������ǰ) ����ἱ�ɼ�Ʈ ���о������� 
//����������� ����(���󳫳�\n��-��)  Ŀ������ ������(������) ƾ������ ���̽��帵ũ(0-�̽��帲ũ) �䱸��Ʈ ���� ����(�̹�\n�ٵ�) ����(�����Ʒ�\n-|-��)  ����(�˳�ȣ��\n-l--��)
//������ ����(0=7��\nŸ-��) ���� ���ἱ�ɼ�Ʈ ȭ��ǰ �������
//������ǰ(Ÿ ����-��-�Ҷ�\n��������) ������ǰ �� ���� Ƽ
//Ȩ/�ֹ��ǰ(��- ȣȣ\n����Ӻ���ǰ) ������(:l ����\n:�Ӣӡ�)  ������
//��õ���ǰ(��������ǰ) �ϱ��� ��Ƽ/������ǰ(��Ƽ�������ǰ) �� �ϵ�ǰ ������ȭ��ǰ ����ī��
//��ǰ�� ���ǻ�ǰ(�����ǰ)
//��� �� �ڴ�� �Ҹ�ǰ

//Ŀ�����ᰡ �Լ� > 1�϶� ���ְ����� �ߵ����� 

//�����/ ���о������� ����������� Ŀ������ �䱸��Ʈ ����,�ٴ�\n-��1-��

public class Main {

	public static BufferedImage capturedImage;

	public static BufferedImage averageSoldImage;
	public static BufferedImage soldADayImage;
	public static BufferedImage currentStockImage;
	public static BufferedImage orderedNumADayImage;
	public static BufferedImage multipliedNumImage;
	public static BufferedImage deliveredQtAdayImage;

	public static BufferedImage enlargedAverageSoldImage;
	public static BufferedImage enlargedCurrentStockImage;
	private static BufferedImage totalOrderQTimage;
	private static BufferedImage enlargedTotalOrderQTimage;
	private static BufferedImage kindImage;
	private static BufferedImage enlargedKindImage;
	private static BufferedImage expiredDateImage;

	public static int orderedNumADay;
	public static int totalOrderedNum;
	public static float averageSold;
	// public static int soldADayQt;
	// public static int soldAWeekQt;
	public static int currentStock;
	public static int multipliedNum;
	public static int deliveredQtADay;
	public static int totalDeliveredQt;

	public static int futrueDeliveryQt;
	public static float toBeOrderedQt;
	public static int inputOrder;
	private static int idx;
	private static int totalOrderQT;
	private static int deliveredQTloopStart;
	private static int expireDate;

	private static String totalOrderQTstring;
	private static String refinedTotalOrderQTstring;
	private static String kindString;

	private static boolean screenCapturedForTotalOrderQT;

	// ����Ǹŷ�
	public static int AVERAGE_SOLD_FIRST_X;
	public static int AVERAGE_SOLD_FIRST_Y;
	public static int AVERAGE_SOLD_WIDTH;
	public static int AVERAGE_SOLD_HEIGHT;

	// �����
	public static int CURRENT_STOCK_FIRST_X;
	public static int CURRENT_STOCK_FIRST_Y;
	public static int CURRENT_STOCK_WIDTH;
	public static int CURRENT_STOCK_HEIGHT;
	// ����� ���ڹؿ� ������������ ����
	public static int HEIGHT_ABOVE_UNDERLINE;
	public static int CURRENT_STOCK_INTERVAL;

	// �Ϸ���ַ�
	public static int ORDEREDNUM_A_DAY_FIRST_X;
	public static int ORDEREDNUM_A_DAY_FIRST_Y;
	public static int ORDEREDNUM_A_DAY_WIDTH;
	public static int ORDEREDNUM_A_DAY_HEIGHT;
	public static int ORDEREDNUM_A_DAY_INTERVAL;

	// �Լ�
	public static int MULTIPLIED_NUM_FIRST_X;
	public static int MULTIPLIED_NUM_FIRST_Y;
	public static int MULTIPLIED_NUM_WIDTH;
	public static int MULTIPLIED_NUM_HEIGHT;
	public static int MULTIPLIED_NUM_INTERVAL;

	// �Ϸ糳ǰ��
	public static int DELIVEREDQT_A_DAY_FIRST_X;
	public static int DELIVEREDQT_A_DAY_FIRST_Y;
	public static int DELIVEREDQT_A_DAY_WIDTH;
	public static int DELIVEREDQT_A_DAY_HEIGHT;
	public static int DELIVEREDQT_A_DAY_INTERVAL;

	// �Ϸ��Ǹŷ�
	/*
	 * public static int SOLD_A_DAY_FISRT_X = 249; public static int
	 * SOLD_A_DAY_FISRT_Y = 696; public static int SOLD_A_DAY_FISRT_WIDTH = 307 -
	 * SOLD_A_DAY_FISRT_X; public static int SOLD_A_DAY_FISRT_HEIGHT = 722 -
	 * SOLD_A_DAY_FISRT_Y;
	 */

	// ���������
	public static int TOTAL_ORDER_QT_X;
	public static int TOTAL_ORDER_QT_Y;
	public static int TOTAL_ORDER_QT_WIDTH;
	public static int TOTAL_ORDER_QT_HEIGHT;

	// �ߺз�
	public static int KIND_X;
	public static int KIND_Y;
	public static int KIND_WIDTH;
	public static int KIND_HEIGHT;

	// �������
	public static int EXPIRED_DATE_X;
	public static int EXPIRED_DATE_Y;
	public static int EXPIRED_DATE_WIDTH;
	public static int EXPIRED_DATE_HEIGHT;
	public static int EXPIRED_DATE_INTERVAL;

	private static boolean heatFirstOrderedNum;

	private static int zeroOrderCount;

	private static int passClickAmount;

	private static boolean jobsDone = false;

	public static void main(String args[]) throws AWTException, IOException, TesseractException {
		Properties prop = new Properties();

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Imgcodecs imageCodecs = new Imgcodecs();
		Mat src = new Mat();
		Mat gray = new Mat();
		Mat bw = new Mat();

		Robot r = new Robot();

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		Rectangle screenRect = new Rectangle(screenSize);
		
		//���� ������ ���Ѵ�.
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        int todayInt = calendar.get(Calendar.DAY_OF_WEEK);

		// �׼���Ʈ ����
		ITesseract instance = new Tesseract();
		instance.setDatapath("C:\\tessdata");// ��Ʈ�Ͽ�
		//instance.setDatapath("C:\\Users\\Joshyoon\\git\\CUAutoOrder\\CUAutoOrder2\\tessdata");// ���Ŀ�

		// ���Ϸκ��� ��ǥ������ �޾ƿͼ� ������ �ڴ´�.
		try {
			InputStream in = new FileInputStream("notebook.properties"); // ��Ʈ�Ͽ�
			//InputStream in = new FileInputStream("home.properties"); // ���Ŀ�

			prop.load(in);
			/*
			 * prop.setProperty("newkey", "newvalue"); prop.store(new
			 * FileOutputStream("xyz.properties"), null);
			 */

			// ����Ǹŷ�
			AVERAGE_SOLD_FIRST_X = getIntFromProperties(prop, "AVERAGE_SOLD_FIRST_X");
			AVERAGE_SOLD_FIRST_Y = getIntFromProperties(prop, "AVERAGE_SOLD_FIRST_Y");
			AVERAGE_SOLD_WIDTH = getIntFromProperties(prop, "AVERAGE_SOLD_WIDTH");
			AVERAGE_SOLD_HEIGHT = getIntFromProperties(prop, "AVERAGE_SOLD_HEIGHT");

			// �����
			CURRENT_STOCK_FIRST_X = getIntFromProperties(prop, "CURRENT_STOCK_FIRST_X");
			CURRENT_STOCK_FIRST_Y = getIntFromProperties(prop, "CURRENT_STOCK_FIRST_Y");
			CURRENT_STOCK_WIDTH = getIntFromProperties(prop, "CURRENT_STOCK_WIDTH");
			CURRENT_STOCK_HEIGHT = getIntFromProperties(prop, "CURRENT_STOCK_HEIGHT");
			// ����� ���ڹؿ� ������������ ����
			HEIGHT_ABOVE_UNDERLINE = getIntFromProperties(prop, "HEIGHT_ABOVE_UNDERLINE");
			CURRENT_STOCK_INTERVAL = getIntFromProperties(prop, "CURRENT_STOCK_INTERVAL");

			// �Ϸ���ַ�
			ORDEREDNUM_A_DAY_FIRST_X = getIntFromProperties(prop, "ORDEREDNUM_A_DAY_FIRST_X");
			ORDEREDNUM_A_DAY_FIRST_Y = getIntFromProperties(prop, "ORDEREDNUM_A_DAY_FIRST_Y");
			ORDEREDNUM_A_DAY_WIDTH = getIntFromProperties(prop, "ORDEREDNUM_A_DAY_WIDTH");
			ORDEREDNUM_A_DAY_HEIGHT = getIntFromProperties(prop, "ORDEREDNUM_A_DAY_HEIGHT");
			ORDEREDNUM_A_DAY_INTERVAL = getIntFromProperties(prop, "ORDEREDNUM_A_DAY_INTERVAL");

			// �Լ�
			MULTIPLIED_NUM_FIRST_X = getIntFromProperties(prop, "MULTIPLIED_NUM_FIRST_X");
			MULTIPLIED_NUM_FIRST_Y = getIntFromProperties(prop, "MULTIPLIED_NUM_FIRST_Y");
			MULTIPLIED_NUM_WIDTH = getIntFromProperties(prop, "MULTIPLIED_NUM_WIDTH");
			MULTIPLIED_NUM_HEIGHT = getIntFromProperties(prop, "MULTIPLIED_NUM_HEIGHT");
			MULTIPLIED_NUM_INTERVAL = getIntFromProperties(prop, "MULTIPLIED_NUM_INTERVAL");

			// �Ϸ糳ǰ��
			DELIVEREDQT_A_DAY_FIRST_X = getIntFromProperties(prop, "DELIVEREDQT_A_DAY_FIRST_X");
			DELIVEREDQT_A_DAY_FIRST_Y = getIntFromProperties(prop, "DELIVEREDQT_A_DAY_FIRST_Y");
			DELIVEREDQT_A_DAY_WIDTH = getIntFromProperties(prop, "DELIVEREDQT_A_DAY_WIDTH");
			DELIVEREDQT_A_DAY_HEIGHT = getIntFromProperties(prop, "DELIVEREDQT_A_DAY_HEIGHT");
			DELIVEREDQT_A_DAY_INTERVAL = getIntFromProperties(prop, "DELIVEREDQT_A_DAY_INTERVAL");

			// ���������
			TOTAL_ORDER_QT_X = getIntFromProperties(prop, "TOTAL_ORDER_QT_X");
			TOTAL_ORDER_QT_Y = getIntFromProperties(prop, "TOTAL_ORDER_QT_Y");
			TOTAL_ORDER_QT_WIDTH = getIntFromProperties(prop, "TOTAL_ORDER_QT_WIDTH");
			TOTAL_ORDER_QT_HEIGHT = getIntFromProperties(prop, "TOTAL_ORDER_QT_HEIGHT");

			// �ߺз�
			KIND_X = getIntFromProperties(prop, "KIND_X");
			KIND_Y = getIntFromProperties(prop, "KIND_Y");
			KIND_WIDTH = getIntFromProperties(prop, "KIND_WIDTH");
			KIND_HEIGHT = getIntFromProperties(prop, "KIND_HEIGHT");

			// �������
			EXPIRED_DATE_X = getIntFromProperties(prop, "EXPIRED_DATE_X");
			EXPIRED_DATE_Y = getIntFromProperties(prop, "EXPIRED_DATE_Y");
			EXPIRED_DATE_WIDTH = getIntFromProperties(prop, "EXPIRED_DATE_WIDTH");
			EXPIRED_DATE_HEIGHT = getIntFromProperties(prop, "EXPIRED_DATE_HEIGHT");
			EXPIRED_DATE_INTERVAL = getIntFromProperties(prop, "EXPIRED_DATE_INTERVAL");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// ���������� ȭ����ȯ
		r.keyPress(KeyEvent.VK_ALT);
		r.keyPress(KeyEvent.VK_TAB);
		r.keyRelease(KeyEvent.VK_ALT);
		r.keyRelease(KeyEvent.VK_TAB);

		delay(1000);

		while (true) {

			delay(1000);
			// �����ؾߵǴ� ���� ������ ���Ѵ�.
			capturedImage = r.createScreenCapture(screenRect);

			instance.setLanguage("kor");
			instance.setTessVariable("tessedit_char_whitelist", "");
			kindImage = capturedImage.getSubimage(KIND_X, KIND_Y, KIND_WIDTH, KIND_HEIGHT);
			enlargedKindImage = enlargeImage(kindImage, KIND_WIDTH, KIND_HEIGHT);
			kindString = image2string(instance, enlargedKindImage);

			/*
			 * File outputfile = new File("enlargedKindImage.bmp"); try {
			 * ImageIO.write(enlargedKindImage, "bmp", outputfile); } catch (IOException e)
			 * { e.printStackTrace(); }
			 */

			System.out.println(kindString);

			// �Ҹ�ǰ�ϋ� ���α׷� ����.
			if (kindString.equals("�Ҹ�ǰ") || jobsDone == true) {
				if (setFocusToWindowsApp("����, �ƺ�", 0)) {
					copy("�׽�Ʈ");
					paste();
					r.keyPress(KeyEvent.VK_ENTER);
					r.keyRelease(KeyEvent.VK_ENTER);
				} else {
					System.out.println("������� ����������");
				}
			}

			// �󸶳� �����ؾߵǴ��� ������ ����
			instance.setLanguage("eng");// ����� �ٽ� �����ؾ� ���ڰ� �� �νĵ�
			instance.setTessVariable("tessedit_char_whitelist", "-0123456789./");

			totalOrderQTimage = capturedImage.getSubimage(TOTAL_ORDER_QT_X, TOTAL_ORDER_QT_Y, TOTAL_ORDER_QT_WIDTH,
					TOTAL_ORDER_QT_HEIGHT);
			enlargedTotalOrderQTimage = enlargeImage(totalOrderQTimage, TOTAL_ORDER_QT_WIDTH, TOTAL_ORDER_QT_HEIGHT);
			totalOrderQTstring = image2string(instance, enlargedTotalOrderQTimage);

			idx = totalOrderQTstring.indexOf("13");
			refinedTotalOrderQTstring = totalOrderQTstring.substring(idx + 2);
			totalOrderQT = Integer.parseInt(refinedTotalOrderQTstring);

			System.out.println(totalOrderQT);

			if (kindString.equals("��=1\n-|") || kindString.equals("����ἱ�ɼ�Ʈ")) { // ��
				doType(KeyEvent.VK_F4);
				continue;
			} else if (kindString.equals("�йݺ��̽�ũ��") || kindString.equals("���б����") || kindString.equals("0-�̽��帲ũ")
					|| kindString.equals("��ǰ��")) {

				passClickAmount = totalOrderQT / 10 + 1;

				for (int i = 0; i < passClickAmount; i++) {
					doType(KeyEvent.VK_F4);
				}
				continue;
			}

			// ���� �ݺ���
			for (int i = 0; i < totalOrderQT; i++) {

				// ���� �ʱ�ȭ
				orderedNumADay = 0;
				totalOrderedNum = 0;
				averageSold = 0;
				currentStock = 0;
				multipliedNum = 0;
				deliveredQtADay = 0;
				totalDeliveredQt = 0;
				futrueDeliveryQt = 0;
				toBeOrderedQt = 0;
				inputOrder = 0;
				idx = 0;
				expireDate = 0;
				zeroOrderCount = 0;
				heatFirstOrderedNum = false;

				if (i % 10 == 0) {
					delay(400);
				}
				delay(20);

				// for�� �ȿ� ��ũ���� ��°Ÿ� �ѹ� �������Ѽ� ���α׷� �ӵ��� �ø���.

				// ȭ�� ĸ��
				capturedImage = r.createScreenCapture(screenRect);

				averageSoldImage = capturedImage.getSubimage(AVERAGE_SOLD_FIRST_X, AVERAGE_SOLD_FIRST_Y,
						AVERAGE_SOLD_WIDTH, AVERAGE_SOLD_HEIGHT);

				// averageSold�� �� ä������
				enlargedAverageSoldImage = enlargeImage(averageSoldImage, AVERAGE_SOLD_WIDTH, AVERAGE_SOLD_HEIGHT);
				// System.out.println(image2string(instance, enlargedAverageSoldImage));
				averageSold = Float.parseFloat(image2string(instance, enlargedAverageSoldImage));

				// ����ǸŰ� 0�̸� �����ѱ��.
				if (averageSold <= 0) {
					doType(KeyEvent.VK_DOWN);
					System.out.println("averageSold <= 0 pass");
					continue;
				} else if (averageSold >= 10) {
					averageSold = averageSold / 10; // OCR ���� Ƣ������ ���� 2.7�� 27�� �ν��ؼ� �����ϴ� �ڵ��ۼ���.
					// ystem.out.println("hit");
				}

				currentStockImage = capturedImage.getSubimage(CURRENT_STOCK_FIRST_X,
						CURRENT_STOCK_FIRST_Y + (CURRENT_STOCK_INTERVAL * (i % 10)), CURRENT_STOCK_WIDTH,
						HEIGHT_ABOVE_UNDERLINE);
				// currentStockImage �̹������μ���
				// �����̹����� Mat���� ��ȯ
				src = BufferedImage2Mat(currentStockImage);
				// ���̳ʸ� �̹����� ��ȯ
				if (src.channels() == 3) {
					Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
				} else {
					gray = src;
				}
				Core.bitwise_not(gray, gray);
				Imgproc.adaptiveThreshold(gray, bw, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 15, -2);
				// Mat�� �����̹����� ��ȯ
				currentStockImage = Mat2BufferedImage(bw);

				// ȭ��ĸ���� �̹����� ���Ϸ� ���
				/*
				 * File outputfile = new File("currentStockImage.bmp"); try {
				 * ImageIO.write(currentStockImage, "bmp", outputfile); } catch (IOException e)
				 * { e.printStackTrace(); }
				 */

				currentStock = Integer.parseInt(image2string(instance, currentStockImage));

				//System.out.println(currentStock);
				
				// ������ ����ǸŸ� ���ؼ� �����ѱ�Ŵ� �����ѱ�
				if (kindString.equals("���") && currentStock > averageSold * 10 / 2) {
					doType(KeyEvent.VK_DOWN);
					// System.out.println("pass");
					continue;
				} else if (currentStock >= averageSold * 10) {
					doType(KeyEvent.VK_DOWN);
					// System.out.println("currentStock >= averageSold * 10 pass ");
					continue;
				}

				multipliedNumImage = capturedImage.getSubimage(MULTIPLIED_NUM_FIRST_X,
						MULTIPLIED_NUM_FIRST_Y + (MULTIPLIED_NUM_INTERVAL * (i % 10)), MULTIPLIED_NUM_WIDTH,
						MULTIPLIED_NUM_HEIGHT);

				// �̹��� �ǵ� �� ������ ä������.
				multipliedNum = Integer.parseInt(image2string(instance, multipliedNumImage));

				// multipliedNum���� 1�ε� 7�̳� 17�� �ν��ҋ��� ����
				/*
				 * if (multipliedNum == 7 || multipliedNum == 17) { multipliedNum = 1; }
				 */

				// ������ ���� �� ������ �ؼ� totalOrderedNum�� ���Ѵ�.
				for (int x = 0; x < 7; x++) {
					orderedNumADayImage = capturedImage.getSubimage(
							ORDEREDNUM_A_DAY_FIRST_X + (ORDEREDNUM_A_DAY_INTERVAL * x), ORDEREDNUM_A_DAY_FIRST_Y,
							ORDEREDNUM_A_DAY_WIDTH, ORDEREDNUM_A_DAY_HEIGHT);
					orderedNumADay = Integer.parseInt(image2string(instance, orderedNumADayImage));
					if (orderedNumADay != 0 && heatFirstOrderedNum == false) {
						deliveredQTloopStart = x;
						heatFirstOrderedNum = true;
					}
					if (orderedNumADay == 0) {
						zeroOrderCount++;
					}
					totalOrderedNum += orderedNumADay;
				}

				// ������ ���� �� ������ �ؼ� totalDeliveredQt�� ���Ѵ�.

				// ��ǰ ���� ��Ÿ�� ����Ʈ ����
				switch (kindString) {
				case "��":
				case "����Ʈ":
				case "��ī����":
				case "�������":
				case "�׼�������":
				case "���ܷ�":
				case "������\n:l:>-l -l-l":
				case "���о�������":
				case "�䱸��Ʈ":
				case "�ٴ�\n-��1-��":// ����
				case "������":
					// System.out.println("milk");
					// expireDate���� �����´�.
					expiredDateImage = capturedImage.getSubimage(EXPIRED_DATE_X,
							EXPIRED_DATE_Y + (EXPIRED_DATE_INTERVAL * (i % 10)), EXPIRED_DATE_WIDTH,
							EXPIRED_DATE_HEIGHT);
					expireDate = Integer.parseInt(image2string(instance, expiredDateImage));
					break;
				}

				// ���ֳ� �������� ������ ��ǰ������ deliveredQTloopStart + 1�� �Ѵ�. ���ֳ��� ��ǰ���� �����Ŵ� �ǵ帱 �ʿ� ����.
				// ���ֳ��� ��ǰ���� ���� �͵�:

				// ������ ������ ������ ���: ����/��Ű ������ ������Ʋ��߳�? ĵ����Ʋ��߳� �������ַ�1,2��߼��� ������� ���̷��

				// �Ѱ��� ��찡 �����ִ� ���: ��(������� ��߳�����) ����Ʈ �������� ���������

				// ��: RI���̽�ũ��

				/*
				 * switch(kindString) {
				 * 
				 * }
				 */

				if (zeroOrderCount < 7) {
					for (int y = deliveredQTloopStart; y < 7; y++) {
						deliveredQtAdayImage = capturedImage.getSubimage(
								DELIVEREDQT_A_DAY_FIRST_X + (DELIVEREDQT_A_DAY_INTERVAL * y), DELIVEREDQT_A_DAY_FIRST_Y,
								DELIVEREDQT_A_DAY_WIDTH, DELIVEREDQT_A_DAY_HEIGHT);
						deliveredQtADay = Integer.parseInt(image2string(instance, deliveredQtAdayImage));
						totalDeliveredQt += deliveredQtADay;
					}
				}

				// System.out.println(multipliedNum);

				futrueDeliveryQt = totalOrderedNum * multipliedNum - totalDeliveredQt;

				// �ߺз��� ���� ���ְ����� ������.
				//System.out.println(kindString);
				
				switch (kindString) {

				case "������":
					// �Լ� 1�Ǵ� 1�̻�
					// ������� 10�̻�
					if (multipliedNum == 1) {
						if ((int) (averageSold * 10) == 1) {// averageSold�� ��Ȯ���� 0.1�� �ƴ�. �׷��� averageSold == 0.1�ȸ���. �Ҽ���
							// �������� ������.
							inputOrder = (int) (2 - currentStock - futrueDeliveryQt);
						} else if ((int) (averageSold * 10) > 1) {
							inputOrder = (int) (averageSold * 10 - currentStock - futrueDeliveryQt);
						}
					} else if (multipliedNum > 1) {
						toBeOrderedQt = averageSold * 10 * 2 / 3 - currentStock - futrueDeliveryQt;
						inputOrder = (int) Math.ceil(toBeOrderedQt / multipliedNum);
					}
					if (multipliedNum == 20) {
						inputOrder = 0;
					}

					break;

				case "�������":
					// �Լ��� 1�Ǵ� 10
					// ��������� ��� 10�ʰ�
					if (multipliedNum == 1) {
						inputOrder = (int) (averageSold * 10 - currentStock - futrueDeliveryQt);
					}

					if (multipliedNum == 10) {
						inputOrder = 0;
					}

					break;
				case "�����������\n--|-����": // ������
					// �Լ��� 1�Ǵ� 1�̻�
					// ��������� ���� 10�̻�
					if (multipliedNum == 1) {
						if (averageSold >= 0.7) {
							averageSold = 0.7f;
						}
						inputOrder = (int) (averageSold * 10 - currentStock - futrueDeliveryQt);
					} else if (multipliedNum > 1 && (int) (averageSold * 10) > 1 && currentStock <= 2) {
						inputOrder = 1;
					}

					break;

				case "�� �ڴ��":
					// �Լ��� 10
					// ������� ���Ѵ�
					toBeOrderedQt = (averageSold * 10 * 2 / 3) - currentStock - futrueDeliveryQt;

					inputOrder = (int) Math.ceil(toBeOrderedQt / multipliedNum);

					break;

				case "�ϱ���":
				case ":l ����\n:�Ӣӡ�": // ������
				case "���ἱ�ɼ�Ʈ":
				case "����":
				case "0=7��\nŸ-��": // ����
					// �Լ� 1�Ǵ� 1�̻�
					// ������� �� 10�̻�
					if (multipliedNum == 1) {
						inputOrder = (int) (averageSold * 10 - currentStock - futrueDeliveryQt);
					} else if (multipliedNum > 1) {
						toBeOrderedQt = averageSold * 10 / 2 - currentStock - futrueDeliveryQt;
						inputOrder = (int) Math.ceil(toBeOrderedQt / multipliedNum);
					}

					break;
				case "������":
					// �Լ� 1�Ǵ� 1�̻�
					// ������� 10������ �͵� ����
					if (expireDate > 10) {
						toBeOrderedQt = averageSold * 10 * 2 / 3 - currentStock - futrueDeliveryQt;
					} else if (expireDate <= 10) {
						toBeOrderedQt = averageSold * expireDate * 3 / 4 - currentStock - futrueDeliveryQt;
					}

					inputOrder = (int) Math.ceil(toBeOrderedQt / multipliedNum);

					break;

				case "Ÿ ����-��-�Ҷ�\n��������":
				case "Ŀ�Ƕ��":
					// �Լ� 1�Ǵ� 1�̻�
					// ������� 10�̻�
					if (multipliedNum == 1) {

						inputOrder = (int) (averageSold * 10 - currentStock - futrueDeliveryQt);

					} else if (multipliedNum > 1) {
						toBeOrderedQt = averageSold * 10 * 2 / 3 - currentStock - futrueDeliveryQt;
						inputOrder = (int) Math.ceil(toBeOrderedQt / multipliedNum);
					}

					break;
				case "���̷��":
					// �Լ� 1�Ǵ� 1�̻�
					// ������� 10�̻�
					if (multipliedNum == 1) {
						if ((int) (averageSold * 10) == 1) {// averageSold�� ��Ȯ���� 0.1�� �ƴ�. �׷��� averageSold == 0.1�ȸ���. �Ҽ���
							// �������� ������.
							inputOrder = (int) (2 - currentStock - futrueDeliveryQt);
						} else if ((int) (averageSold * 10) > 1) {
							inputOrder = (int) (averageSold * 10 - currentStock - futrueDeliveryQt);
						}
					} else if (multipliedNum > 1) {
						toBeOrderedQt = averageSold * 10 / 2 - currentStock - futrueDeliveryQt;
						inputOrder = (int) Math.ceil(toBeOrderedQt / multipliedNum);
					}

					break;

				case "�˳�ȣ��\n-l--��": // ����
					// �Լ��� ���� 1 �̻�
					// ��������� ���Ѵ�..
					if (multipliedNum > 1) {
						toBeOrderedQt = averageSold * 10 / 2 - currentStock - futrueDeliveryQt;

						inputOrder = (int) Math.ceil(toBeOrderedQt / multipliedNum);
					}

					if (multipliedNum == 30) {
						inputOrder = 0;
					}

					break;

				case "�����Ʒ�\n-|-��":// ����
					// �Լ��� �� 1�ʰ�
					// ��������� �� 10�̻�
					if (multipliedNum > 1) {
						toBeOrderedQt = averageSold * 10 * 3 / 4 - currentStock - futrueDeliveryQt;

						inputOrder = (int) Math.ceil(toBeOrderedQt / multipliedNum);
					}

					break;
				case "�̹�\n�ٵ�": // ����
					// �Լ� 1�̻��ΰ� ����
					// ��ȿ�Ⱓ�� �� 10�̻�
					if (multipliedNum == 1) {
						if ((int) (averageSold * 10) == 1) {// averageSold�� ��Ȯ���� 0.1�� �ƴ�. �׷��� averageSold == 0.1�ȸ���. �Ҽ���
							// �������� ������.
							inputOrder = (int) (2 - currentStock - futrueDeliveryQt);
						} else if ((int) (averageSold * 10) > 1) {
							inputOrder = (int) (averageSold * 10 - currentStock - futrueDeliveryQt);
						}
					} else if (multipliedNum > 1) {
						toBeOrderedQt = averageSold * 10 / 2 - currentStock - futrueDeliveryQt;

						inputOrder = (int) Math.ceil(toBeOrderedQt / multipliedNum);
					}

					break;
				case "���󳫳�\n��-��":// ����
					// �Լ��� 1�ΰŴ� �ϳ��ְ� �������� �� 1 �̻��̴�.
					// ��������� �� 10 �̻�

					if (multipliedNum == 1) {
						inputOrder = (int) (averageSold * 10 * 3 / 4 - currentStock - futrueDeliveryQt);
					} else if (multipliedNum > 1 && (currentStock + futrueDeliveryQt) <= averageSold * 10 / 2) {
						inputOrder = 1;
					}

					break;
				case "�����������":
					// �Լ��� 1 �̻��� �͵� �ִ�.
					// ��������� �� 10�̻��̴�.
					if (multipliedNum == 1) {
						if ((int) (averageSold * 10) == 1) {// averageSold�� ��Ȯ���� 0.1�� �ƴ�. �׷��� averageSold == 0.1�ȸ���. �Ҽ���
							// �������� ������.
							inputOrder = (int) (1 - currentStock - futrueDeliveryQt);
						} else if ((int) (averageSold * 10) > 1) {
							inputOrder = (int) (averageSold * 10 - currentStock - futrueDeliveryQt);
						}
					} else if (multipliedNum > 1 && (currentStock + futrueDeliveryQt) <= averageSold * 10 / 2) {
						inputOrder = 1;
					}
					break;
				case "�������":
				case "��ǰ\n����":
					// �Լ��� 10�̴�.
					// ������� 10�̻��̴�.
					if (currentStock <= averageSold * 10 / 2 - futrueDeliveryQt)
						inputOrder = 1;
					break;

				case "�䱸��Ʈ":
				case "�ٴ�\n-��1-��":// ����
					// �Լ��� 1�Ѵ� �͵��� �մ�.
					// ������� 10������ �͵��� �մ�.
					if (expireDate <= 10) {
						toBeOrderedQt = averageSold * expireDate * 3 / 4 - currentStock - futrueDeliveryQt;
					} else if (expireDate > 10) {
						toBeOrderedQt = averageSold * 10 * 2 / 3 - currentStock - futrueDeliveryQt;
					}

					inputOrder = (int) (toBeOrderedQt / multipliedNum);

					if (multipliedNum == 1 && (int) (averageSold * 10) == 1) {
						inputOrder = (int) (1 - currentStock - futrueDeliveryQt);
					}

					break;

				case "ƾ������":
				case "������":// ������
				case "Ŀ������":
					// �Լ��� 1�Ѵ� �͵��� �մ�.
					// ��������� �� 10�̻��̴�.
					if (multipliedNum == 1) {
						if ((int) (averageSold * 10) == 1) {// averageSold�� ��Ȯ���� 0.1�� �ƴ�. �׷��� averageSold == 0.1�ȸ���. �Ҽ���
							// �������� ������.
							inputOrder = (int) (1 - currentStock - futrueDeliveryQt);
						} else if ((int) (averageSold * 10) > 1) {
							inputOrder = (int) (averageSold * 10 * 2 / 3 - currentStock - futrueDeliveryQt);
						}
					} else if (multipliedNum > 1) {
						toBeOrderedQt = averageSold * 10 * 2 / 3  - currentStock - futrueDeliveryQt;

						inputOrder = (int) Math.floor(toBeOrderedQt / multipliedNum);
					}

					break;
				case "���о�������":
					// �Լ��� �� 1�� �ְ� 1�Ѿ�� �͵� �ִ�.
					// ��������� 10�����ΰ͵��� �ִ�.
					// System.out.println("hit");
					if (expireDate == 10) {
						toBeOrderedQt = averageSold * 10 * 2 / 3 - currentStock - futrueDeliveryQt;
					} else if (expireDate < 10) {
						if ((int) (averageSold * 10) == 1) {// averageSold�� ��Ȯ���� 0.1�� �ƴ�. �׷��� averageSold == 0.1�ȸ���. �Ҽ���
															// �������� ������.
							toBeOrderedQt = 1 - currentStock - futrueDeliveryQt;
						} else if ((int) (averageSold * 10) > 1) {
							toBeOrderedQt = averageSold * expireDate - currentStock - futrueDeliveryQt;
						}
					} else if (expireDate > 10) {

						toBeOrderedQt = averageSold * 10 - currentStock - futrueDeliveryQt;
					}

					inputOrder = (int) Math.floor(toBeOrderedQt / multipliedNum);

					if (multipliedNum > 1 && (currentStock + futrueDeliveryQt) <= averageSold * 10 / 2) {
						inputOrder = 1;
					}

					// System.out.println("after " + inputOrder);
					break;

				case "������\n:l:>-l -l-l":

					// �Լ��� �� 1�̴�.
					// ��������� 10�����ΰ͵��� �ִ�.
					if (expireDate >= 10) {
						inputOrder = (int) (averageSold * 10 - currentStock - futrueDeliveryQt);
					}
					if (expireDate <= 30) {
						inputOrder = (int) (averageSold * 10 * 2 / 3 - currentStock - futrueDeliveryQt);
					}
					if (expireDate < 10) {

						if ((int) (averageSold * 10) == 1) {// averageSold�� ��Ȯ���� 0.1�� �ƴ�. �׷��� averageSold == 0.1�ȸ���. �Ҽ���
															// �������� ������.
							inputOrder = (int) (1 - currentStock - futrueDeliveryQt);
						} else if ((int) (averageSold * 10) > 1) {
							inputOrder = (int) (averageSold * expireDate - currentStock - futrueDeliveryQt);
						}
					}

					break;

				case "����ī��":
				case "������ȭ��ǰ":
				case "�� �ϵ�ǰ":
				case "��Ƽ�������ǰ":// ��Ƽ/������ǰ
				case "�� ���� Ƽ":
				case "������ǰ":
				case "ȭ��ǰ":
				case "�����ǰ":
				case "��������ǰ":// ��õ���ǰ
				case "��- ȣȣ\n����Ӻ���ǰ":// Ȩ/�ֹ��ǰ
				case "�����������ǰ":
				case "��- ��^��^|\n�ٵ��� -l-l":
				case "�ĵ����\n:l:>-l -l-l":
					// �Լ��� �� 1�̴�.
					// ��������� �� 10�̻��̴�.

					inputOrder = (int) (averageSold * 10 - currentStock - futrueDeliveryQt);
					break;

				case "���\n-����": // ���
					// �Լ��� �� 1�̻�
					// ��������� �� 10�̻�

					toBeOrderedQt = averageSold * 10 / 2 - currentStock - futrueDeliveryQt;

					inputOrder = (int) Math.ceil(toBeOrderedQt / multipliedNum);

					if ((int) (averageSold * 10) == 1 && multipliedNum > 1) {
						inputOrder = 0;
					}

					break;

				case "���ܷ�":
					// �Լ��� �� 1�̴�.
				case "�׼�������":

					if (multipliedNum > 1) {
						if (currentStock <= averageSold * 10 / 2 - futrueDeliveryQt)
							inputOrder = 1;
					} else if (multipliedNum == 1) {
						inputOrder = (int) (averageSold * 10 - currentStock - futrueDeliveryQt);
					}

					if (expireDate < 10) {
						if ((int) (averageSold * 10) == 1) {
							inputOrder = (int) (1 - currentStock - futrueDeliveryQt);
						} else if ((int) (averageSold * 10) > 1) {
							inputOrder = (int) (averageSold * expireDate - currentStock - futrueDeliveryQt);
						}
					}

					break;
				case "��ī����":
				case "�������":
					if (expireDate < 10) {
						toBeOrderedQt = (averageSold * expireDate) - currentStock - futrueDeliveryQt;
						if ((averageSold * expireDate) - (int) (averageSold * expireDate) == 0) {// averageSold *
																									// expireDate�� �Ҽ���
																									// �ڸ���
																									// ������
							toBeOrderedQt = toBeOrderedQt - 1;// �ϳ� �� �����Ѵ�
						}
					} else if (expireDate >= 10) {
						toBeOrderedQt = (averageSold * 10 * 2 / 3) - currentStock - futrueDeliveryQt;
						if ((int) (averageSold * 10) == 1) {
							toBeOrderedQt = 1 - currentStock - futrueDeliveryQt;
						}
					}
					// �Ҽ����ڸ����� ������ �ؾߵ�
					inputOrder = (int) Math.floor(toBeOrderedQt / multipliedNum);

					if (multipliedNum > 1) {

						toBeOrderedQt = (averageSold * 10 / 2) - currentStock - futrueDeliveryQt;

						inputOrder = (int) Math.ceil(toBeOrderedQt / multipliedNum);
					}

					break;

				case "��":// ��
				case "����Ʈ":

					if (expireDate < 10) {
						toBeOrderedQt = (averageSold * expireDate) - currentStock - futrueDeliveryQt;

						if ((averageSold * expireDate) - (int) (averageSold * expireDate) == 0) {// averageSold *
																									// expireDate�� �Ҽ���
																									// �ڸ���
																									// ������
							toBeOrderedQt = toBeOrderedQt - 1;// �ϳ� �� �����Ѵ�
						}
						
						if(multipliedNum == 1 && (int)(averageSold * 10) == 1) {
							toBeOrderedQt = 1 - currentStock - futrueDeliveryQt;
						}
							
					} else if (expireDate >= 10) {
						toBeOrderedQt = (averageSold * 10) - currentStock - futrueDeliveryQt;
					}

					/*
					 * if (expireDate >= 20) { toBeOrderedQt = (averageSold * 10 / 2) - currentStock
					 * - futrueDeliveryQt; }
					 */

					// �Ҽ����ڸ����� ������ �ؾߵ�
					inputOrder = (int) Math.floor(toBeOrderedQt / multipliedNum);

					// �Լ��� 1�� ������ ��� * 10 / 2 �ϋ� 1�� ���ֳִ´�.
					if (multipliedNum > 1 && (currentStock + futrueDeliveryQt) <= averageSold * 10 / 2) {
						inputOrder = 1;
					}

					break;

				case "���Ϻ���Ű":

					toBeOrderedQt = (averageSold * 10 * 2 / 3) - currentStock - futrueDeliveryQt;
					inputOrder = (int) Math.ceil(toBeOrderedQt / multipliedNum);

					if (multipliedNum > 1 && (currentStock + futrueDeliveryQt) <= averageSold * 10 / 2) {
						inputOrder = 1;
					}

					/*
					 * if (inputOrder >= 7) { inputOrder = 7; }
					 */

					break;

				case "��0-0|��ũ��":// RI���̽�ũ��
				case "��_��0~7ȣ��\nl- |_-��-����":

					if (averageSold >= 0.7) {
						averageSold = 0.7f;
					}
					toBeOrderedQt = (averageSold * 10) - currentStock - futrueDeliveryQt;
					inputOrder = (int) Math.floor(toBeOrderedQt / multipliedNum);

					break;

				case "���":

					toBeOrderedQt = (averageSold * 10 * 2 / 3) - currentStock - futrueDeliveryQt;

					inputOrder = (int) Math.ceil(toBeOrderedQt / multipliedNum);
					/*
					 * System.out.println("toBeOrderedQt: "+toBeOrderedQt+ "averageSold:"
					 * +averageSold + " currentStock: "+currentStock +
					 * " futrueDeliveryQt:"+futrueDeliveryQt ); System.out.println("����ν�2");
					 */
					break;

				case "�ø���":
				case "ĵ��":
				case "��\n��":
				case "���ݸ�":
					// �Լ��� 1�Ǵ� 1�̻�
					// ��������� 10�̻�

					if (multipliedNum > 1) {
						if (currentStock <= averageSold * 10 / 2 - futrueDeliveryQt)
							inputOrder = 1;
					} else if (multipliedNum == 1) {
						inputOrder = (int) (averageSold * 10 - currentStock - futrueDeliveryQt);
					}

					break;
				
				default:
					//��Ŭ������ ȭ����ȯ
					r.keyPress(KeyEvent.VK_ALT);
					r.keyPress(KeyEvent.VK_TAB);
					r.keyRelease(KeyEvent.VK_ALT);
					r.keyRelease(KeyEvent.VK_TAB);
					System.out.println("�����Ϳ� �����׸���");
					System.exit(0);
				}

				System.out.println("totalOrderedNum: " + totalOrderedNum + "multipliedNum: " + multipliedNum
						+ " totalDeliveredQt: " + totalDeliveredQt);

				System.out.println("toBeOrderedQt: " + toBeOrderedQt + "averageSold:" + averageSold + " currentStock: "
						+ currentStock + " expireDate: " + expireDate + " futrueDeliveryQt: " + futrueDeliveryQt);

				// ���ַ��� 0�̸� �н��Ѵ�.
				if (inputOrder <= 0) {
					doType(KeyEvent.VK_DOWN);
					System.out.println("inputOrder <= 0");
					continue;
				}else if(inputOrder >= 9) {
					inputOrder = 9;
				}

				String stringInputOrder = String.valueOf(inputOrder);

				System.out.println(stringInputOrder);

				// ���ְ��� �������α׷��� ����.
				type(stringInputOrder);

				// ��ĭ �Ʒ��� �̵�
				doType(KeyEvent.VK_DOWN);
				
				if(todayInt == 7 && kindString == "�ٴ�\n-��1-��" && i == totalOrderQT - 1 ) {//����
					jobsDone = true;
				}
			}

		}
	}

	private static String image2string(ITesseract instance, BufferedImage inputImg) {
		String result = null;
		try {
			result = instance.doOCR(inputImg).trim();
			if (result.isEmpty()) {
				result = "0";
				// ���� �߸� �ν��ϸ� �����Ѵ�.
			} else if (result.equals("7\n.")) {
				result = "7";
			} else if (result.equals("11\n. ..")) {
				result = "7.7";
			}

		} catch (TesseractException e) {
			System.err.println(e.getMessage());
		}
		return result;
	}

	private static BufferedImage enlargeImage(BufferedImage inputImage, int width, int height) {
		Image toolkitImage;
		BufferedImage outputImage;
		int scaledWidth = width * 2;
		int scaledHeight = height * 2;

		toolkitImage = inputImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
		outputImage = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
		Graphics g = outputImage.getGraphics();
		g.drawImage(toolkitImage, 0, 0, null);
		g.dispose();
		return outputImage;
	}

	public static Mat BufferedImage2Mat(BufferedImage image) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ImageIO.write(image, "bmp", byteArrayOutputStream);
		byteArrayOutputStream.flush();
		return Imgcodecs.imdecode(new MatOfByte(byteArrayOutputStream.toByteArray()), Imgcodecs.IMREAD_UNCHANGED);
	}

	public static BufferedImage Mat2BufferedImage(Mat matrix) throws IOException {
		MatOfByte mob = new MatOfByte();
		Imgcodecs.imencode(".bmp", matrix, mob);
		return ImageIO.read(new ByteArrayInputStream(mob.toArray()));
	}

	public static void imshow(Mat matrix) {
		// �о���� ������ ������
		Imgproc.resize(matrix, matrix, new Size(640, 480));
		MatOfByte matOfByte = new MatOfByte();
		Imgcodecs.imencode(".jpg", matrix, matOfByte);
		byte[] byteArray = matOfByte.toArray();
		BufferedImage bufImage = null;
		try {
			InputStream in = new ByteArrayInputStream(byteArray);
			bufImage = ImageIO.read(in);
			JFrame frame = new JFrame();
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.getContentPane().add(new JLabel(new ImageIcon(bufImage)));
			frame.pack();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void delay(int milisecond) {
		try {
			Thread.sleep(milisecond);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

	public static void type(CharSequence characters) throws AWTException {
		int length = characters.length();
		for (int i = 0; i < length; i++) {
			char character = characters.charAt(i);
			type(character);
		}
	}

	public static void type(char character) throws AWTException {
		switch (character) {
		case '0':
			doType(VK_0);
			break;
		case '1':
			doType(VK_1);
			break;
		case '2':
			doType(VK_2);
			break;
		case '3':
			doType(VK_3);
			break;
		case '4':
			doType(VK_4);
			break;
		case '5':
			doType(VK_5);
			break;
		case '6':
			doType(VK_6);
			break;
		case '7':
			doType(VK_7);
			break;
		case '8':
			doType(VK_8);
			break;
		case '9':
			doType(VK_9);
			break;
		default:
			throw new IllegalArgumentException("Cannot type character " + character);
		}
	}

	public static void doType(int keyCodes) throws AWTException {
		Robot r = new Robot();

		r.keyPress(keyCodes);
		r.keyRelease(keyCodes);
		try {
			Thread.sleep(20);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int getIntFromProperties(Properties prop, String inputString) {
		return Integer.parseInt(prop.getProperty(inputString));
	}
	
	public static boolean setFocusToWindowsApp(String applicationTitle, int... windowState) {
		int state = User32.SW_SHOWNORMAL; // default window state (Normal)
		if (windowState.length > 0) {
			state = windowState[0];
			switch (state) {
			default:
			case 0:
				state = User32.SW_SHOWNORMAL;
				break;
			case 1:
				state = User32.SW_SHOWMAXIMIZED;
				break;
			case 2:
				state = User32.SW_SHOWMINIMIZED;
				break;
			}
		}
		User32 user32 = User32.INSTANCE;
		WinDef.HWND hWnd = user32.FindWindow(null, applicationTitle);
		if (user32.IsWindowVisible(hWnd)) {
			user32.ShowWindow(hWnd, state); // .SW_SHOW);
			user32.SetForegroundWindow(hWnd);
			user32.SetFocus(hWnd);
			return true;
		} else {
			return false;
		}
	}

	public static void copy(String text) {
		Clipboard clipboard = getSystemClipboard();

		clipboard.setContents(new StringSelection(text), null);
	}

	public static void paste() throws AWTException {
		Robot robot = new Robot();

		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_V);
	}

	private static Clipboard getSystemClipboard() {
		Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
		Clipboard systemClipboard = defaultToolkit.getSystemClipboard();

		return systemClipboard;
	}
	
}
