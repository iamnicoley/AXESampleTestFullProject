package Deque.Acceasibility;

import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;



/**
 * Unit test for Deque MarsDemo site.
 */
public class MarsDemoAllyTest 
{
    /**
     * Rigorous Test :-)
     */
	
   WebDriver driver;

	private static final URL scriptUrl = MarsDemoAllyTest.class.getResource("/axe.min.js");
    
	
	/**
	 * Instantiate the WebDriver and navigate to the test site
	 */
	@Before
	public void setUp() {
	
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://dequeuniversity.com/demo/mars");
	}

	
	@Test
	public void DequeMarsDemositeAllyTest() {
			
		JSONObject responseJSON = new AXE.Builder(driver, scriptUrl).analyze();
		JSONArray violations = responseJSON.getJSONArray("violations");

		if (violations.length() == 0) {
			assertTrue("No violations found", true);
		} else {
			System.out.println(responseJSON.toString());
			AXE.writeResults("DequeMarsDemositeAllyTest",responseJSON);
			assertTrue(AXE.report(violations), false);
		}
	}
	
	@Test
	public void MarsDemoPageLoad() {
		
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@class,'container')]")).isDisplayed());
		
	}
	
	@Test
	public void RadioButtonCheck() {
		List<WebElement> radioCount = driver.findElements(By.xpath("//div[contains(@class, 'span-half')]//input[@type='radio']"));
		if(radioCount.size()==5) {
			assertTrue("Total 5 radio buttons", true);
		} else{
			System.out.println("Radio button is NOT 5. Actual size is "+radioCount.size());
		}
	}
	
	
	@Test
	public void AddAnotherTraveler() {
		int initTraveler = driver.findElements(By.xpath("//div[contains(@class, 'passenger')]")).size();
		driver.findElement(By.xpath("//a[@class='add-traveler']")).click();
		int newTraveler = driver.findElements(By.xpath("//div[contains(@class, 'passenger')]")).size();
		
		if (newTraveler > initTraveler) {
			assertTrue("There is another traveler input field added",true);
		}else {
			System.out.println("Add Traveler link doesn't add addtional inout field");

		}
	}
	
	
	@Test
	public void HeadingChangeOnClick() {
		String initHeading = driver.findElement(By.xpath("//h3[@id='video-text' and contains(., 'Life was possible on Mars')]")).toString();
		driver.findElement(By.xpath("//i[@class='vid-next icon-video-right-arrow']")).click();
		String newHeading = driver.findElement(By.xpath("//h3[contains(.,'Why Mars died')]")).toString();
		
		if (newHeading.equalsIgnoreCase(initHeading)) {
			System.out.println("Heading doesn't change on arrow click");
		}else if (newHeading.equalsIgnoreCase("Why Mars died")){
			assertTrue("Heading changed as expected",true);
		}
		
	}
	
	
	/**
	 * Ensure we close the WebDriver after finishing
	 */
	@After
	public void tearDown() {
		driver.quit();
	}
	
	
}
