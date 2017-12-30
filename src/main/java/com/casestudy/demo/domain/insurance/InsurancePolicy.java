package com.casestudy.demo.domain.insurance;

import com.casestudy.demo.domain.modules.Module;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

import static com.casestudy.demo.domain.utils.ValidationUtilities.isGreaterThanZero;

@Data
@Entity
@NoArgsConstructor
public class InsurancePolicy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "module_id", referencedColumnName = "id", nullable = false)
	private Module module;

	@Column(nullable = false)
	private BigDecimal selectedCoverage;

	@Transient
	@Setter(AccessLevel.NONE)
	private BigDecimal tariff;

	@Transient
	@Setter(AccessLevel.NONE)
	private BigDecimal totalReimbursement;

	public InsurancePolicy(@NonNull Customer customer, @NonNull Module module, @NonNull BigDecimal selectedCoverage) {
		isGreaterThanZero(selectedCoverage, "selectedCoverage");
		this.customer = customer;
		this.module = module;
		this.selectedCoverage = selectedCoverage;

		loadTransientFields();
	}

	@PostLoad
	@PostUpdate
	private void loadTransientFields() {
		loadTariff();
		loadTotalReimbursement();
	}

	private void loadTariff() {
		final BigDecimal valueToCalculate = isGreaterThanMaximumCoverage() ? module.getMaximumCoverage() : selectedCoverage ;
		tariff = valueToCalculate.multiply(module.getRisk()).setScale(2, BigDecimal.ROUND_UP);
	}

	private void loadTotalReimbursement() {
		totalReimbursement = isGreaterThanMaximumCoverage() ? module.getMaximumCoverage() : selectedCoverage;
	}

	private boolean isGreaterThanMaximumCoverage() {
		return selectedCoverage.compareTo(module.getMaximumCoverage()) > 0;
	}
}
