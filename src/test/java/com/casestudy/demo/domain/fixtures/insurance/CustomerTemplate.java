package com.casestudy.demo.domain.fixtures.insurance;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.function.impl.CpfFunction;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.casestudy.demo.domain.insurance.Customer;

import static br.com.six2six.bfgex.RandomGen.email;

public class CustomerTemplate implements TemplateLoader {
	public static final String DEFAULT = "default-customer";

	@Override
	public void load() {
		Fixture.of(Customer.class).addTemplate(DEFAULT, new Rule() {{
			add("id", random(Long.class, range(1L, 200L)));
			add("name", firstName());
			add("email", email(40));
			add("document", new CpfFunction());
		}});
	}
}
