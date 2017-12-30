package com.casestudy.demo.domain.insurance;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.six2six.fixturefactory.processor.Processor;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;

import static com.casestudy.demo.domain.fixtures.insurance.InsurePolicyTemplate.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InsurancePolicyCalculationsTest {

	@Before
	public void setUp() {
		FixtureFactoryLoader.loadTemplates("com.casestudy.demo.domain.fixtures");
	}

	@Test
	public void shouldCalculateRegularTariffForMaximumBikeCoverage() {
		assertEquals(parseExpectedValue("900"), retrieveTariffFor(FULL_BIKE));
	}

	@Test
	public void shouldCalculateRegularTariffForHalfMaximumBikeCoverage() {
		assertEquals(parseExpectedValue("450"), retrieveTariffFor(HALF_BIKE));
	}

	@Test
	public void shouldCalculateRegularTariffForAHundredJewelry() {
		assertEquals(parseExpectedValue("5"), retrieveTariffFor(HUNDRED_JEWELRY));
	}

	@Test
	public void shouldCalculateRegularTariffForAThousandJewelry() {
		assertEquals(parseExpectedValue("50"), retrieveTariffFor(THOUSAND_JEWELRY));
	}

	@Test
	public void shouldCalculateTariffBasedUponMaximumCoverage() {
		assertEquals(parseExpectedValue("500"), retrieveTariffFor(ELEVEN_THOUSAND_JEWELRY));
	}

	@Test
	public void shouldPayTotallySelectedCoverage() {
		final InsurancePolicy policy = retrieveTemplate(THOUSAND_JEWELRY);
		assertEquals(policy.getTotalReimbursement(), policy.getSelectedCoverage());
	}

	@Test
	public void shouldPayOnlyMaximumReimbursement() {
		final InsurancePolicy policy = retrieveTemplate(ELEVEN_THOUSAND_JEWELRY);
		final String message =
			"when selected coverage amount is above maximum coverage, " +
			"the total expected reimbursement will be the maximum coverage ceil";

		assertTrue(message, policy.getTotalReimbursement().compareTo(policy.getSelectedCoverage()) < 0);
		assertEquals(policy.getTotalReimbursement(), policy.getModule().getMaximumCoverage());
	}

	private InsurancePolicy retrieveTemplate(String template) {
		return Fixture.from(InsurancePolicy.class).uses(transientFieldsInitializer()).gimme(template);
	}

	private Processor transientFieldsInitializer() {
		return result -> {
			if(result instanceof InsurancePolicy) {
				try {
					final Method method = InsurancePolicy.class.getDeclaredMethod("loadTransientFields");
					method.setAccessible(true);
					method.invoke(result);
					method.setAccessible(false);
				} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		};
	}

	private BigDecimal retrieveTariffFor(String template) {
		return retrieveTemplate(template).getTariff();
	}

	private BigDecimal parseExpectedValue(String expectedValue) {
		return new BigDecimal(expectedValue).setScale(2, BigDecimal.ROUND_UP);
	}
}