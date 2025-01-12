package com.example.todolist.repository;

import com.example.todolist.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface TodoRepo extends JpaRepository<Todo, Long> {
        //List<Todo> findByUserId(Long userId, Pageable pageable);
        List<Todo> findByUserId(Long userId);
}

