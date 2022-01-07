package com.bugtracker.db.roles;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bugtracker.db.btj.BoardTaskJoin;

public interface RolesRepository extends JpaRepository<Roles, RolesIdentity> {
	public List<Roles> findAllByRolesIdentityUsername(String username);
	public List<Roles> findAllByRolesIdentityProjectId(Integer projectId);
	public Roles findByRolesIdentity(RolesIdentity identity);
	public Boolean deleteByRolesIdentity(RolesIdentity identity);
	
	//public Roles findOneByUsername(String username);
}