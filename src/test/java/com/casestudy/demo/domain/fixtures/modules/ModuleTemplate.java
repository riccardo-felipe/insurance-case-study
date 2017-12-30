package com.casestudy.demo.domain.fixtures.modules;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.casestudy.demo.domain.modules.Module;

import java.math.BigDecimal;

public class ModuleTemplate  implements TemplateLoader {
	final static String BASE = "bike-module";
	public final static String BIKE = "bike-module";
	public final static String JEWELRY = "jewelry";

	@Override
	public void load() {
		Fixture.of(Module.class).addTemplate(BASE, new Rule() {{
			add("id", random(Long.class, range(1L, 200L)));
		}});

		Fixture.of(Module.class).addTemplate(BIKE).inherits(BASE, new Rule() {{
			add("name", "Bike");
			add("minimumCoverage", new BigDecimal("0"));
			add("maximumCoverage", new BigDecimal("3000"));
			add("risk", new BigDecimal("0.30"));
		}});

		Fixture.of(Module.class).addTemplate(JEWELRY).inherits(BASE, new Rule() {{
			add("name", "Jewelry");
			add("minimumCoverage", new BigDecimal("500"));
			add("maximumCoverage", new BigDecimal("10000"));
			add("risk", new BigDecimal("0.05"));
		}});
	}
}
