package com.bugtracker.db.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bugtracker.boards.tasks.join.BoardTaskJoin;

public interface RolesRepository extends JpaRepository<Roles, String> {
	public Roles findOneByUsername(String username);
}