package com.epam.sqa.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Stage;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public final class TestInjector {

	private static TestInjector instance = new TestInjector();

	private Map<List<Class<? extends Module>>, Injector> injectorMap = new ConcurrentHashMap<List<Class<? extends Module>>, Injector>();

	private TestInjector() {
	}

	public static void injectMembers(Object object, List<Class<? extends Module>> modulesList) {

		getInjector(modulesList).injectMembers(object);
	}

	public static Injector getInjector(List<Class<? extends Module>> modulesList) {
		TestInjector testInjector;

		testInjector = instance;
		if (testInjector == null) {
			testInjector = new TestInjector();
			instance = testInjector;
		}

		return testInjector.createInjector(modulesList);

	}

	private Injector createInjector(List<Class<? extends Module>> modulesList) {
		if (!isKeyContain(modulesList)) {
			if (!isKeyContain(modulesList)) {
				List<Module> modules = new ArrayList<>();
				for (Class<? extends Module> module : modulesList) {
					try {
						modules.add(module.newInstance());
					} catch (InstantiationException | IllegalAccessException e) {
						log.error(e.getMessage());
						throw new RuntimeException(e.getMessage());
					}
				}
				Injector injector = Guice.createInjector(Stage.DEVELOPMENT, modules);
				injectorMap.put(modulesList, injector);
			}
		}

		return injectorMap.get(getModuleFromMap(modulesList));
	}

	private boolean isKeyContain(List<Class<? extends Module>> modulesList) {

		for (List<Class<? extends Module>> clazz : injectorMap.keySet()) {
			if (clazz.containsAll(modulesList)) {
				return true;
			}
		}
		return false;
	}

	private List<Class<? extends Module>> getModuleFromMap(List<Class<? extends Module>> modulesList) {

		for (List<Class<? extends Module>> clazz : injectorMap.keySet()) {
			if (clazz.containsAll(modulesList)) {
				return clazz;
			}
		}

		throw new RuntimeException("Module: " + modulesList.toString() + " were not cached in map");
	}
}
