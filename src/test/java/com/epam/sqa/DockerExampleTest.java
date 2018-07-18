package com.epam.sqa;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class DockerExampleTest extends SimpleTest {


	@Test
	public void tutbyTest(){
		driver.get("https://tut.by");
		WebElement searchInput = driver.findElementById("search_from_str");

		searchInput.sendKeys("EPAM");
		searchInput.submit();

		WebElement otherPage = driver.findElementByLinkText("www.epam.com/");
		otherPage.click();

	}
}
