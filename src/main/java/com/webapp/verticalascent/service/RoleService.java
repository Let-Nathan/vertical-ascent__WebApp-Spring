package com.webapp.verticalascent.service;

import com.webapp.verticalascent.entity.Role;
import com.webapp.verticalascent.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * Service implementation with logic for Role.
 *
 * @author Nathan L.
 * @version 1.0
 */
@Service
public class RoleService {
	
	private final RoleRepository roleRepository;
	
	@Autowired
	public RoleService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}
	
	/**
	 * Return the default Role defined as "ROLE_USER"
	 *
	 * @return Role
	 */
	public Role getDefaultRole() {
		return roleRepository.findByName("ROLE_USER");
	}
}
