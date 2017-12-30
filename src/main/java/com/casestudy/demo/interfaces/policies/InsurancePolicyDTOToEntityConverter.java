package com.casestudy.demo.interfaces.policies;

import com.casestudy.demo.application.insurance.CustomerService;
import com.casestudy.demo.domain.insurance.Customer;
import com.casestudy.demo.domain.insurance.InsurancePolicy;
import com.casestudy.demo.interfaces.module.ModuleDTO;
import com.casestudy.demo.application.module.ModuleService;
import com.casestudy.demo.domain.modules.Module;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InsurancePolicyDTOToEntityConverter implements Converter<InsurancePolicyDTO, InsurancePolicy> {
	private final CustomerService customerService;
	private final ModuleService moduleService;

	@Autowired
	public InsurancePolicyDTOToEntityConverter(CustomerService customerService, ModuleService moduleService) {
		this.customerService = customerService;
		this.moduleService = moduleService;
	}

	@Override
	public InsurancePolicy convert(MappingContext<InsurancePolicyDTO, InsurancePolicy> mappingContext) {
		final InsurancePolicyDTO dto = mappingContext.getSource();
		final Customer customer = customerService.findByEmail(dto.getCustomer().getEmail());
		final ModuleDTO dtoModule = dto.getModule();
		final Module module = moduleService.findOneByIdAndName(dtoModule.getId(), dtoModule.getName());

		final InsurancePolicy policy = new InsurancePolicy(customer, module, dto.getSelectedCoverage());
							  policy.setId(dto.getId());
		return policy;
	}
}
