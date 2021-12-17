package com.bugtracker.db.boards.tasks;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TestCustom implements TaskRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

	@Transactional
    @Override
    public void customMethod() {

        Query query = em.createNativeQuery("alter table"
        		+ " bugtracker_db.board_tasks add column test int(1)"
        		+ " not null default 0");

        em.getTransaction().begin();
        em.getTransaction().commit();
        em.flush();
        em.clear();
        em.close();
        query.executeUpdate();
        System.out.println("Sucessful!");  
    }
}