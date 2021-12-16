package com.bugtracker.boards.tasks.join;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BTJRepository extends JpaRepository<BoardTaskJoin, BTJIdentity> {

}