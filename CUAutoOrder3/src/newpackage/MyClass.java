package newpackage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

public class MyClass {

	static int totalOrderedNum = 0;
	private static int totalDeliveredQt = 0;
	private static int expireDate;
	private static int multipliedNum;
	private static int currentStock;
	private static int futrueDeliveryQt;
	private static float averageSold;
	private static float toBeOrderedQt;
	private static int inputOrder;
	private static String kindString;
	private static int totalOrderQT;
	private static String totalOrderQTStr;
	private static int idx;
	private static int passClickAmount;
	private static int orderQTaPage;
	private static boolean heatFirstOrderedNum;
	private static int deliveredQTloopStart;
	private static int orderedNumADay;
	private static int zeroOrderCount;
	private static String deliveredQtADay;
	private static String ProductNameColor;
	private static int index = 0;
	private static List<File> snapShotFiles;
	private static Robot robot;
	private static String FirstProductName = "1";
	private static String eventNum;

	public static void continueAlertPopUp(WebDriverWait wait) {

		try { // Wait 10 seconds till alert is present ;
			Alert alert = wait.until(ExpectedConditions.alertIsPresent());

			// Accepting alert.
			alert.dismiss();
			System.out.println("Accepted the alert successfully.");
		} catch (Throwable e) {
			System.err.println("Error came while waiting for the alert popup. " + e.getMessage());
		}
	}

	public static void main(String args[]) throws Throwable {

		robot = new Robot();

		WebDriverManager.chromedriver().setup();

		// System.setProperty("webdriver.gecko.driver", "/usr/bin/geckodriver");
		WebDriver driver = new ChromeDriver();

		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

		driver.get("https://store.bgfretail.com/websrc/deploy/index.html");
		driver.manage().window().maximize();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));

		// 브라우져지원종료 안내창 클릭
		// driver.findElement(By.id("mainframe.HFrameSet00.LoginFrame.ExplorerStop.form.btn_close:icontext")).click()

		wait.until(ExpectedConditions
				.elementToBeClickable(By.id("mainframe.HFrameSet00.LoginFrame.form.div_login.form.edt_id:input")))
				.sendKeys("32382");

		WebElement passwordElement = wait.until(ExpectedConditions
				.elementToBeClickable(By.id("mainframe.HFrameSet00.LoginFrame.form.div_login.form.edt_pw:input")));
		passwordElement.click();
		passwordElement.clear();
		passwordElement.sendKeys("911124");

		// 로그인 확인 버튼 누름
		wait.until(ExpectedConditions
				.elementToBeClickable(By.id("mainframe.HFrameSet00.LoginFrame.form.div_login.form.btn_login"))).click();

		// 초기비밀번호입니다. 팝업 확인
		try {
			// Wait 10 seconds till alert is present
			Alert alert = wait.until(ExpectedConditions.alertIsPresent());

			// Accepting alert.
			alert.dismiss();
			System.out.println("Accepted the alert successfully.");
		} catch (Throwable e) {
			System.err.println("Error came while waiting for the alert popup. " + e.getMessage());
		}

		// 다음에 변경 클릭
		wait.until(ExpectedConditions
				.elementToBeClickable(By.id("mainframe.HFrameSet00.LoginFrame.cmmPwChange.form.btn_nextChange")))
				.click();

		// 무선랜 경고창 팝업 닫기 버튼을 누름
		wait.until(ExpectedConditions.elementToBeClickable(
				By.id("mainframe.HFrameSet00.VFrameSet00.FrameSet.WorkFrame.STZZ120_P0.form.btn_close"))).click();

		// 발주 버튼 클릭
		wait.until(ExpectedConditions
				.elementToBeClickable(By.id("mainframe.HFrameSet00.VFrameSet00.FrameSet.WorkFrame.form.btn_ord")))
				.click();
		
		for (int j = 0; j < 4; j++) {
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_SUBTRACT);
			robot.keyRelease(KeyEvent.VK_SUBTRACT);
			robot.keyRelease(KeyEvent.VK_CONTROL);
		}

		// SC추천상품발주창 닫기 클릭
		// driver.findElement(By.id("mainframe.HFrameSet00.VFrameSet00.FrameSet.STBJ300_M0.fn_recommItemPop.form.btn_x")).click();
		try {
			wait.until(ExpectedConditions.elementToBeClickable(
					By.id("mainframe.HFrameSet00.VFrameSet00.FrameSet.STBJ300_M0.fn_recommItemPop.form.btn_x")))
					.click();
		} catch (Exception e) {

		}
		// 호빵창 닫기
		// driver.findElement(By.id("mainframe.HFrameSet00.VFrameSet00.FrameSet.STBJ300_M0.fn_recommItemPop.form.btn_x")).click();

		// 발주 이어서 하겟냐는 팝업창 취소버튼 클릭
		continueAlertPopUp(wait);// 계속 이어서할거냐는 팝업 확인누르기

		

		// 발주 선택 표의 맨 아래 왼쪽에 있는을 누름. 보통 빵인데 아닐 경우도 있음

		/*
		 * driver.findElement(By.id(
		 * "mainframe.HFrameSet00.VFrameSet00.FrameSet.STBJ300_M0.fn_initBalju.form.gdList1.body.gridrow_9.cell_9_1"
		 * )) .click();
		 */

		// 중분류 코드 클릭 후 12 입력. 빵이 12임
		// 비스켓/쿠키 15
		// 음료선물세트 54
		// 초콜릿 19
		driver.findElement(By
				.id("mainframe.HFrameSet00.VFrameSet00.FrameSet.STBJ300_M0.fn_initBalju.form.Div02.form.edMidCd:input"))
				.click();
		driver.findElement(By
				.id("mainframe.HFrameSet00.VFrameSet00.FrameSet.STBJ300_M0.fn_initBalju.form.Div02.form.edMidCd:input"))
				.sendKeys("72");

		// 선택 버튼 클릭
		driver.findElement(By.id("mainframe.HFrameSet00.VFrameSet00.FrameSet.STBJ300_M0.fn_initBalju.form.btn_select"))
				.click();

		while (true) {
			// 중분류
			kindString = wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(By.id(
					"mainframe.HFrameSet00.VFrameSet00.FrameSet.STBJ300_M0.form.div_workForm.form.div_work.form.Div10.form.stMidNm:input"))))
					.getAttribute("value");
			// System.out.println(kindString);
			
			// 소모품일때 카톡보내고 프로그램 끈다
			if (kindString.equals("소모품")) {

				if (setFocusToWindowsApp("엄마, 아빠", 0)) {

					robot.keyPress(KeyEvent.VK_ENTER);
					robot.keyRelease(KeyEvent.VK_ENTER);

					copyToClipBoard("다넛어~ 오늘 등록한 컷상품은" + index + "개임");
					paste(robot);

					robot.keyPress(KeyEvent.VK_ENTER);
					robot.keyRelease(KeyEvent.VK_ENTER);

					for (int a = 0; a < snapShotFiles.size(); a++) {

						copyToClipboard(snapShotFiles.get(a));
						paste(robot);

						robot.keyPress(KeyEvent.VK_ENTER);
						robot.keyRelease(KeyEvent.VK_ENTER);
					}

				} else {
					System.out.println("단톡방이 안켜져있음");
				}
				driver.quit();
				System.exit(0);
			}

			// 총물건 개수를 구해서 몇번 다음버튼을 눌러야될지 결정한다.
			totalOrderQTStr = wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(By
					.id("mainframe.HFrameSet00.VFrameSet00.FrameSet.STBJ300_M0.form.div_workForm.form.div_work.form.Div10.form.stBJText:input"))))
					.getAttribute("value");
			// System.out.println(totalOrderQTStr);

			idx = totalOrderQTStr.indexOf("/");
			totalOrderQTStr = totalOrderQTStr.substring(idx + 2);
			// System.out.println(totalOrderQTStr);

			totalOrderQT = Integer.parseInt(totalOrderQTStr);

			if (totalOrderQT % 10 != 0) {
				passClickAmount = totalOrderQT / 10 + 1;
			} else {
				passClickAmount = totalOrderQT / 10;
			}

			// 떡이나 상품권등등은 빨리 넘긴다.
			if (kindString.equals("떡") || kindString.equals("식재료선믈세트")) { // 떡
				nextBtnClick(driver);
				continue;
			} else if (kindString.equals("일반아이스크림") || kindString.equals("과일/채소") // || kindString.equals("0-이스드림크")
					|| kindString.equals("상품권")) {
				// || kindString.equals("담배") || kindString.equals("전자담배"))

				for (int i = 0; i < passClickAmount; i++) {
					nextBtnClick(driver);
				}
				continue;
			}

			for (int j = 0; j < passClickAmount; j++) {

				// 다음버튼을 마지막으로 누르는 루프가 돌떄 한 화면의 물건들 발주수량을 구한다.
				if (j >= passClickAmount - 1) {
					orderQTaPage = totalOrderQT % 10;
				} else {
					orderQTaPage = 10;
				}

				if (orderQTaPage <= 0)
					orderQTaPage = 10;

				// 한페이지 넘어갈떄마다 한페이지 로딩 다 될때까지 기다린다. 첫번쨰 물건이름이 바뀌면 로딩이 끝난것으로 간주한다.

				/*
				 * while (true) { String preString = FirstProductName; FirstProductName =
				 * driver.findElement(By.id(
				 * "mainframe.HFrameSet00.VFrameSet00.FrameSet.STBJ300_M0.form.div_workForm.form.div_work.form.gdList.body.gridrow_0.cell_0_0"
				 * )) .getAttribute("textContent"); if (!preString.equals(FirstProductName)) {
				 * break; } Thread.sleep(10); }
				 */

				// 한페이지 넘어갈떄마다 한페이지 로딩 다 될때까지 기다린다. 첫번쨰 현재고가 뜨면 로딩이 끝난것으로 간주한다.
				// 브라우저를 최대 20초까지 기다린다.
				// wait.until(ExpectedConditions
				// .visibilityOfElementLocated(By.id("mainframe.HFrameSet00.VFrameSet00.FrameSet.STBJ300_M0.form.div_workForm.form.div_work.form.gdList.body.gridrow_0.cell_0_5")));

				// driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
				// driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

				// 상품명 로딩 기다리기
				wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(By.id(
						"mainframe.HFrameSet00.VFrameSet00.FrameSet.STBJ300_M0.form.div_workForm.form.div_work.form.gdList.body.gridrow_0.cell_0_0"))));

				// 유통기한 로딩 기다리기
				wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(By.id(
						"mainframe.HFrameSet00.VFrameSet00.FrameSet.STBJ300_M0.form.div_workForm.form.div_work.form.gdList.body.gridrow_0.cell_0_3"))));

				// 현재고 로딩 기다리기
				wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(By.id(
						"mainframe.HFrameSet00.VFrameSet00.FrameSet.STBJ300_M0.form.div_workForm.form.div_work.form.gdList.body.gridrow_0.cell_0_5"))));

				// 입수 로딩 기다리기
				wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(By.id(
						"mainframe.HFrameSet00.VFrameSet00.FrameSet.STBJ300_M0.form.div_workForm.form.div_work.form.gdList.body.gridrow_0.cell_0_6"))));

				System.out.println(orderQTaPage);

				// 한페이지 돌림
				for (int i = 0; i < orderQTaPage; i++) {

					// 값들 초기화
					orderedNumADay = 0;
					totalOrderedNum = 0;
					averageSold = 0;
					currentStock = 0;
					multipliedNum = 0;
					deliveredQtADay = "0";
					totalDeliveredQt = 0;
					futrueDeliveryQt = 0;
					toBeOrderedQt = 0;
					inputOrder = 0;
					idx = 0;
					expireDate = 0;
					zeroOrderCount = 0;
					heatFirstOrderedNum = false;
					// wordIsBlack = false;

					// 상품명을 구한다.
					/*
					 * String productName = driver.findElement(By.id(
					 * "mainframe.HFrameSet00.VFrameSet00.FrameSet.STBJ300_M0.form.div_workForm.form.div_work.form.gdList.body.gridrow_"+
					 * i +".cell_"+ i +"_0")) .getAttribute("textContent");
					 *
					 * //System.out.println(productName);
					 */
					ProductNameColor = driver.findElement(By.id(
							"mainframe.HFrameSet00.VFrameSet00.FrameSet.STBJ300_M0.form.div_workForm.form.div_work.form.gdList.body.gridrow_"
									+ i + ".cell_" + i + "_0"))
							.getAttribute("class");

					// 글자색이 하늘색인것은 넘긴다(컷상품)
					if (ProductNameColor.contains("darkcyanColor")) {
						continue;
					}

					// 배수칸 마우스 클릭
					if (i != 0) {
						wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(By.id(
								"mainframe.HFrameSet00.VFrameSet00.FrameSet.STBJ300_M0.form.div_workForm.form.div_work.form.gdList.body.gridrow_"
										+ i + ".cell_" + i + "_7"))))
								.sendKeys(Keys.ENTER);
					}

					// 평균판매 구한다.
					String averageSoldStr = driver.findElement(By.id(
							"mainframe.HFrameSet00.VFrameSet00.FrameSet.STBJ300_M0.form.div_workForm.form.div_work.form.grd_summ.body.gridrow_2.cell_2_8"))
							.getAttribute("textContent");

					if (averageSoldStr.equals(""))
						averageSoldStr = "0";

					averageSold = Float.valueOf(averageSoldStr);
					// System.out.println(averageSold);

					// 평균판매가 0이면 빨리넘긴다.
					if (averageSold <= 0) {
						// System.out.println("averageSold <= 0 pass");
						continue;
					}

					// 현재고
					currentStock = Integer.valueOf(driver.findElement(By.id(
							"mainframe.HFrameSet00.VFrameSet00.FrameSet.STBJ300_M0.form.div_workForm.form.div_work.form.gdList.body.gridrow_"
									+ i + ".cell_" + i + "_5"))
							.getText());

					// System.out.println(currentStock);

					// 현재고와 평균판매를 비교해서 빨리넘길거는 빨리넘김
					if (kindString.equals("담배") && currentStock > averageSold * 10 * 2 / 3) {
						// System.out.println("pass");
						continue;
					} else if (currentStock >= averageSold * 10) {
						// System.out.println("currentStock >= averageSold * 10 pass ");
						continue;
					}

					// 입수
					multipliedNum = Integer.valueOf(driver.findElement(By.id(
							"mainframe.HFrameSet00.VFrameSet00.FrameSet.STBJ300_M0.form.div_workForm.form.div_work.form.gdList.body.gridrow_"
									+ i + ".cell_" + i + "_6"))
							.getText());

					// 평균판매가 0.1이고 현재고가 0인것들은 쳐냄. 사진캡쳐하고 컷상품등록함
					if (multipliedNum > 1 && (int) (averageSold * 10) <= 3 && !kindString.equals("담배")
							&& !kindString.equals("전자담배")) {
						if (currentStock <= 3) {

							// 컷상품 버튼 클릭
							driver.findElement(By.id(
									"mainframe.HFrameSet00.VFrameSet00.FrameSet.STBJ300_M0.form.div_cmmbtn.form.F_9"))
									.click();

							// 확인을 묻는 팝업창이 뜨면 확인을 클릭
							driver.switchTo().alert().accept();

							index++;
						}
						continue;
					}

					// 하루 발주량 다 더해서 일주일 전체발주량 계산.
					for (int x = 0; x < 7; x++) {
						String orderedNumADayStr = driver.findElement(By.id(
								"mainframe.HFrameSet00.VFrameSet00.FrameSet.STBJ300_M0.form.div_workForm.form.div_work.form.grd_summ.body.gridrow_0.cell_0_"
										+ x))
								.getAttribute("textContent");

						if (orderedNumADayStr.equals(""))
							orderedNumADayStr = "0";

						orderedNumADay = Integer.valueOf(orderedNumADayStr);

						if (orderedNumADay != 0 && heatFirstOrderedNum == false) {
							deliveredQTloopStart = x;
							heatFirstOrderedNum = true;
						}
						if (orderedNumADay == 0) {
							zeroOrderCount++;
						}

						totalOrderedNum += Integer.valueOf(orderedNumADayStr);
						// System.out.println(orderedNumADay);
					}
					// System.out.println();
					// System.out.println(totalOrderedNum);

					switch (kindString) {
					case "빵":
					case "디저트":
					case "육가공류":
					case "농산식재료":
					case "축수산식재료":
					case "반찬류":
					case "냉장즉석식":
					case "과일야채음료":
					case "요구르트":
					case "우유":
					case "전통주":

						// 유효기간
						expireDate = Integer.valueOf(driver.findElement(By.id(
								"mainframe.HFrameSet00.VFrameSet00.FrameSet.STBJ300_M0.form.div_workForm.form.div_work.form.gdList.body.gridrow_"
										+ i + ".cell_" + i + "_3"))
								.getText().replace(",", ""));

						// System.out.println("입수: " + multipliedNum + " 현재고: " + currentStock + " 유효기간:
						// " + expiredDate);
						break;
					}

					// 하루 납품량 다 더해서 일주일 토탈 납품량 계산.
					if (zeroOrderCount < 7) {
						for (int y = deliveredQTloopStart; y < 7; y++) {
							deliveredQtADay = driver.findElement(By.id(
									"mainframe.HFrameSet00.VFrameSet00.FrameSet.STBJ300_M0.form.div_workForm.form.div_work.form.grd_summ.body.gridrow_1.cell_1_"
											+ y))
									.getAttribute("textContent");

							if (deliveredQtADay.equals(""))
								deliveredQtADay = "0";

							totalDeliveredQt += Integer.valueOf(deliveredQtADay);
							// System.out.println(deliveredQtADay);
						}
					}
					// System.out.println(totalDeliveredQt);

					futrueDeliveryQt = totalOrderedNum * multipliedNum - totalDeliveredQt;

					// 중분류에 따라서 발주공식이 나뉜다.
					// System.out.println(kindString);

					if (futrueDeliveryQt <= 0)
						futrueDeliveryQt = 0;

					switch (kindString) {

					case "아이스드링크":
						// 입수는 8 또는 10
						// 유통기한 10이상
						toBeOrderedQt = averageSold * 10 / 2 - currentStock - futrueDeliveryQt;
						inputOrder = (int) Math.ceil(toBeOrderedQt / multipliedNum);

						break;

					case "전기연료":
						// 입수 1또는 1이상
						// 유통기한 10이상
						if (multipliedNum == 1) {
							if ((int) (averageSold * 10) == 1) {// averageSold가 정확히는 0.1이 아님. 그래서 averageSold == 0.1안먹힘.
								// 소수점
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

					case "목욕세면":
						// 입수는 1또는 10
						// 유통기한은 모두 10초과
						if (multipliedNum == 1) {
							inputOrder = (int) (averageSold * 10 - currentStock - futrueDeliveryQt);
						}

						if (multipliedNum == 10) {
							inputOrder = 0;
						}

						break;
					case "스낵류":
						// 입수는 1또는 1이상
						// 유통기한은 전부 10이상
						if (multipliedNum == 1) {
							if (averageSold >= 0.7) {
								averageSold = 0.7f;
							}
							inputOrder = (int) (averageSold * 10 - currentStock - futrueDeliveryQt);
						} else /*
								 * if (multipliedNum > 1 && currentStock <= 2) { inputOrder = 1; }
								 *
								 * break;
								 */if (multipliedNum > 1) {
							toBeOrderedQt = averageSold * 10 / 2 - currentStock - futrueDeliveryQt;

							inputOrder = (int) Math.ceil(toBeOrderedQt / multipliedNum);
						}

						break;

					case "전자담배":
						// 입수는 10
						// 유통기한 무한대
						toBeOrderedQt = (averageSold * 10 * 2 / 3) - currentStock - futrueDeliveryQt;

						inputOrder = (int) Math.ceil(toBeOrderedQt / multipliedNum);

						break;

					case "완구류":
					case "문구류":
					case "음료선물세트":
						// 입수 1또는 1이상
						// 유통기한 다 10이상
						if (multipliedNum == 1) {
							inputOrder = (int) (averageSold * 10 - currentStock - futrueDeliveryQt);
						} else if (multipliedNum > 1) {
							toBeOrderedQt = averageSold * 10 / 2 - currentStock - futrueDeliveryQt;
							inputOrder = (int) Math.ceil(toBeOrderedQt / multipliedNum);
						}

						break;

					case "와인":
					case "양주":
						// 입수 1또는 1이상
						// 유통기한 다 10이상
						if (multipliedNum == 1) {
							if ((int) (averageSold * 10) == 1) {// averageSold가 정확히는 0.1이 아님. 그래서 averageSold == 0.1안먹힘.
								// 소수점
								// 버릴려고 이짓함.
								inputOrder = (int) (1 - currentStock - futrueDeliveryQt);
							} else if ((int) (averageSold * 10) > 1) {
								inputOrder = (int) (averageSold * 10 * 2 / 3 - currentStock - futrueDeliveryQt);
							}
						} else if (multipliedNum > 1) {
							toBeOrderedQt = averageSold * 10 / 2 - currentStock - futrueDeliveryQt;
							inputOrder = (int) Math.ceil(toBeOrderedQt / multipliedNum);
						}

						break;

					case "전통주":
						// 입수 1또는 1이상
						// 유통기한 10이하인 것들 존재
						if ((int) (averageSold * 10) > 1) {
							if (expireDate > 10) {
								toBeOrderedQt = averageSold * 10 * 2 / 3 - currentStock - futrueDeliveryQt;
							} else if (expireDate <= 10) {
								toBeOrderedQt = averageSold * expireDate * 3 / 4 - currentStock - futrueDeliveryQt;
							}

							inputOrder = (int) Math.ceil(toBeOrderedQt / multipliedNum);
						}
						break;

					case "위생용품":
					case "커피차류":
						// 입수 1또는 1이상
						// 유통기한 10이상
						if (multipliedNum == 1) {
							if ((int) (averageSold * 10) == 1) {// averageSold가 정확히는 0.1이 아님. 그래서 averageSold == 0.1안먹힘.
								// 소수점
								// 버릴려고 이짓함.
								inputOrder = (int) (1 - currentStock - futrueDeliveryQt);
							} else if ((int) (averageSold * 10) > 1) {
								inputOrder = (int) (averageSold * 10 * 2 / 3 - currentStock - futrueDeliveryQt);
							}
						} else if (multipliedNum > 1) {
							toBeOrderedQt = averageSold * 10 * 2 / 3 - currentStock - futrueDeliveryQt;
							inputOrder = (int) Math.ceil(toBeOrderedQt / multipliedNum);
						}

						break;
					case "조미료류":
						// 입수 1또는 1이상
						// 유통기한 10이상
						if (multipliedNum == 1) {
							if ((int) (averageSold * 10) == 1) {// averageSold가 정확히는 0.1이 아님. 그래서 averageSold == 0.1안먹힘.
								// 소수점
								// 버릴려고 이짓함.
								inputOrder = (int) (1 - currentStock - futrueDeliveryQt);
							} else if ((int) (averageSold * 10) > 1) {
								inputOrder = (int) (averageSold * 10 * 2 / 3 - currentStock - futrueDeliveryQt);
							}
						} else if (multipliedNum > 1) {
							toBeOrderedQt = averageSold * 10 / 2 - currentStock - futrueDeliveryQt;
							inputOrder = (int) Math.ceil(toBeOrderedQt / multipliedNum);
						}

						break;

					case "소주":
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

					case "맥주":
					case "기타주류":
						// 입수는 다 1초과
						// 유통기한은 다 10이상
						if ((int) (averageSold * 10) > 1) {
							toBeOrderedQt = averageSold * 10 * 2 / 3 - currentStock - futrueDeliveryQt;

							inputOrder = (int) Math.ceil(toBeOrderedQt / multipliedNum);
						}
						if (multipliedNum == 20)
							inputOrder = 0;

						break;
					case "얼음":
						// 입수 1이상인것 있음
						// 유효기간은 다 10이상
						if (multipliedNum == 1 && ((int) (averageSold * 10) > 0)) {

							inputOrder = (int) (1 - currentStock - futrueDeliveryQt);

							/*
							 * if ((int) (averageSold * 10) == 1) {// averageSold가 정확히는 0.1이 아님. 그래서
							 * averageSold == 0.1안먹힘. 소수점 // 버릴려고 이짓함. inputOrder = (int) (2 - currentStock
							 * - futrueDeliveryQt); } else if ((int) (averageSold * 10) > 1) { inputOrder =
							 * (int) (averageSold * 10 * 1 / 2 - currentStock - futrueDeliveryQt); }
							 */
						} else if (multipliedNum > 1) {
							toBeOrderedQt = averageSold * 10 / 2 - currentStock - futrueDeliveryQt;

							inputOrder = (int) Math.ceil(toBeOrderedQt / multipliedNum);
						}

						if (inputOrder >= 1)
							inputOrder = 1;

						break;
					case "생수":
						// 입수가 1인거는 하나있고 나머지는 다 1 이상이다.
						// 유통기한은 다 10 이상

						if (multipliedNum == 1) {
							inputOrder = (int) (averageSold * 10 * 3 / 4 - currentStock - futrueDeliveryQt);
						} else if (multipliedNum > 1 && (currentStock + futrueDeliveryQt) <= averageSold * 10 / 2) {
							inputOrder = 1;
						}

						break;
					case "기능건강음료":
						// 입수가 1 이상인 것도 있다.
						// 유통기한은 다 10이상이다.
						if (multipliedNum == 1) {
							if ((int) (averageSold * 10) == 1) {// averageSold가 정확히는 0.1이 아님. 그래서 averageSold == 0.1안먹힘.
								// 소수점
								// 버릴려고 이짓함.
								inputOrder = (int) (1 - currentStock - futrueDeliveryQt);
							} else if ((int) (averageSold * 10) > 1) {
								inputOrder = (int) (averageSold * 10 - currentStock - futrueDeliveryQt);
							}
						} else if (multipliedNum > 1) {
							toBeOrderedQt = averageSold * 10 / 2 - currentStock - futrueDeliveryQt;
							inputOrder = (int) Math.ceil(toBeOrderedQt / multipliedNum);
						}
						break;

					case "의약외품":
						if (multipliedNum == 1) {
							inputOrder = (int) (averageSold * 10 - currentStock - futrueDeliveryQt);
						} else if (multipliedNum > 1) {
							if (currentStock <= averageSold * 10 / 2 - futrueDeliveryQt)
								inputOrder = 1;
						}
						break;
					case "건강기능":
						// 입수는 10이다.
						// 유통기한 10이상이다.
						if (currentStock <= averageSold * 10 / 2 - futrueDeliveryQt)
							inputOrder = 1;
						break;

					case "요구르트":
					case "우유":
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

					case "탄산음료":
					case "차음료":// 차음료
						// 입수가 1넘는 것들이 잇다.
						// 유통기한은 다 10이상이다.
						if (multipliedNum == 1) {
							if ((int) (averageSold * 10) == 1) {// averageSold가 정확히는 0.1이 아님. 그래서 averageSold == 0.1안먹힘.
								// 소수점
								// 버릴려고 이짓함.
								inputOrder = (int) (1 - currentStock - futrueDeliveryQt);
							} else if ((int) (averageSold * 10) > 1) {
								inputOrder = (int) (averageSold * 10 * 2 / 3 - currentStock - futrueDeliveryQt);
							}
						} else if (multipliedNum > 1) {
							toBeOrderedQt = averageSold * 10 / 2 - currentStock - futrueDeliveryQt;

							inputOrder = (int) Math.ceil(toBeOrderedQt / multipliedNum);
						}

						if (inputOrder >= 9)
							inputOrder = 3;

						break;

					case "커피음료":
						// 입수가 1넘는 것들이 잇다.
						// 유통기한은 다 10이상이다.
						if (multipliedNum == 1) {
							if ((int) (averageSold * 10) == 1) {// averageSold가 정확히는 0.1이 아님. 그래서 averageSold == 0.1안먹힘.
								// 소수점
								// 버릴려고 이짓함.
								inputOrder = (int) (1 - currentStock - futrueDeliveryQt);
							} else if ((int) (averageSold * 10) > 1) {
								inputOrder = (int) (averageSold * 10 * 2 / 3 - currentStock - futrueDeliveryQt);
							}
						} else if (multipliedNum > 1) {
							toBeOrderedQt = averageSold * 10 / 2 - currentStock - futrueDeliveryQt;

							inputOrder = (int) Math.ceil(toBeOrderedQt / multipliedNum);

							if (inputOrder >= 2)
								inputOrder = 2;
						}

						if (inputOrder >= 9)
							inputOrder = 3;

						break;
					case "과일야채음료":
						// 입수는 다 1도 있고 1넘어가는 것도 있다.
						// 유통기한은 10이하인것들이 있다.
						// System.out.println("hit");
						if (expireDate > 10) {
							toBeOrderedQt = averageSold * 10 * 2 / 3 - currentStock - futrueDeliveryQt;
						} else if (expireDate <= 10) {
							if ((int) (averageSold * 10) == 1) {// averageSold가 정확히는 0.1이 아님. 그래서 averageSold == 0.1안먹힘.
								// 소수점
								// 버릴려고 이짓함.
								toBeOrderedQt = 1 - currentStock - futrueDeliveryQt;
							} else if ((int) (averageSold * 10) > 1) {
								toBeOrderedQt = averageSold * expireDate * 2 / 3 - currentStock - futrueDeliveryQt;
							}
						}

						inputOrder = (int) Math.floor(toBeOrderedQt / multipliedNum);

						if (multipliedNum > 1 && (currentStock + futrueDeliveryQt) <= averageSold * 10 / 2) {
							inputOrder = 1;
						}

						// System.out.println("after " + inputOrder);
						break;

					case "냉장즉석식":

						// 입수는 다 1이다.
						// 유통기한은 10이하인것들이 있다.
						if (expireDate >= 10) {
							inputOrder = (int) (averageSold * 10 - currentStock - futrueDeliveryQt);
						}
						if (expireDate <= 30) {
							if ((int) (averageSold * 10) == 1) {// averageSold가 정확히는 0.1이 아님. 그래서 averageSold == 0.1안먹힘.
								// 소수점
								// 버릴려고 이짓함.
								inputOrder = (int) (1 - currentStock - futrueDeliveryQt);
							} else {
								inputOrder = (int) (averageSold * 10 * 2 / 3 - currentStock - futrueDeliveryQt);
							}
						}
						if (expireDate < 10) {

							if ((int) (averageSold * 10) == 1) {// averageSold가 정확히는 0.1이 아님. 그래서 averageSold == 0.1안먹힘.
								// 소수점
								// 버릴려고 이짓함.
								inputOrder = (int) (1 - currentStock - futrueDeliveryQt);
							} else if ((int) (averageSold * 10) > 1) {
								inputOrder = (int) (averageSold * expireDate - currentStock - futrueDeliveryQt);
							}
						}

						break;

					case "소형가전":
					case "입지특화상품":
					case "애완용품":
					case "파티/오락용품":
					case "액세서리":
					case "의류용품":
					case "화장품":
					case "편의상품":
					case "우천용상품":
					case "홈/주방용품":
					case "안전상비의약품":
						// 입수는 다 1이다.
						// 유통기한은 다 10이상이다.

						inputOrder = (int) (averageSold * 10 - currentStock - futrueDeliveryQt);

						break;
					case "냉동즉석식":
						// 입수는 다 1이다.
						// 유통기한은 다 10이상이다.

					case "상온즉석식":
						// 입수는 다 1이다.
						// 유통기한은 다 10이상이다.

						if ((int) (averageSold * 10) == 1) {// averageSold가 정확히는 0.1이 아님. 그래서 averageSold == 0.1안먹힘. 소수점
							// 버릴려고 이짓함.
							inputOrder = (int) (1 - currentStock - futrueDeliveryQt);
						} else if ((int) (averageSold * 10) > 1) {
							inputOrder = (int) (averageSold * 10 * 2 / 3 - currentStock - futrueDeliveryQt);
						}

						break;

					case "면류":
						// 입수는 다 1 또는 1이상
						// 유통기한은 다 10이상
						if (multipliedNum > 1) {
							toBeOrderedQt = averageSold * 10 / 2 - currentStock - futrueDeliveryQt;

						} else if (multipliedNum == 1) {
							toBeOrderedQt = averageSold * 10 * 2 / 3 - currentStock - futrueDeliveryQt;
						}

						inputOrder = (int) Math.ceil(toBeOrderedQt / multipliedNum);

						break;

					case "반찬류":
						// 입수는 다 1이다.
					case "축수산식재료":

						if (multipliedNum > 1) {
							if (currentStock <= averageSold * 10 / 2 - futrueDeliveryQt)
								inputOrder = 1;
						} else if (multipliedNum == 1) {
							if ((int) (averageSold * 10) == 1) {// averageSold가 정확히는 0.1이 아님. 그래서 averageSold == 0.1안먹힘.
								// 소수점
								// 버릴려고 이짓함.
								inputOrder = (int) (1 - currentStock - futrueDeliveryQt);
							} else if ((int) (averageSold * 10) > 1) {
								inputOrder = (int) (averageSold * 10 * 2 / 3 - currentStock - futrueDeliveryQt);
							}
						}

						if (expireDate < 10) {
							if ((int) (averageSold * 10) == 1) {
								inputOrder = (int) (1 - currentStock - futrueDeliveryQt);
							} else if ((int) (averageSold * 10) > 1) {
								inputOrder = (int) (averageSold * expireDate - currentStock - futrueDeliveryQt);
							}
						}

						break;
					case "육가공류":
					case "농산식재료":
						if (expireDate < 10) {
							toBeOrderedQt = (averageSold * expireDate) - currentStock - futrueDeliveryQt;
							if ((averageSold * expireDate) - (int) (averageSold * expireDate) == 0) {// averageSold *
								// expireDate의
								// 소수점
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

					case "빵":// 빵
					case "디저트":

						if (expireDate < 10) {
							toBeOrderedQt = (averageSold * expireDate) - currentStock - futrueDeliveryQt;

							if ((averageSold * expireDate) - (int) (averageSold * expireDate) == 0) {// averageSold *
								// expireDate의
								// 소수점
								// 자리가
								// 없을떄
								toBeOrderedQt = toBeOrderedQt - 1;// 하나 덜 발주한다
							}

							if (multipliedNum == 1 && (int) (averageSold * 10) == 1) {
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
						if (multipliedNum > 1 && expireDate >= 5
								&& (currentStock + futrueDeliveryQt) <= averageSold * 10 / 2) {
							inputOrder = 1;
						}

						if (inputOrder >= 4)
							inputOrder = 4;

						break;

					case "비스켓/쿠키":

						toBeOrderedQt = (averageSold * 10 * 2 / 3) - currentStock - futrueDeliveryQt;
						inputOrder = (int) Math.ceil(toBeOrderedQt / multipliedNum);

						if (multipliedNum > 1 && (currentStock + futrueDeliveryQt) <= averageSold * 10 / 2) {
							inputOrder = 1;
						}

						/*
						 * if (inputOrder >= 7) { inputOrder = 7; }
						 */

						break;

					case "RI아이스크림":// RI아이스크림
					case "마른안주류":

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
					case "껌":
					case "초콜릿":
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

						System.out.println("데이터에 없는항목임");
						System.exit(0);
					}

					// 발주량이 0이면 패스한다.
					if (inputOrder <= 0) {
						continue;
					} else if (inputOrder >= 9) {
						inputOrder = 9;
					}

					// 배수칸에 inputOrder값을 넣는다.

					// *[@id="mainframe.HFrameSet00.VFrameSet00.FrameSet.STBJ300_M0.form.div_workForm.form.div_work.form.gdList.body.gridrow_i.cell_i_7.celledit:input"]

					wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
							"//*[@id=\"mainframe.HFrameSet00.VFrameSet00.FrameSet.STBJ300_M0.form.div_workForm.form.div_work.form.gdList.body.gridrow_"
									+ i + ".cell_" + i + "_7.celledit:input\"]")))
							.sendKeys(String.valueOf(inputOrder));

					/*
					 * driver.findElement(By.id(
					 * "mainframe.HFrameSet00.VFrameSet00.FrameSet.STBJ300_M0.form.div_workForm.form.div_work.form.gdList.body.gridrow_"
					 * + i + ".cell_" + i + "_7.celledit:input"))
					 * .sendKeys(String.valueOf(inputOrder));
					 */

					// driver.findElement(By.cssSelector(
					// "#mainframe\\.HFrameSet00\\.VFrameSet00\\.FrameSet\\.STBJ300_M0\\.form\\.div_workForm\\.form\\.div_work\\.form\\.gdList\\.body\\.gridrow_"
					// + i + "\\.cell_" + i + "_7")).sendKeys(String.valueOf(inputOrder));

					// alert창(최대발주개수는 ~개입니다)가 뜨면 확인 누름.
					// driver.findElement(By.id("mainframe.HFrameSet00.VFrameSet00.FrameSet.STBJ300_M0.alert01.form.btn_ok")).click();
					// driver.switchTo().alert().accept();
					Thread.sleep(50);// 발주제한 팝업창이 뜰떄까지 시간이 필요함

					if (isAlertPresent(driver)) {
						driver.switchTo().alert().accept();
					}

					// driver.switchTo().alert().accept();

					// driver.findElement(By.id("mainframe.HFrameSet00.VFrameSet00.FrameSet.STBJ300_M0.alert01.form.btn_ok")).sendKeys(Keys.ENTER);

					/*
					 * try { // Wait 10 seconds till alert is present Alert alert =
					 * wait.until(ExpectedConditions.alertIsPresent());
					 *
					 * // Accepting alert. alert.dismiss();
					 * System.out.println("Accepted the alert successfully."); } catch (Throwable e)
					 * { System.err.println("Error came while waiting for the alert popup. " +
					 * e.getMessage()); }
					 */

					// 상품명을 구한다.

					String productName = driver.findElement(By.id(
							"mainframe.HFrameSet00.VFrameSet00.FrameSet.STBJ300_M0.form.div_workForm.form.div_work.form.gdList.body.gridrow_"
									+ i + ".cell_" + i + "_0"))
							.getAttribute("textContent");

					System.out.println("상품명: " + productName + " 현재고: " + currentStock + " 평균판매: " + averageSold
							+ " futureDeliveryQT: " + futrueDeliveryQt + " 입수: " + multipliedNum);
					System.out.println("발주량: " + inputOrder);
					System.out.println();

				}
				// 다음 버튼 클릭
				nextBtnClick(driver);
				// Thread.sleep(180);
			}
		}
	}

	public static void nextBtnClick(WebDriver driver) {
		driver.findElement(By.id("mainframe.HFrameSet00.VFrameSet00.FrameSet.STBJ300_M0.form.div_cmmbtn.form.F_4"))
				.click();
	}

	public static boolean isAlertPresent(WebDriver driver) {
		try {
			driver.switchTo().alert();
			return true;
		} // try
		catch (Exception e) {
			return false;
		} // catch
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
	
	public static void paste(Robot robot) throws AWTException {

		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_V);
	}
	
	public static void copyToClipboard(File file) throws IOException {
		BufferedImage original = ImageIO.read(file);
		BufferedImage newImage = new BufferedImage(original.getWidth(null), original.getHeight(null),
				BufferedImage.TYPE_INT_RGB);

		Graphics2D g = newImage.createGraphics();
		g.drawImage(original, 0, 0, null);
		g.dispose();

		ImageSelection imgSel = new ImageSelection(newImage);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(imgSel, null);
	}
	
	public static void copyToClipBoard(String numOfSnapShots) {

		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

		// Add your elements
		clipboard.setContents(new StringSelection(numOfSnapShots), null);

	}
	
	static class ImageSelection implements Transferable {
		private Image image;

		public ImageSelection(Image image) {
			this.image = image;
		}

		// Returns supported flavors
		public DataFlavor[] getTransferDataFlavors() {
			return new DataFlavor[] { DataFlavor.imageFlavor };
		}

		// Returns true if flavor is supported
		public boolean isDataFlavorSupported(DataFlavor flavor) {
			return DataFlavor.imageFlavor.equals(flavor);
		}

		// Returns image
		public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
			if (!DataFlavor.imageFlavor.equals(flavor)) {
				throw new UnsupportedFlavorException(flavor);
			}
			return image;
		}
	}

	/**
	 * Post a message to a channel your app is in using ID and message text
	 */

}
