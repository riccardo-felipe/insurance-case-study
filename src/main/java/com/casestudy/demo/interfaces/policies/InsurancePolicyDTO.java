package com.casestudy.demo.interfaces.policies;

import com.casestudy.demo.interfaces.customer.CustomerDTO;
import com.casestudy.demo.interfaces.module.ModuleDTO;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class InsurancePolicyDTO {
	private Long id;
	private CustomerDTO customer;
	private ModuleDTO module;
	private BigDecimal selectedCoverage;
	private BigDecimal tariff;
	private BigDecimal totalReimbursement;
}
