package com.casestudy.demo.domain.insurance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsurancePolicyRepository extends JpaRepository<InsurancePolicy, Long>{
	List<InsurancePolicy> findByCustomerEmail(String email);
	InsurancePolicy findByCustomerEmailAndId(String email, Long id);
	void deleteByCustomerEmailAndId(String email, Long id);
}
