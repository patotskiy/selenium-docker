package com.epam.sqa.core;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import static com.epam.sqa.guice.WebModule.BROWSER_NAME;
import static com.epam.sqa.guice.WebModule.SELENIUM_HUB;

@Slf4j
public class DriverManager {

	private static ThreadLocal<RemoteWebDriver> webDrivers = new ThreadLocal();

	@Named(SELENIUM_HUB)
	@Inject
	private String seleniumHubHost;

	@Named(BROWSER_NAME)
	@Inject
	private String browserName;

	public DriverManager() {
	}

	public RemoteWebDriver getInstance() {
		if (webDrivers.get() == null) {
			DesiredCapabilities dc = DesiredCapabilities.chrome();
			URL url = null;
			try {
				url = new URL("http://" + seleniumHubHost + ":4444/wd/hub");
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			webDrivers.set(new RemoteWebDriver(url, dc));
		}

		return webDrivers.get();

	}

	public void removeDriver() {
		if (webDrivers.get() != null) {
			log.info("Killing driver for Thread: : " + Thread.currentThread().getId());
			webDrivers.get().quit();
			webDrivers.remove();
		}
	}
}
