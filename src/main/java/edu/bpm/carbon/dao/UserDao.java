package edu.bpm.carbon.dao;

import edu.bpm.carbon.entity.User;

public interface UserDao {

    User checkUser(String username, String password);

}
