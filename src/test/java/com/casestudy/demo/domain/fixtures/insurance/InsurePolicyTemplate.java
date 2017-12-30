package com.casestudy.demo.domain.fixtures.insurance;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.casestudy.demo.domain.fixtures.modules.ModuleTemplate;
import com.casestudy.demo.domain.insurance.Customer;
import com.casestudy.demo.domain.insurance.InsurancePolicy;
import com.casestudy.demo.domain.modules.Module;

import java.math.BigDecimal;

import static br.com.six2six.bfgex.RandomGen.email;

public class InsurePolicyTemplate implements TemplateLoader {
	private static String BASE = "base-insurance";

	public static String FULL_BIKE = "full-bike-insurance";
	public static String HALF_BIKE = "half-bike-insurance";

	public static String HUNDRED_JEWELRY = "hundred-jewelry-insurance";
	public static String THOUSAND_JEWELRY = "thousand-jewelry-insurance";
	public static String ELEVEN_THOUSAND_JEWELRY = "eleven-thousand-jewelry-insurance";

	@Override
	public void load() {
		Fixture.of(InsurancePolicy.class).addTemplate(BASE, new Rule() {{
			add("id", random(Long.class, range(1L, 200L)));
			add("customer", one(Customer.class, CustomerTemplate.DEFAULT) );
		}});

		Fixture.of(InsurancePolicy.class).addTemplate(FULL_BIKE).inherits(BASE, new Rule() {{
			add("module", one(Module.class, ModuleTemplate.BIKE));
			add("selectedCoverage", new BigDecimal("3000"));
		}});

		Fixture.of(InsurancePolicy.class).addTemplate(HALF_BIKE).inherits(FULL_BIKE, new Rule () {{
			add("selectedCoverage", new BigDecimal("1500"));
		}});

		Fixture.of(InsurancePolicy.class).addTemplate(HUNDRED_JEWELRY).inherits(BASE, new Rule () {{
			add("module", one(Module.class, ModuleTemplate.JEWELRY));
			add("selectedCoverage", new BigDecimal("100"));
		}});

		Fixture.of(InsurancePolicy.class).addTemplate(THOUSAND_JEWELRY).inherits(HUNDRED_JEWELRY, new Rule () {{
			add("selectedCoverage", new BigDecimal("1000"));
		}});

		Fixture.of(InsurancePolicy.class).addTemplate(ELEVEN_THOUSAND_JEWELRY).inherits(HUNDRED_JEWELRY, new Rule () {{
			add("selectedCoverage", new BigDecimal("11000"));
		}});
	}
}
