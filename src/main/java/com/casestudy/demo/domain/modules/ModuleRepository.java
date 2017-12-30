package com.casestudy.demo.domain.modules;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long>{
	Module findOneByIdAndName(Long id, String name);
}
