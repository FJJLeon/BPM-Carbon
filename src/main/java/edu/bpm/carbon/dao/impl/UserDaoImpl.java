package edu.bpm.carbon.dao.impl;

import com.alibaba.fastjson.JSONObject;
import edu.bpm.carbon.dao.UserDao;
import edu.bpm.carbon.entity.User;
import edu.bpm.carbon.utils.httputils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

// for what
@Repository
@Slf4j
public class UserDaoImpl implements UserDao {

    @Value("${rmp.url}")
    String RMP_URL;

    @Override
    public User checkUser(String username, String password) {

        String param = String.format("Testuser.username=%s&Testuser.password=%s", username, password);
        String checkUser = HttpUtil.sendGet(RMP_URL, param);

        JSONObject jsonObject = JSONObject.parseObject(checkUser);

        return JSONObject.toJavaObject(jsonObject.getJSONArray("Testuser").getJSONObject(0), User.class);
    }
}
