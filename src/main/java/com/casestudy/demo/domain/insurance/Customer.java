package com.casestudy.demo.domain.insurance;

import lombok.*;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Setter(AccessLevel.NONE)
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NonNull
	@Column(nullable = false)
	private String name;

	@Email
	@NonNull
	@Column(nullable = false)
	private String email;

	@NonNull
	@Column(nullable = false)
	private String document;

	@OneToMany(mappedBy = "customer")
	private final List<InsurancePolicy> insurancePolicies = new ArrayList<>();

}
