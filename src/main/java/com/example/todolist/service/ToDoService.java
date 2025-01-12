package com.example.todolist.service;

import com.example.todolist.entity.Todo;
import com.example.todolist.entity.User;
import com.example.todolist.repository.TodoRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoService {

    private final TodoRepo toDoItemRepository;

    public ToDoService(TodoRepo toDoItemRepository) {
        this.toDoItemRepository = toDoItemRepository;
    }

    public Todo createToDo(String title, String description, User user) {
        Todo toDoItem = new Todo(title, description, user);
        return toDoItemRepository.save(toDoItem);
    }

    public List<Todo> getUserTodos(Long userId) {
        return toDoItemRepository.findByUserId(userId);
    }

    public void deleteToDo(Long id) {
        toDoItemRepository.deleteById(id);
    }
}
