package com.casestudy.demo.domain.insurance;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.casestudy.demo.domain.fixtures.insurance.CustomerTemplate;
import com.casestudy.demo.domain.fixtures.modules.ModuleTemplate;
import com.casestudy.demo.domain.modules.Module;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class InsurancePolicyInvariantsTest {

	private Customer customer;
	private Module module;

	@Before
	public void setUp() {
		FixtureFactoryLoader.loadTemplates("com.casestudy.demo.domain.fixtures");

		this.customer = Fixture.from(Customer.class).gimme(CustomerTemplate.DEFAULT);
		this.module = Fixture.from(Module.class).gimme(ModuleTemplate.BIKE);
	}

	@Test(expected = IllegalStateException.class)
	public void shouldAllowOnlyInsurancePolicyWithPositiveSelectedCoverageAmount() {
		new InsurancePolicy(customer, module, BigDecimal.ZERO);
	}

	@Test(expected = NullPointerException.class)
	public void shouldNotAllowNullCustomer() {
		new InsurancePolicy(null, module, BigDecimal.ONE);
	}

	@Test(expected = NullPointerException.class)
	public void shouldNotAllowNullModule() {
		new InsurancePolicy(customer, null, BigDecimal.ONE);
	}
}
