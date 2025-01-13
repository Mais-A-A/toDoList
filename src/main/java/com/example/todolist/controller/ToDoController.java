package com.example.todolist.controller;

import com.example.todolist.dtos.ToDoDTO;
import com.example.todolist.service.ToDoService;
import com.example.todolist.entity.ToDo;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/todos")
public class ToDoController {

    private final ToDoService toDoService;

    ToDoController(ToDoService toDoService){
        this.toDoService = toDoService;
    }

    @PostMapping()
    public ResponseEntity<ToDoDTO> createNewTask(@RequestBody ToDoDTO newTask, @RequestHeader Map<String, String> headers)  {
        System.out.println("maissssssssssssssss "+newTask.getDescription());
        String token = headers.get("authorization");
        System.out.println(token);
        ToDoDTO taskCreated = toDoService.createNewTask(newTask, token);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskCreated);
    }

    @GetMapping()
    public ResponseEntity<Page<ToDo>> createNewTask(@RequestParam int page, @RequestParam int size, @RequestHeader Map<String, String> headers) {
        String token = headers.get("authorization");
        Page<ToDo> taskList = toDoService.getAllTasks(page, size, token);

        return ResponseEntity.status(HttpStatus.OK).body(taskList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ToDoDTO> updateTask(@PathVariable Long id, @RequestBody ToDoDTO task, @RequestHeader Map<String, String> headers){
        String token = headers.get("authorization");
        ToDoDTO updatedTask = toDoService.updateTask(id, task, token);
        if(updatedTask== null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ToDoDTO> deleteTask (@PathVariable Long id, @RequestHeader Map<String, String> headers) {
        String token = headers.get("authorization");
        ToDoDTO deletedTask = toDoService.deleteTask(id, token);
        if(deletedTask== null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(deletedTask);
    }
}
