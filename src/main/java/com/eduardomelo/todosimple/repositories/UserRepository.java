package com.eduardomelo.todosimple.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eduardomelo.todosimple.models.User;



@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    
    
}
