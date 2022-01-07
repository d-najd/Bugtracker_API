package com.bugtracker.db.roles;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bugtracker.db.btj.BoardTaskJoin;

public interface RolesRepository extends JpaRepository<Roles, RolesIdentity> {
	public Roles findOneByUsername(String username);
}