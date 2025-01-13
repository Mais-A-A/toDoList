package com.example.todolist.service;

import com.example.todolist.dtos.ToDoDTO;
import com.example.todolist.entity.User;
import com.example.todolist.entity.ToDo;
import com.example.todolist.repository.ToDoRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ToDoService {

    private ToDoRepo toDoRepo;

    private UserService userService;

    public ToDoService(ToDoRepo toDoRepo, UserService userService) {
        this.toDoRepo = toDoRepo;
        this.userService = userService;
    }

    public ToDoDTO createNewTask(ToDoDTO task, String token){
        System.out.println("hiiiiii from createNewTask");
        ToDo newToDo = new ToDo();
        newToDo.setTitle(task.getTitle());
        newToDo.setDescription(task.getDescription());
        
        String username = getUsernameFromToken(token);
        User taskOwner = validateIfUserExists(username);
        if(taskOwner == null){
            return null;
        }
        newToDo.setUser(taskOwner);
        ToDo toDoCreated = toDoRepo.save(newToDo);
        ToDoDTO taskDTO = new ToDoDTO();
        taskDTO.setTitle(toDoCreated.getTitle());
        taskDTO.setDescription(toDoCreated.getDescription());
        taskDTO.setOwner(toDoCreated.getUser().getUsername());
        return taskDTO;

    }

    public Page<ToDo> getAllTasks(int page, int size, String token){

        String username = getUsernameFromToken(token);
        PageRequest pageRequest = PageRequest.of(page, size);
        return toDoRepo.findByUserUsername(username, pageRequest);

    }

    public ToDoDTO updateTask(Long id, ToDoDTO task, String token){
        String username = getUsernameFromToken(token);
        //validateIfUserExists(username);
        User taskOwner = validateIfUserExists(username);
        if(taskOwner == null){
            return null;
        }

        Optional<ToDo> taskToUpdate = toDoRepo.findById(id);
        if(!taskToUpdate.isPresent()) return null;
        if(!taskToUpdate.get().getUser().getUsername().equals(username)) return null;

        ToDo toDoUpdated = taskToUpdate.get();
        toDoUpdated.setTitle(task.getTitle());
        toDoUpdated.setDescription(task.getDescription());

        ToDo toDoSaved = toDoRepo.save(toDoUpdated);

        ToDoDTO taskDTO = new ToDoDTO();
        taskDTO.setTitle(toDoSaved.getTitle());
        taskDTO.setDescription(toDoSaved.getDescription());
        taskDTO.setOwner(toDoSaved.getUser().getUsername());
        return taskDTO;
    }

    public ToDoDTO deleteTask(Long id, String token){

        String username = getUsernameFromToken(token);
        //validateIfUserExists(username);
        User taskOwner = validateIfUserExists(username);
        if(taskOwner == null){
            return null;
        }

        Optional<ToDo> taskToDelete = toDoRepo.findById(id);
        if(!taskToDelete.isPresent()) return null;

        if(!taskToDelete.get().getUser().getUsername().equals(username)) return null;
        ToDoDTO taskDTO = new ToDoDTO();
        taskDTO.setTitle(taskToDelete.get().getTitle());
        taskDTO.setDescription(taskToDelete.get().getDescription());
        taskDTO.setOwner(taskToDelete.get().getUser().getUsername());

        toDoRepo.delete(taskToDelete.get());

        return taskDTO;

    }

    private String getUsernameFromToken(String token){
        return userService.getUsernameFromToken(token);
    }

    private User validateIfUserExists(String username){

        Optional<User> taskOwner = userService.getUserByUsername(username);
        if(!taskOwner.isPresent()) return null;

        return taskOwner.get();

    }
}
