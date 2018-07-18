package com.epam.sqa.guice;

import com.epam.sqa.core.DriverManager;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.remote.RemoteWebDriver;

@Slf4j
public class WebModule extends AbstractModule {

	public static final String BROWSER_NAME = "browser.name";
	public static final String SELENIUM_HUB = "selenium.hub";


	@Override
	protected void configure() {
		DriverManager driverManager = new DriverManager();
		bind(DriverManager.class).toInstance(driverManager);
	}

	@Provides
	public RemoteWebDriver provideWebDriver(DriverManager driverManager) {
		return driverManager.getInstance();
	}
}
