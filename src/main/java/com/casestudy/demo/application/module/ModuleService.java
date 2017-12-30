package com.casestudy.demo.application.module;

import com.casestudy.demo.domain.modules.Module;
import com.casestudy.demo.domain.modules.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class ModuleService {
	private final ModuleRepository repository;

	@Autowired
	public ModuleService(ModuleRepository repository) {
		this.repository = repository;
	}

	public Module findOneByIdAndName(Long id, String name) {
		return Optional
			.ofNullable(repository.findOneByIdAndName(id, name))
			.orElseThrow(() -> new EntityNotFoundException(String.format("module %s not found", name)));
	}
}
