package edu.bpm.carbon.dao;

import edu.bpm.carbon.entity.User;

import java.util.List;
import java.util.Map;

public interface UserDao {

    /**
     * 普通查询, GET
     * @param params Map 形式的参数表
     * @return 查询结果，User 列表
     */
    List<User> queryUser(Map<String, Object> params);

    /**
     * 登陆用查询，GET
     * @param username 用户名
     * @param password 密码
     * @return 对应的用户，若不存在则为 new User()
     */
    User checkUser(String username, String password);

    /**
     * 用户注册，POST
     * 在系统中没用到
     * @param username 用户名
     * @param password 密码
     * @param age 年龄
     * @param gender 性别
     * @return 注册返回的 User，给定 id
     */
    User register(String username, String password, int age, int gender);

    /**
     * 修改用户信息，PUT
     * @param user 实体对象
     * @return 修改后的 User 对象
     */
    User putUser(User user);
}
