package tests;


import org.openqa.selenium.WebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import pageObjects.DashboardPage;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.TechProfilePage;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SanityTests {
		WebDriver driver;
		
		HomePage objHome;
		LoginPage objLogin;
		DashboardPage objDashboard;
		TechProfilePage objTechProfile;
		
		
	    @BeforeMethod
	    public void initTests() {
	    	WebDriverManager.chromedriver().setup();
	    	driver = new ChromeDriver();
	    	driver.get("https://staging.tecme.io/"); 
	        driver.manage().window().maximize();
	    }
	    
	    @Test(priority=0)
	    public void viewNewsletter() {
	    	objHome = new HomePage(driver);
	    	String modalText = objHome.getNewsletterHeaderText();
	    	
	    	Assert.assertTrue(modalText.equals("Save 10% on your next booking!"));	    	
	    }
	    
	    @Test(priority=1)
	    public void technicianAccountLogin() {
	    	//instantiate Home Page object
	    	objHome = new HomePage(driver);
	    	//deals with alerts on home
	    	objHome.navbacktoHome();
	    	
	    	//stores returned object from homePage
	    	objLogin = objHome.navtoLoginPage();
	    	
	    	String elementText = objLogin.getLoginHeaderText();
	    	Assert.assertTrue(elementText.contains("Welcome Back!"));
	    	
	    	objDashboard = objLogin.navToTechDashboard("yousuf867@gmail.com", "Lebara@123");
	    	//get header text on alert 
	    	elementText = objDashboard.getDashboardAlertHeader();
	    	Assert.assertTrue(elementText.equals("Setup Your Earnings Account"));
	    	
	    	objDashboard.viewDashbaord();
	    	//get title text on dash board
	    	elementText = objDashboard.getDashboardTitleText();
	    	Assert.assertTrue(elementText.contains("Yousuf H."));
	    }
	    
	    @Test(priority=2)
	    public void customerAccountLogin() {
	    	//instantiate Home Page object
	    	objHome = new HomePage(driver);
	    	//deals with alerts on home
	    	objHome.navbacktoHome();
	    	
	    	//stores returned object from homePage
	    	objLogin = objHome.navtoLoginPage();
	    	
	    	String elementText = objLogin.getLoginHeaderText();
	    	Assert.assertTrue(elementText.contains("Welcome Back!"));
	    	
	    	objDashboard = objLogin.navToTechDashboard("hooriya.new.staging@outlook.com", "Hoor1234");

	    	//get title text on dash board	    	
	    	elementText = objDashboard.getCustomerDashboardTitleText();
	    	Assert.assertTrue(elementText.contains("Johnathan D."));
	    }
	    
	    @Test(priority = 3)
	    public void getTechnicianByTechCode() {
	    	objHome = new HomePage(driver);
	    	objTechProfile = objHome.navToTechProfilePage("Hooriya#30421");
	    	String code = objTechProfile.getTechProfileInfo();
	    	Assert.assertEquals(code, "Hooriya#30421");
	    }
    
	    @AfterMethod
	    public void tearDown() {
	    	driver.close();
	    }    
}
