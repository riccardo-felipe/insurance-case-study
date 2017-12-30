package com.casestudy.demo.application.insurance;

import com.casestudy.demo.domain.insurance.InsurancePolicy;
import com.casestudy.demo.domain.insurance.InsurancePolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class InsurancePolicyService {

	private final InsurancePolicyRepository repository;

	@Autowired
	public InsurancePolicyService(InsurancePolicyRepository repository) {
		this.repository = repository;
	}

	public List<InsurancePolicy> findByCustomerEmail(String email) {
		return repository.findByCustomerEmail(email);
	}

	public InsurancePolicy findByCustomerEmailAndId(String email, Long id) {
		return repository.findByCustomerEmailAndId(email, id);
	}

	public InsurancePolicy create(InsurancePolicy policy) {
		Assert.notNull(policy, "cannot create an invalid insurance policy");
		if(policy.getId() != null)
			throw new EntityExistsException(String.format("insurance policy %d already exists", policy.getId()));

		return repository.saveAndFlush(policy);
	}


	public InsurancePolicy update(InsurancePolicy policy) {
		Assert.notNull(policy, "cannot update an invalid insurance policy");
		Assert.notNull(policy.getId(), "cannot update a yet transient insurance policy");

		final Long id = policy.getId();
		final String email = policy.getCustomer().getEmail();
		final String message = String.format("insurance id %d for customer %s not found", id, email);

		//noinspection ResultOfMethodCallIgnored
		Optional
			.ofNullable(repository.findByCustomerEmailAndId(email, id))
			.orElseThrow(() -> new EntityNotFoundException(message));

		return repository.saveAndFlush(policy);
	}

	@Transactional
	public void delete(String email, Long id) {
		final String message = String.format("insurance id %d for customer %s not found", id, email);
		final InsurancePolicy policy = Optional
			.ofNullable(repository.findByCustomerEmailAndId(email, id))
			.orElseThrow(() -> new EntityNotFoundException(message));

		repository.delete(policy);
	}
}
