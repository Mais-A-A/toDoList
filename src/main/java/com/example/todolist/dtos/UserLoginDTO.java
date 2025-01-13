package com.example.todolist.dtos;


public class UserLoginDTO {

    private String username;
    private String password;

    public UserLoginDTO() {
    }

    public UserLoginDTO(String userName, String password) {
        this.username = userName;
        this.password = password;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserLoginDTO [userName=" + username + ", password=" + password + "]";
    }

}
