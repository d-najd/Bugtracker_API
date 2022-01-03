package com.bugtracker.db.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Roles, String> {
}