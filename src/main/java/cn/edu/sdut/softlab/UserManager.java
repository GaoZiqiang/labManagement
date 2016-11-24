package cn.edu.sdut.softlab;

import java.util.List;

public interface UserManager {

    List<User> getUsers() throws Exception;

    String addUser() throws Exception;

    User getNewUser();

    void setNewUser(User newUser);

}
