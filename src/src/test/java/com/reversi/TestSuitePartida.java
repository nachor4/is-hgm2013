package com.reversi;

import org.junit.ClassRule;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Suite.class)

@Suite.SuiteClasses({
	PartidaSpec.class
})

public class TestSuitePartida {
}
