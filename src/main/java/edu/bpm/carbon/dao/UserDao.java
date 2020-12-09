package edu.bpm.carbon.dao;

import edu.bpm.carbon.entity.User;

import java.util.List;
import java.util.Map;

public interface UserDao {

    List<User> queryUser(Map<String, Object> params);

    User checkUser(String username, String password);

    User register(String username, String password, int age, int gender);
}
