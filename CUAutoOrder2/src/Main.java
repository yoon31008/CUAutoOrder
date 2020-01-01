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
//** 문화상품권, 소주는 따로 발주해야됨, 여름에는 아이스드링크 스킵 풀어야됨

//한것들
//빵 떡(꾀=1\n-|) 디저트 비스켓봇쿠키 스낵류 시리얼 조콜릿 껌 캔디 일반아이스크림 RI아이스크림(뮈0-0|스크림) 마른안주류(힌_르0~7호근\nl- |_-「-「「) 육카좀류       
//과밀굶재소 놈산식재료 죽수산식재료 조미료류 커피뜻류(커피차류) 반잔류 면류(면끈\n-「「) 상온즉석식ing(씸- 즈^쇄^|\n다돈「 -l-l) 냉동즉석식(냐도즈서시\n:l:>-l -l-l)
//냉장즉석식(대공즈서시\n:l:>-l -l-l) 의약외품(폐품\n뫼므) 건강기능(컨감기능) 안전상비의약품(완전샬비뫼얌품) 식재료선믈세트 과밀야재음료 
//기능컨감음료 생수(갸폐낙노\n힌-「)  커피음료 차음료(곳음료) 틴산음료 아이스드링크(0-이스드림크) 요구르트 우유 얼음(미므\n근딘) 맥쥬(꾀폐컷렸\n-|-「)  소주(싯노호토\n-l--「)
//전통주 양주(0=7렸\n타-「) 와인 음료선믈세트 화장품 목옥세면
//위생용품(타 갸폐-줌-뚝뚝\n뉘랍다판) 뫼류돋품 맥 세서 티
//홈/주방용품(꽈- 호호\n몸댈†빙돋품) 문구류(:l 륵근\n:††「)  전기면료
//우천용상품(우전돋샬품) 완구류 파티/오락용품(파티댈오략돋품) 매 완돋품 밉지룩화삼품 소혐카전
//상품권 편의상품(편뫼삼품)
//담배 전 자담배 소모품

//커피음료가 입수 > 1일때 발주공식이 잘되있음 

//토요일/ 과밀야재음료 기능컨감음료 커피음료 요구르트 우유,다다\n-「1-「

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

	// 평균판매량
	public static int AVERAGE_SOLD_FIRST_X;
	public static int AVERAGE_SOLD_FIRST_Y;
	public static int AVERAGE_SOLD_WIDTH;
	public static int AVERAGE_SOLD_HEIGHT;

	// 현재고
	public static int CURRENT_STOCK_FIRST_X;
	public static int CURRENT_STOCK_FIRST_Y;
	public static int CURRENT_STOCK_WIDTH;
	public static int CURRENT_STOCK_HEIGHT;
	// 현재고 숫자밑에 라인위까지의 높이
	public static int HEIGHT_ABOVE_UNDERLINE;
	public static int CURRENT_STOCK_INTERVAL;

	// 하루발주량
	public static int ORDEREDNUM_A_DAY_FIRST_X;
	public static int ORDEREDNUM_A_DAY_FIRST_Y;
	public static int ORDEREDNUM_A_DAY_WIDTH;
	public static int ORDEREDNUM_A_DAY_HEIGHT;
	public static int ORDEREDNUM_A_DAY_INTERVAL;

	// 입수
	public static int MULTIPLIED_NUM_FIRST_X;
	public static int MULTIPLIED_NUM_FIRST_Y;
	public static int MULTIPLIED_NUM_WIDTH;
	public static int MULTIPLIED_NUM_HEIGHT;
	public static int MULTIPLIED_NUM_INTERVAL;

	// 하루납품량
	public static int DELIVEREDQT_A_DAY_FIRST_X;
	public static int DELIVEREDQT_A_DAY_FIRST_Y;
	public static int DELIVEREDQT_A_DAY_WIDTH;
	public static int DELIVEREDQT_A_DAY_HEIGHT;
	public static int DELIVEREDQT_A_DAY_INTERVAL;

	// 하루판매량
	/*
	 * public static int SOLD_A_DAY_FISRT_X = 249; public static int
	 * SOLD_A_DAY_FISRT_Y = 696; public static int SOLD_A_DAY_FISRT_WIDTH = 307 -
	 * SOLD_A_DAY_FISRT_X; public static int SOLD_A_DAY_FISRT_HEIGHT = 722 -
	 * SOLD_A_DAY_FISRT_Y;
	 */

	// 발주진행률
	public static int TOTAL_ORDER_QT_X;
	public static int TOTAL_ORDER_QT_Y;
	public static int TOTAL_ORDER_QT_WIDTH;
	public static int TOTAL_ORDER_QT_HEIGHT;

	// 중분류
	public static int KIND_X;
	public static int KIND_Y;
	public static int KIND_WIDTH;
	public static int KIND_HEIGHT;

	// 유통기한
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
		
		//오늘 요일을 구한다.
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        int todayInt = calendar.get(Calendar.DAY_OF_WEEK);

		// 테서랙트 세팅
		ITesseract instance = new Tesseract();
		instance.setDatapath("C:\\tessdata");// 노트북용
		//instance.setDatapath("C:\\Users\\Joshyoon\\git\\CUAutoOrder\\CUAutoOrder2\\tessdata");// 집컴용

		// 파일로부터 좌표값들을 받아와서 변수에 박는다.
		try {
			InputStream in = new FileInputStream("notebook.properties"); // 노트북용
			//InputStream in = new FileInputStream("home.properties"); // 집컴용

			prop.load(in);
			/*
			 * prop.setProperty("newkey", "newvalue"); prop.store(new
			 * FileOutputStream("xyz.properties"), null);
			 */

			// 평균판매량
			AVERAGE_SOLD_FIRST_X = getIntFromProperties(prop, "AVERAGE_SOLD_FIRST_X");
			AVERAGE_SOLD_FIRST_Y = getIntFromProperties(prop, "AVERAGE_SOLD_FIRST_Y");
			AVERAGE_SOLD_WIDTH = getIntFromProperties(prop, "AVERAGE_SOLD_WIDTH");
			AVERAGE_SOLD_HEIGHT = getIntFromProperties(prop, "AVERAGE_SOLD_HEIGHT");

			// 현재고
			CURRENT_STOCK_FIRST_X = getIntFromProperties(prop, "CURRENT_STOCK_FIRST_X");
			CURRENT_STOCK_FIRST_Y = getIntFromProperties(prop, "CURRENT_STOCK_FIRST_Y");
			CURRENT_STOCK_WIDTH = getIntFromProperties(prop, "CURRENT_STOCK_WIDTH");
			CURRENT_STOCK_HEIGHT = getIntFromProperties(prop, "CURRENT_STOCK_HEIGHT");
			// 현재고 숫자밑에 라인위까지의 높이
			HEIGHT_ABOVE_UNDERLINE = getIntFromProperties(prop, "HEIGHT_ABOVE_UNDERLINE");
			CURRENT_STOCK_INTERVAL = getIntFromProperties(prop, "CURRENT_STOCK_INTERVAL");

			// 하루발주량
			ORDEREDNUM_A_DAY_FIRST_X = getIntFromProperties(prop, "ORDEREDNUM_A_DAY_FIRST_X");
			ORDEREDNUM_A_DAY_FIRST_Y = getIntFromProperties(prop, "ORDEREDNUM_A_DAY_FIRST_Y");
			ORDEREDNUM_A_DAY_WIDTH = getIntFromProperties(prop, "ORDEREDNUM_A_DAY_WIDTH");
			ORDEREDNUM_A_DAY_HEIGHT = getIntFromProperties(prop, "ORDEREDNUM_A_DAY_HEIGHT");
			ORDEREDNUM_A_DAY_INTERVAL = getIntFromProperties(prop, "ORDEREDNUM_A_DAY_INTERVAL");

			// 입수
			MULTIPLIED_NUM_FIRST_X = getIntFromProperties(prop, "MULTIPLIED_NUM_FIRST_X");
			MULTIPLIED_NUM_FIRST_Y = getIntFromProperties(prop, "MULTIPLIED_NUM_FIRST_Y");
			MULTIPLIED_NUM_WIDTH = getIntFromProperties(prop, "MULTIPLIED_NUM_WIDTH");
			MULTIPLIED_NUM_HEIGHT = getIntFromProperties(prop, "MULTIPLIED_NUM_HEIGHT");
			MULTIPLIED_NUM_INTERVAL = getIntFromProperties(prop, "MULTIPLIED_NUM_INTERVAL");

			// 하루납품량
			DELIVEREDQT_A_DAY_FIRST_X = getIntFromProperties(prop, "DELIVEREDQT_A_DAY_FIRST_X");
			DELIVEREDQT_A_DAY_FIRST_Y = getIntFromProperties(prop, "DELIVEREDQT_A_DAY_FIRST_Y");
			DELIVEREDQT_A_DAY_WIDTH = getIntFromProperties(prop, "DELIVEREDQT_A_DAY_WIDTH");
			DELIVEREDQT_A_DAY_HEIGHT = getIntFromProperties(prop, "DELIVEREDQT_A_DAY_HEIGHT");
			DELIVEREDQT_A_DAY_INTERVAL = getIntFromProperties(prop, "DELIVEREDQT_A_DAY_INTERVAL");

			// 발주진행률
			TOTAL_ORDER_QT_X = getIntFromProperties(prop, "TOTAL_ORDER_QT_X");
			TOTAL_ORDER_QT_Y = getIntFromProperties(prop, "TOTAL_ORDER_QT_Y");
			TOTAL_ORDER_QT_WIDTH = getIntFromProperties(prop, "TOTAL_ORDER_QT_WIDTH");
			TOTAL_ORDER_QT_HEIGHT = getIntFromProperties(prop, "TOTAL_ORDER_QT_HEIGHT");

			// 중분류
			KIND_X = getIntFromProperties(prop, "KIND_X");
			KIND_Y = getIntFromProperties(prop, "KIND_Y");
			KIND_WIDTH = getIntFromProperties(prop, "KIND_WIDTH");
			KIND_HEIGHT = getIntFromProperties(prop, "KIND_HEIGHT");

			// 유통기한
			EXPIRED_DATE_X = getIntFromProperties(prop, "EXPIRED_DATE_X");
			EXPIRED_DATE_Y = getIntFromProperties(prop, "EXPIRED_DATE_Y");
			EXPIRED_DATE_WIDTH = getIntFromProperties(prop, "EXPIRED_DATE_WIDTH");
			EXPIRED_DATE_HEIGHT = getIntFromProperties(prop, "EXPIRED_DATE_HEIGHT");
			EXPIRED_DATE_INTERVAL = getIntFromProperties(prop, "EXPIRED_DATE_INTERVAL");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 씨유앱으로 화면전환
		r.keyPress(KeyEvent.VK_ALT);
		r.keyPress(KeyEvent.VK_TAB);
		r.keyRelease(KeyEvent.VK_ALT);
		r.keyRelease(KeyEvent.VK_TAB);

		delay(1000);

		while (true) {

			delay(1000);
			// 발주해야되는 양을 사진찍어서 구한다.
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

			// 소모품일떄 프로그램 끈다.
			if (kindString.equals("소모품") || jobsDone == true) {
				if (setFocusToWindowsApp("엄마, 아빠", 0)) {
					copy("테스트");
					paste();
					r.keyPress(KeyEvent.VK_ENTER);
					r.keyRelease(KeyEvent.VK_ENTER);
				} else {
					System.out.println("단톡방이 안켜져있음");
				}
			}

			// 얼마나 발주해야되는지 개수를 구함
			instance.setLanguage("eng");// 영어로 다시 셋팅해야 숫자가 잘 인식됨
			instance.setTessVariable("tessedit_char_whitelist", "-0123456789./");

			totalOrderQTimage = capturedImage.getSubimage(TOTAL_ORDER_QT_X, TOTAL_ORDER_QT_Y, TOTAL_ORDER_QT_WIDTH,
					TOTAL_ORDER_QT_HEIGHT);
			enlargedTotalOrderQTimage = enlargeImage(totalOrderQTimage, TOTAL_ORDER_QT_WIDTH, TOTAL_ORDER_QT_HEIGHT);
			totalOrderQTstring = image2string(instance, enlargedTotalOrderQTimage);

			idx = totalOrderQTstring.indexOf("13");
			refinedTotalOrderQTstring = totalOrderQTstring.substring(idx + 2);
			totalOrderQT = Integer.parseInt(refinedTotalOrderQTstring);

			System.out.println(totalOrderQT);

			if (kindString.equals("꾀=1\n-|") || kindString.equals("식재료선믈세트")) { // 떡
				doType(KeyEvent.VK_F4);
				continue;
			} else if (kindString.equals("밀반빤이스크림") || kindString.equals("과밀굶재소") || kindString.equals("0-이스드림크")
					|| kindString.equals("상품권")) {

				passClickAmount = totalOrderQT / 10 + 1;

				for (int i = 0; i < passClickAmount; i++) {
					doType(KeyEvent.VK_F4);
				}
				continue;
			}

			// 메인 반복문
			for (int i = 0; i < totalOrderQT; i++) {

				// 값들 초기화
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

				// for문 안에 스크린샷 찍는거를 한번 중지시켜서 프로그램 속도를 올린다.

				// 화면 캡쳐
				capturedImage = r.createScreenCapture(screenRect);

				averageSoldImage = capturedImage.getSubimage(AVERAGE_SOLD_FIRST_X, AVERAGE_SOLD_FIRST_Y,
						AVERAGE_SOLD_WIDTH, AVERAGE_SOLD_HEIGHT);

				// averageSold에 값 채워넣음
				enlargedAverageSoldImage = enlargeImage(averageSoldImage, AVERAGE_SOLD_WIDTH, AVERAGE_SOLD_HEIGHT);
				// System.out.println(image2string(instance, enlargedAverageSoldImage));
				averageSold = Float.parseFloat(image2string(instance, enlargedAverageSoldImage));

				// 평균판매가 0이면 빨리넘긴다.
				if (averageSold <= 0) {
					doType(KeyEvent.VK_DOWN);
					System.out.println("averageSold <= 0 pass");
					continue;
				} else if (averageSold >= 10) {
					averageSold = averageSold / 10; // OCR 갑시 튀는현상 방지 2.7을 27로 인식해서 수정하는 코드작성함.
					// ystem.out.println("hit");
				}

				currentStockImage = capturedImage.getSubimage(CURRENT_STOCK_FIRST_X,
						CURRENT_STOCK_FIRST_Y + (CURRENT_STOCK_INTERVAL * (i % 10)), CURRENT_STOCK_WIDTH,
						HEIGHT_ABOVE_UNDERLINE);
				// currentStockImage 이미지프로세싱
				// 버퍼이미지를 Mat으로 변환
				src = BufferedImage2Mat(currentStockImage);
				// 바이너리 이미지로 변환
				if (src.channels() == 3) {
					Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
				} else {
					gray = src;
				}
				Core.bitwise_not(gray, gray);
				Imgproc.adaptiveThreshold(gray, bw, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 15, -2);
				// Mat을 버퍼이미지로 변환
				currentStockImage = Mat2BufferedImage(bw);

				// 화면캡쳐한 이미지를 파일로 출력
				/*
				 * File outputfile = new File("currentStockImage.bmp"); try {
				 * ImageIO.write(currentStockImage, "bmp", outputfile); } catch (IOException e)
				 * { e.printStackTrace(); }
				 */

				currentStock = Integer.parseInt(image2string(instance, currentStockImage));

				//System.out.println(currentStock);
				
				// 현재고와 평균판매를 비교해서 빨리넘길거는 빨리넘김
				if (kindString.equals("담배") && currentStock > averageSold * 10 / 2) {
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

				// 이미지 판독 후 값들을 채워넣음.
				multipliedNum = Integer.parseInt(image2string(instance, multipliedNumImage));

				// multipliedNum값이 1인데 7이나 17로 인식할떄가 있음
				/*
				 * if (multipliedNum == 7 || multipliedNum == 17) { multipliedNum = 1; }
				 */

				// 사진을 찍은 후 연산을 해서 totalOrderedNum를 구한다.
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

				// 사진을 찍은 후 연산을 해서 totalDeliveredQt를 구한다.

				// 납품 루프 스타팅 포인트 설정
				switch (kindString) {
				case "빰":
				case "디저트":
				case "육카좀류":
				case "놈산식재료":
				case "죽수산식재료":
				case "반잔류":
				case "대공즈서시\n:l:>-l -l-l":
				case "과밀야재음료":
				case "요구르트":
				case "다다\n-「1-「":// 우유
				case "전통주":
					// System.out.println("milk");
					// expireDate값을 가져온다.
					expiredDateImage = capturedImage.getSubimage(EXPIRED_DATE_X,
							EXPIRED_DATE_Y + (EXPIRED_DATE_INTERVAL * (i % 10)), EXPIRED_DATE_WIDTH,
							EXPIRED_DATE_HEIGHT);
					expireDate = Integer.parseInt(image2string(instance, expiredDateImage));
					break;
				}

				// 발주날 다음날에 들어오는 상품종류는 deliveredQTloopStart + 1을 한다. 발주날과 납품날이 같은거는 건드릴 필요 없다.
				// 발주날과 납품날이 같은 것들:

				// 발주한 다음날 들어오는 경우: 비스켓/쿠키 스낵류 껌은이틀어긋남? 캔디이틀어긋남 마른안주류1,2어긋섞임 농상식재료 조미료류

				// 둘가지 경우가 섞여있는 경우: 빵(편스토랑이 어긋나있음) 디저트 육가공류 축수산식재료

				// 모름: RI아이스크림

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

				// 중분류에 따라서 발주공식이 나뉜다.
				//System.out.println(kindString);
				
				switch (kindString) {

				case "전기면료":
					// 입수 1또는 1이상
					// 유통기한 10이상
					if (multipliedNum == 1) {
						if ((int) (averageSold * 10) == 1) {// averageSold가 정확히는 0.1이 아님. 그래서 averageSold == 0.1안먹힘. 소수점
							// 버릴려고 이짓함.
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

				case "목옥세면":
					// 입수는 1또는 10
					// 유통기한은 모두 10초과
					if (multipliedNum == 1) {
						inputOrder = (int) (averageSold * 10 - currentStock - futrueDeliveryQt);
					}

					if (multipliedNum == 10) {
						inputOrder = 0;
					}

					break;
				case "낙샨느뇨폐근\n--|-「「": // 스낵류
					// 입수는 1또는 1이상
					// 유통기한은 전부 10이상
					if (multipliedNum == 1) {
						if (averageSold >= 0.7) {
							averageSold = 0.7f;
						}
						inputOrder = (int) (averageSold * 10 - currentStock - futrueDeliveryQt);
					} else if (multipliedNum > 1 && (int) (averageSold * 10) > 1 && currentStock <= 2) {
						inputOrder = 1;
					}

					break;

				case "전 자담배":
					// 입수는 10
					// 유통기한 무한대
					toBeOrderedQt = (averageSold * 10 * 2 / 3) - currentStock - futrueDeliveryQt;

					inputOrder = (int) Math.ceil(toBeOrderedQt / multipliedNum);

					break;

				case "완구류":
				case ":l 륵근\n:††「": // 문구류
				case "음료선믈세트":
				case "와인":
				case "0=7렸\n타-「": // 양주
					// 입수 1또는 1이상
					// 유통기한 다 10이상
					if (multipliedNum == 1) {
						inputOrder = (int) (averageSold * 10 - currentStock - futrueDeliveryQt);
					} else if (multipliedNum > 1) {
						toBeOrderedQt = averageSold * 10 / 2 - currentStock - futrueDeliveryQt;
						inputOrder = (int) Math.ceil(toBeOrderedQt / multipliedNum);
					}

					break;
				case "전통주":
					// 입수 1또는 1이상
					// 유통기한 10이하인 것들 존재
					if (expireDate > 10) {
						toBeOrderedQt = averageSold * 10 * 2 / 3 - currentStock - futrueDeliveryQt;
					} else if (expireDate <= 10) {
						toBeOrderedQt = averageSold * expireDate * 3 / 4 - currentStock - futrueDeliveryQt;
					}

					inputOrder = (int) Math.ceil(toBeOrderedQt / multipliedNum);

					break;

				case "타 갸폐-줌-뚝뚝\n뉘랍다판":
				case "커피뜻류":
					// 입수 1또는 1이상
					// 유통기한 10이상
					if (multipliedNum == 1) {

						inputOrder = (int) (averageSold * 10 - currentStock - futrueDeliveryQt);

					} else if (multipliedNum > 1) {
						toBeOrderedQt = averageSold * 10 * 2 / 3 - currentStock - futrueDeliveryQt;
						inputOrder = (int) Math.ceil(toBeOrderedQt / multipliedNum);
					}

					break;
				case "조미료류":
					// 입수 1또는 1이상
					// 유통기한 10이상
					if (multipliedNum == 1) {
						if ((int) (averageSold * 10) == 1) {// averageSold가 정확히는 0.1이 아님. 그래서 averageSold == 0.1안먹힘. 소수점
							// 버릴려고 이짓함.
							inputOrder = (int) (2 - currentStock - futrueDeliveryQt);
						} else if ((int) (averageSold * 10) > 1) {
							inputOrder = (int) (averageSold * 10 - currentStock - futrueDeliveryQt);
						}
					} else if (multipliedNum > 1) {
						toBeOrderedQt = averageSold * 10 / 2 - currentStock - futrueDeliveryQt;
						inputOrder = (int) Math.ceil(toBeOrderedQt / multipliedNum);
					}

					break;

				case "싯노호토\n-l--「": // 소주
					// 입수는 전부 1 이상
					// 유통기한은 무한대..
					if (multipliedNum > 1) {
						toBeOrderedQt = averageSold * 10 / 2 - currentStock - futrueDeliveryQt;

						inputOrder = (int) Math.ceil(toBeOrderedQt / multipliedNum);
					}

					if (multipliedNum == 30) {
						inputOrder = 0;
					}

					break;

				case "꾀폐컷렸\n-|-「":// 맥쥬
					// 입수는 다 1초과
					// 유통기한은 다 10이상
					if (multipliedNum > 1) {
						toBeOrderedQt = averageSold * 10 * 3 / 4 - currentStock - futrueDeliveryQt;

						inputOrder = (int) Math.ceil(toBeOrderedQt / multipliedNum);
					}

					break;
				case "미므\n근딘": // 얼음
					// 입수 1이상인것 있음
					// 유효기간은 다 10이상
					if (multipliedNum == 1) {
						if ((int) (averageSold * 10) == 1) {// averageSold가 정확히는 0.1이 아님. 그래서 averageSold == 0.1안먹힘. 소수점
							// 버릴려고 이짓함.
							inputOrder = (int) (2 - currentStock - futrueDeliveryQt);
						} else if ((int) (averageSold * 10) > 1) {
							inputOrder = (int) (averageSold * 10 - currentStock - futrueDeliveryQt);
						}
					} else if (multipliedNum > 1) {
						toBeOrderedQt = averageSold * 10 / 2 - currentStock - futrueDeliveryQt;

						inputOrder = (int) Math.ceil(toBeOrderedQt / multipliedNum);
					}

					break;
				case "갸폐낙노\n힌-「":// 생수
					// 입수가 1인거는 하나있고 나머지는 다 1 이상이다.
					// 유통기한은 다 10 이상

					if (multipliedNum == 1) {
						inputOrder = (int) (averageSold * 10 * 3 / 4 - currentStock - futrueDeliveryQt);
					} else if (multipliedNum > 1 && (currentStock + futrueDeliveryQt) <= averageSold * 10 / 2) {
						inputOrder = 1;
					}

					break;
				case "기능컨감음료":
					// 입수가 1 이상인 것도 있다.
					// 유통기한은 다 10이상이다.
					if (multipliedNum == 1) {
						if ((int) (averageSold * 10) == 1) {// averageSold가 정확히는 0.1이 아님. 그래서 averageSold == 0.1안먹힘. 소수점
							// 버릴려고 이짓함.
							inputOrder = (int) (1 - currentStock - futrueDeliveryQt);
						} else if ((int) (averageSold * 10) > 1) {
							inputOrder = (int) (averageSold * 10 - currentStock - futrueDeliveryQt);
						}
					} else if (multipliedNum > 1 && (currentStock + futrueDeliveryQt) <= averageSold * 10 / 2) {
						inputOrder = 1;
					}
					break;
				case "컨감기능":
				case "폐품\n뫼므":
					// 입수는 10이다.
					// 유통기한 10이상이다.
					if (currentStock <= averageSold * 10 / 2 - futrueDeliveryQt)
						inputOrder = 1;
					break;

				case "요구르트":
				case "다다\n-「1-「":// 우유
					// 입수가 1넘는 것들이 잇다.
					// 유통기한 10이하인 것들이 잇다.
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

				case "틴산음료":
				case "곳음료":// 차음료
				case "커피음료":
					// 입수가 1넘는 것들이 잇다.
					// 유통기한은 다 10이상이다.
					if (multipliedNum == 1) {
						if ((int) (averageSold * 10) == 1) {// averageSold가 정확히는 0.1이 아님. 그래서 averageSold == 0.1안먹힘. 소수점
							// 버릴려고 이짓함.
							inputOrder = (int) (1 - currentStock - futrueDeliveryQt);
						} else if ((int) (averageSold * 10) > 1) {
							inputOrder = (int) (averageSold * 10 * 2 / 3 - currentStock - futrueDeliveryQt);
						}
					} else if (multipliedNum > 1) {
						toBeOrderedQt = averageSold * 10 * 2 / 3  - currentStock - futrueDeliveryQt;

						inputOrder = (int) Math.floor(toBeOrderedQt / multipliedNum);
					}

					break;
				case "과밀야재음료":
					// 입수는 다 1도 있고 1넘어가는 것도 있다.
					// 유통기한은 10이하인것들이 있다.
					// System.out.println("hit");
					if (expireDate == 10) {
						toBeOrderedQt = averageSold * 10 * 2 / 3 - currentStock - futrueDeliveryQt;
					} else if (expireDate < 10) {
						if ((int) (averageSold * 10) == 1) {// averageSold가 정확히는 0.1이 아님. 그래서 averageSold == 0.1안먹힘. 소수점
															// 버릴려고 이짓함.
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

				case "대공즈서시\n:l:>-l -l-l":

					// 입수는 다 1이다.
					// 유통기한은 10이하인것들이 있다.
					if (expireDate >= 10) {
						inputOrder = (int) (averageSold * 10 - currentStock - futrueDeliveryQt);
					}
					if (expireDate <= 30) {
						inputOrder = (int) (averageSold * 10 * 2 / 3 - currentStock - futrueDeliveryQt);
					}
					if (expireDate < 10) {

						if ((int) (averageSold * 10) == 1) {// averageSold가 정확히는 0.1이 아님. 그래서 averageSold == 0.1안먹힘. 소수점
															// 버릴려고 이짓함.
							inputOrder = (int) (1 - currentStock - futrueDeliveryQt);
						} else if ((int) (averageSold * 10) > 1) {
							inputOrder = (int) (averageSold * expireDate - currentStock - futrueDeliveryQt);
						}
					}

					break;

				case "소혐카전":
				case "밉지룩화삼품":
				case "매 완돋품":
				case "파티댈오략돋품":// 파티/오락용품
				case "맥 세서 티":
				case "뫼류돋품":
				case "화장품":
				case "편뫼삼품":
				case "우전돋샬품":// 우천용상품
				case "꽈- 호호\n몸댈†빙돋품":// 홈/주방용품
				case "완전샬비뫼얌품":
				case "씸- 즈^쇄^|\n다돈「 -l-l":
				case "냐도즈서시\n:l:>-l -l-l":
					// 입수는 다 1이다.
					// 유통기한은 다 10이상이다.

					inputOrder = (int) (averageSold * 10 - currentStock - futrueDeliveryQt);
					break;

				case "면끈\n-「「": // 면류
					// 입수는 다 1이상
					// 유통기한은 다 10이상

					toBeOrderedQt = averageSold * 10 / 2 - currentStock - futrueDeliveryQt;

					inputOrder = (int) Math.ceil(toBeOrderedQt / multipliedNum);

					if ((int) (averageSold * 10) == 1 && multipliedNum > 1) {
						inputOrder = 0;
					}

					break;

				case "반잔류":
					// 입수는 다 1이다.
				case "죽수산식재료":

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
				case "육카좀류":
				case "놈산식재료":
					if (expireDate < 10) {
						toBeOrderedQt = (averageSold * expireDate) - currentStock - futrueDeliveryQt;
						if ((averageSold * expireDate) - (int) (averageSold * expireDate) == 0) {// averageSold *
																									// expireDate의 소수점
																									// 자리가
																									// 없을떄
							toBeOrderedQt = toBeOrderedQt - 1;// 하나 덜 발주한다
						}
					} else if (expireDate >= 10) {
						toBeOrderedQt = (averageSold * 10 * 2 / 3) - currentStock - futrueDeliveryQt;
						if ((int) (averageSold * 10) == 1) {
							toBeOrderedQt = 1 - currentStock - futrueDeliveryQt;
						}
					}
					// 소수점자리이하 내림을 해야됨
					inputOrder = (int) Math.floor(toBeOrderedQt / multipliedNum);

					if (multipliedNum > 1) {

						toBeOrderedQt = (averageSold * 10 / 2) - currentStock - futrueDeliveryQt;

						inputOrder = (int) Math.ceil(toBeOrderedQt / multipliedNum);
					}

					break;

				case "빰":// 빵
				case "디저트":

					if (expireDate < 10) {
						toBeOrderedQt = (averageSold * expireDate) - currentStock - futrueDeliveryQt;

						if ((averageSold * expireDate) - (int) (averageSold * expireDate) == 0) {// averageSold *
																									// expireDate의 소수점
																									// 자리가
																									// 없을떄
							toBeOrderedQt = toBeOrderedQt - 1;// 하나 덜 발주한다
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

					// 소수점자리이하 내림을 해야됨
					inputOrder = (int) Math.floor(toBeOrderedQt / multipliedNum);

					// 입수가 1을 넘으면 평균 * 10 / 2 일떄 1을 발주넣는다.
					if (multipliedNum > 1 && (currentStock + futrueDeliveryQt) <= averageSold * 10 / 2) {
						inputOrder = 1;
					}

					break;

				case "비스켓봇쿠키":

					toBeOrderedQt = (averageSold * 10 * 2 / 3) - currentStock - futrueDeliveryQt;
					inputOrder = (int) Math.ceil(toBeOrderedQt / multipliedNum);

					if (multipliedNum > 1 && (currentStock + futrueDeliveryQt) <= averageSold * 10 / 2) {
						inputOrder = 1;
					}

					/*
					 * if (inputOrder >= 7) { inputOrder = 7; }
					 */

					break;

				case "뮈0-0|스크림":// RI아이스크림
				case "힌_르0~7호근\nl- |_-「-「「":

					if (averageSold >= 0.7) {
						averageSold = 0.7f;
					}
					toBeOrderedQt = (averageSold * 10) - currentStock - futrueDeliveryQt;
					inputOrder = (int) Math.floor(toBeOrderedQt / multipliedNum);

					break;

				case "담배":

					toBeOrderedQt = (averageSold * 10 * 2 / 3) - currentStock - futrueDeliveryQt;

					inputOrder = (int) Math.ceil(toBeOrderedQt / multipliedNum);
					/*
					 * System.out.println("toBeOrderedQt: "+toBeOrderedQt+ "averageSold:"
					 * +averageSold + " currentStock: "+currentStock +
					 * " futrueDeliveryQt:"+futrueDeliveryQt ); System.out.println("담배인식2");
					 */
					break;

				case "시리얼":
				case "캔디":
				case "꺼\n그":
				case "조콜릿":
					// 입수는 1또는 1이상
					// 유통기한은 10이상

					if (multipliedNum > 1) {
						if (currentStock <= averageSold * 10 / 2 - futrueDeliveryQt)
							inputOrder = 1;
					} else if (multipliedNum == 1) {
						inputOrder = (int) (averageSold * 10 - currentStock - futrueDeliveryQt);
					}

					break;
				
				default:
					//이클립스로 화면전환
					r.keyPress(KeyEvent.VK_ALT);
					r.keyPress(KeyEvent.VK_TAB);
					r.keyRelease(KeyEvent.VK_ALT);
					r.keyRelease(KeyEvent.VK_TAB);
					System.out.println("데이터에 없는항목임");
					System.exit(0);
				}

				System.out.println("totalOrderedNum: " + totalOrderedNum + "multipliedNum: " + multipliedNum
						+ " totalDeliveredQt: " + totalDeliveredQt);

				System.out.println("toBeOrderedQt: " + toBeOrderedQt + "averageSold:" + averageSold + " currentStock: "
						+ currentStock + " expireDate: " + expireDate + " futrueDeliveryQt: " + futrueDeliveryQt);

				// 발주량이 0이면 패스한다.
				if (inputOrder <= 0) {
					doType(KeyEvent.VK_DOWN);
					System.out.println("inputOrder <= 0");
					continue;
				}else if(inputOrder >= 9) {
					inputOrder = 9;
				}

				String stringInputOrder = String.valueOf(inputOrder);

				System.out.println(stringInputOrder);

				// 발주값을 발주프로그램에 쓴다.
				type(stringInputOrder);

				// 한칸 아래로 이동
				doType(KeyEvent.VK_DOWN);
				
				if(todayInt == 7 && kindString == "다다\n-「1-「" && i == totalOrderQT - 1 ) {//우유
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
				// 값을 잘못 인식하면 수정한다.
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
		// 읽어들인 사진을 보여줌
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
