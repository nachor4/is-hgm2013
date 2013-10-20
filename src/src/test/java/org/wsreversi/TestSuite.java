package org.wsreversi;

import org.junit.ClassRule;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)

@Suite.SuiteClasses({
	/*
	 * Aqui se deben agregar las clases atestear
	 * Ej: InmoTest.class,
	 */
	Ejemplo.class
})

public class TestSuite {
	//Test General
}
