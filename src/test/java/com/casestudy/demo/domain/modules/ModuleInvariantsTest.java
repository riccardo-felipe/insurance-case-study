package com.casestudy.demo.domain.modules;

import org.junit.Test;

import java.math.BigDecimal;

public class ModuleInvariantsTest {
	private static final String MODULE = "MODULE";
	private static final BigDecimal NEGATIVE = new BigDecimal("-1");

	@Test(expected = IllegalStateException.class)
	public void shouldAllowOnlyModulesWithPositiveMinCoverageAmount() {
		new Module(MODULE, NEGATIVE, BigDecimal.ONE, BigDecimal.ONE);
	}

	@Test(expected = IllegalStateException.class)
	public void shouldAllowOnlyModulesWithPositiveMaxCoverageAmount() {
		new Module(MODULE, BigDecimal.ZERO, NEGATIVE, BigDecimal.ONE);
	}

	@Test(expected = IllegalStateException.class)
	public void shouldAllowOnlyModulesWithPositiveRiskAmount() {
		new Module(MODULE, BigDecimal.ONE, BigDecimal.ZERO, NEGATIVE);
	}

	@Test(expected = NullPointerException.class)
	public void shouldNotAllowNullName() {
		new Module(null, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE);
	}

	@Test(expected = NullPointerException.class)
	public void shouldNotAllowNullMinCoverageAmount() {
		new Module(MODULE, null, BigDecimal.ONE, BigDecimal.ONE);
	}

	@Test(expected = NullPointerException.class)
	public void shouldNotAllowNullMaxCoverageAmount() {
		new Module(MODULE, BigDecimal.ONE,null, BigDecimal.ONE);
	}

	@Test(expected = NullPointerException.class)
	public void shouldNotAllowNullRiskAmount() {
		new Module(MODULE, BigDecimal.ONE, BigDecimal.ONE, null);
	}
}