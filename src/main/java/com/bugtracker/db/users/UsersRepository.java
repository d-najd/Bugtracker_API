package com.bugtracker.db.users;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsersRepository extends JpaRepository<User, Integer>{
	//NOTE User is the user object above not the user field in the sql database
	
	//@Query("SELECT u.id FROM User u WHERE u.id ?1")
	public Optional<User> findById(Integer id);
    
    /* for modifying
    @Modifying
    @Query(
      value = 
        "insert into Users (name, age, email, status) values (:name, :age, :email, :status)",
      nativeQuery = true)
    void insertUser(@Param("name") String name, @Param("age") Integer age, 
      @Param("status") Integer status, @Param("email") String email);
      */
}
