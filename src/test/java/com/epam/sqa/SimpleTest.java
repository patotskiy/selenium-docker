package com.epam.sqa;

import com.epam.sqa.core.DriverManager;
import com.epam.sqa.guice.ConfigurationModule;
import com.epam.sqa.guice.TestInjector;
import com.epam.sqa.guice.WebModule;
import com.google.inject.Inject;
import com.google.inject.Module;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.util.ArrayList;
import java.util.List;

public class SimpleTest {

	@Inject
	protected RemoteWebDriver driver;

	@Inject
	private DriverManager driverManager;

	@BeforeClass
	public void setup() {
		List<Class<? extends Module>> modules = new ArrayList<>();
		modules.add(WebModule.class);
		modules.add(ConfigurationModule.class);
		TestInjector.injectMembers(this, modules);
	}

	@AfterMethod
	public void thearDown() {
		driverManager.removeDriver();
	}
}
