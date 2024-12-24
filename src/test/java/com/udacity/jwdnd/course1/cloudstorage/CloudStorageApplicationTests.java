package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.services.HashService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.io.File;
import java.time.Duration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	private FilesPage filesPage;

	private NotesPage notesPage;

	private CredentialsPage credentialsPage;



	@LocalServerPort
	private int port;

	private WebDriver driver;


	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {

		this.driver = new ChromeDriver();

		driver.get("http://localhost:" + port + "/login");

		filesPage = new FilesPage(driver);

		notesPage = new NotesPage(driver);

		credentialsPage = new CredentialsPage(driver);
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2));
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2));

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2));
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}













	@Test
	public void testUnauthorizedAccess() {

		driver.get("http://localhost:" + port + "/home");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));

		wait.until(driver -> driver.getCurrentUrl().contains("login"));

		Assertions.assertTrue(driver.getCurrentUrl().contains("login"));
	}

	@Test
	public void testAnauthorizedLoginAccess() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));

		wait.until(driver -> driver.getCurrentUrl().contains("login"));

		Assertions.assertTrue(driver.getCurrentUrl().contains("login"));
	}

	@Test
	public void testAnauthorizedSignUpAccess() {

		driver.get("http://localhost:" + port + "/signup");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));

		wait.until(driver -> driver.getCurrentUrl().contains("signup"));

		Assertions.assertTrue(driver.getCurrentUrl().contains("signup"));
	}




	@Test
	public void testUnauthorizedAccessToHomeAfterLogOut() {

		driver.get("http://localhost:" + port + "/signup");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));


		wait.until(driver -> driver.getCurrentUrl().contains("signup"));


		doMockSignUp("AccessToHome","AfterLogOut","ATH","123");

		driver.get("http://localhost:" + port + "/login");

		wait.until(driver -> driver.getCurrentUrl().contains("login"));


		doLogIn("ATH", "123");

		wait.until(driver -> driver.getCurrentUrl().contains("home"));

		Assertions.assertTrue(filesPage.isDisplayed());


		filesPage.logout();

		driver.get("http://localhost:" + port + "/home");

		wait.until(driver -> driver.getCurrentUrl().contains("login"));

		Assertions.assertTrue(driver.getCurrentUrl().contains("login"));
	}



	@Test
	public void testCreatedNote() {

		doMockSignUp("Create","Note","TCN","123");

		doLogIn("TCN", "123");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));

		wait.until(driver -> driver.getCurrentUrl().contains("home"));

		notesPage.createNote("Math exam", "I have math exam on Monday");

		Assertions.assertTrue(notesPage.isNoteDisplayed("Math exam", "I have math exam on Monday"));
	}


	@Test
	public void testEditNote() {

		doMockSignUp("Edit","Note","TEN","123");

		doLogIn("TEN", "123");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));

		wait.until(driver -> driver.getCurrentUrl().contains("home"));

		notesPage.createNote("Math exam", "I have math exam on Thursday");

		Assertions.assertTrue(notesPage.isNoteDisplayed("Math exam", "I have math exam on Thursday"));

		notesPage.editNote("Geography exam", "I have geography exam on Thursday");

		Assertions.assertTrue(notesPage.isNoteDisplayed("Geography exam", "I have geography exam on Thursday"));
	}

	@Test
	public void testDeleteNote() {

		doMockSignUp("testDelete","Note","TDN","123");

		doLogIn("TDN", "123");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));

		wait.until(driver -> driver.getCurrentUrl().contains("home"));

		notesPage.createNote("Math exam", "I have math exam on Friday");

		Assertions.assertTrue(notesPage.isNoteDisplayed("Math exam", "I have math exam on Friday"));

		notesPage.deleteNote();

		Assertions.assertFalse(notesPage.isNoteDisplayed("Math exam", "I have math exam on Friday"));
	}


	@Test
	public void testCreatedCredential() {

		doMockSignUp("Create","Credential","TCC","123");


		doLogIn("TCC", "123");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));

		wait.until(driver -> driver.getCurrentUrl().contains("home"));

		credentialsPage.createCredential("www.google.com", "jalal", "jalal2024");

		Assertions.assertTrue(credentialsPage.isCredentialDisplayed("www.google.com", "jalal", "jalal2024"));

		Assertions.assertTrue(credentialsPage.isCredentialPasswordEncrypted("jalal2024"));

	}


	@Test
	public void testEditCredential() {

		doMockSignUp("Edit","Credential","TEC","123");

		doLogIn("TEC", "123");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(200));

		wait.until(driver -> driver.getCurrentUrl().contains("home"));

		credentialsPage.createCredential("www.google.com", "jalal", "jalal2024");

		Assertions.assertTrue(credentialsPage.isCredentialDisplayed("www.google.com", "jalal", "jalal2024"));


		Assertions.assertTrue(credentialsPage.isCredentialPasswordDecrypted("jalal2024"));

		credentialsPage.editCredential("www.yahoo.com", "jalal", "jalal2024");

		Assertions.assertTrue(credentialsPage.isCredentialDisplayed("www.yahoo.com", "jalal", "jalal2024"));


	}

	@Test
	public void testDeleteCredential() {

		doMockSignUp("Delete","Credential","TDC","123");

		doLogIn("TDC", "123");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));

		wait.until(driver -> driver.getCurrentUrl().contains("home"));

		credentialsPage.createCredential("www.google.com", "jalal", "jalal2024");

		Assertions.assertTrue(credentialsPage.isCredentialDisplayed("www.google.com", "jalal", "jalal2024"));


		credentialsPage.deleteCredential();

		Assertions.assertFalse(credentialsPage.isCredentialDisplayed("www.google.com", "jalal", "jalal2024"));
	}



}
