package com.example.todolist.dtos;

import lombok.Data;

@Data
public class ToDoDTO {

    private String title;
    private String description;
    private String owner;


    public ToDoDTO() {
    }
    public ToDoDTO(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

}
