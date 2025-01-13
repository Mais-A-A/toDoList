package com.example.todolist.repository;


import com.example.todolist.entity.ToDo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoRepo extends JpaRepository<ToDo, Long> {
    Page<ToDo> findByUserUsername(String username, Pageable pageable);
}
