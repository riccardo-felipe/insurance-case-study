package com.casestudy.demo.interfaces.policies;

import com.casestudy.demo.application.insurance.InsurancePolicyService;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/insurance-policy")
public class InsurancePolicyController {

	private InsurancePolicyService service;
	private InsurancePolicyConverter converter;

	@Autowired
	public InsurancePolicyController(InsurancePolicyService service, InsurancePolicyConverter converter) {
		this.service = service;
		this.converter = converter;
	}

	@GetMapping
	public ResponseEntity<List<InsurancePolicyDTO>> findAll(Authentication authentication) {
		return Optional
			.ofNullable( service.findByCustomerEmail(parseEmail(authentication)) )
			.map( insurancePolicies -> ResponseEntity.ok().body(converter.convertToDTO(insurancePolicies)))
			.orElseGet( () -> ResponseEntity.notFound().build());
	}

	@GetMapping("/{id}")
	public ResponseEntity<InsurancePolicyDTO> findOne(Authentication authentication, @PathVariable("id") Long id) {
		return Optional
			.ofNullable( service.findByCustomerEmailAndId(parseEmail(authentication), id) )
			.map( insurancePolicy -> ResponseEntity.ok().body(converter.convertToDTO(insurancePolicy)) )
			.orElseGet( () -> ResponseEntity.notFound().build() );
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public InsurancePolicyDTO create(Authentication authentication, @RequestBody InsurancePolicyDTO dto) {
		return converter.convertToDTO(service.create(converter.convertToEntity(parseEmail(authentication), dto)));
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public InsurancePolicyDTO update(Authentication authentication,
									 @RequestBody InsurancePolicyDTO dto, @PathVariable("id") Long id) {
		Assert.notNull(id, "id must be informed");
		Assert.isTrue(id.equals(dto.getId()), "insurance policy body id and path must be the same");

		return converter.convertToDTO(service.update(converter.convertToEntity(parseEmail(authentication), dto)));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(Authentication authentication, @PathVariable("id") Long id) {
		Assert.notNull(id, "id must be informed");
		service.delete(parseEmail(authentication), id);
	}

	private String parseEmail(Authentication authentication) {
		return (String) authentication.getPrincipal();
	}
}
