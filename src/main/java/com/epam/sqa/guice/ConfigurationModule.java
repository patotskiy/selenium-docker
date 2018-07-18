package com.epam.sqa.guice;

import com.google.common.base.Ascii;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.google.common.io.Resources.getResource;

@Slf4j
public class ConfigurationModule extends AbstractModule {

	static Properties loadProperties(String resource) {



		Properties properties = new Properties();
		try (InputStream is = getResource(resource).openStream()) {
			properties.load(is);
		} catch (IOException e) {
			throw new IllegalArgumentException("Cannot find properties", e);
		}

		properties.entrySet().forEach(entry -> {
			final String env = System.getenv(toUpperUnderscore(entry.getKey().toString()));
			if (null != env) {
				entry.setValue(env);
			}
		});
		return properties;

	}

	static String toUpperUnderscore(String s) {
		return Ascii.toUpperCase(s.replaceAll("\\.", "_"));
	}

	@Override
	protected void configure() {
		final Properties properties = loadProperties("run.properties");
		bind(Properties.class).toInstance(properties);
		Names.bindProperties(binder(), properties);
	}
}
