package com.casestudy.demo.domain.modules;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

import static com.casestudy.demo.domain.utils.ValidationUtilities.isGreaterThanZero;
import static com.casestudy.demo.domain.utils.ValidationUtilities.isPositiveValue;

@Data
@Entity
@NoArgsConstructor
@Setter(AccessLevel.NONE)
public class Module {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private BigDecimal minimumCoverage;

	@Column(nullable = false)
	private BigDecimal maximumCoverage;

	@Column(nullable = false)
	private BigDecimal risk;

	public Module(@NonNull String name,
				  @NonNull BigDecimal minimumCoverage,
				  @NonNull BigDecimal maximumCoverage,
				  @NonNull BigDecimal risk) {
		isPositiveValue(minimumCoverage, "minimumCoverage");
		isGreaterThanZero(maximumCoverage, "maximumCoverage");
		isGreaterThanZero(risk, "risk");

		this.name = name;
		this.minimumCoverage = minimumCoverage;
		this.maximumCoverage = maximumCoverage;
		this.risk = risk;
	}
}
