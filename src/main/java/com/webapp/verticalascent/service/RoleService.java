package com.webapp.verticalascent.service;

import com.webapp.verticalascent.entity.Role;
import com.webapp.verticalascent.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
	
	private final RoleRepository roleRepository;
	
	@Autowired
	public RoleService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}
	
	public Role getDefaultRole() {
		return roleRepository.findByName("ROLE_USER");
	}
}
