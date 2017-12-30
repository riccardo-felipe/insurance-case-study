package com.casestudy.demo.interfaces.policies;

import com.casestudy.demo.domain.insurance.InsurancePolicy;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InsurancePolicyConverter {

	private final ModelMapper mapper;


	@Autowired
	public InsurancePolicyConverter(ModelMapper mapper, Converter<InsurancePolicyDTO, InsurancePolicy> converter) {
		this.mapper = mapper;
		this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		this.mapper.addConverter(converter);
	}


	public InsurancePolicyDTO convertToDTO(InsurancePolicy policy) {
		return mapper.map(policy, InsurancePolicyDTO.class);
	}

	public List<InsurancePolicyDTO> convertToDTO(List<InsurancePolicy> policies) {
		return policies
			.stream()
			.map(this::convertToDTO)
			.collect(Collectors.toList());
	}

	public InsurancePolicy convertToEntity(String email, InsurancePolicyDTO dto) {
		if(!email.equals(dto.getCustomer().getEmail()))
			throw new AccessDeniedException("invalid attempt to create an insurance policy other customer");

		final String message = "invalid module informed";

		Assert.notNull(dto.getModule(), message);
		Assert.notNull(dto.getModule().getId(), message);
		Assert.notNull(dto.getModule().getName(), message);

		return mapper.map(dto, InsurancePolicy.class);
	}
}
