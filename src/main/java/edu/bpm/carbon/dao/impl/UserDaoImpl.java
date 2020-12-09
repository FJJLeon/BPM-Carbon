package edu.bpm.carbon.dao.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.corba.se.spi.ior.ObjectKey;
import edu.bpm.carbon.constant.Constant;
import edu.bpm.carbon.dao.UserDao;
import edu.bpm.carbon.entity.User;
import edu.bpm.carbon.utils.httputils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.*;

// for what
@Repository
@Slf4j
public class UserDaoImpl implements UserDao {

    @Value("${rmp.url}")
    String RMP_URL;

    @Override
    public List<User> queryUser(Map<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        Iterator<Map.Entry<String, Object>> entries = params.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, Object> e = entries.next();
            sb.append(Constant.USER_RESOURCE + "." + e.getKey())
                    .append("=")
                    .append(e.getValue());
            if (entries.hasNext()) {
                sb.append("&");
            }
        }

        log.info(sb.toString());

        JSONObject queryResp = HttpUtil.httpGetJSON(RMP_URL, sb.toString());
        JSONArray userFound = queryResp.getJSONArray(Constant.USER_RESOURCE);
        if (userFound == null) {
            return null;
        }

        JSONObject jsonUser = null;
        User u = null;
        List<User> result = new ArrayList<>();
        for (int i = 0; i < userFound.size(); i++) {
            jsonUser = userFound.getJSONObject(i);
            u = JSONObject.toJavaObject(jsonUser, User.class);
            result.add(u);
        }

        return result;
    }

    @Override
    public User checkUser(String username, String password) {

        String param = String.format("Testuser.username=%s&Testuser.password=%s", username, password);
        JSONObject checkUser = HttpUtil.httpGetJSON(RMP_URL, param);
        /** response json
         * {
         *     "Testuser": [
         *         {
         *             "id": 1607360530791,
         *             "userid": 2,
         *             "username": "zzt",
         *             "password": "123",
         *             "age": 23,
         *             "gender": 1,
         *             "userdesc": {
         *                 "id": 1607360478811,
         *                 "university": "SJTU",
         *                 "dorm": "x36-5405",
         *                 "lab": "5403"
         *             }
         *         }
         *     ]
         * }
         * */
        JSONArray userFound = checkUser.getJSONArray("Testuser");

        if (userFound != null && userFound.size() == 1) {
            return JSONObject.toJavaObject(userFound.getJSONObject(0), User.class);
        }
        else {
            return new User();
        }
    }

    @Override
    public User register(String username, String password, int age, int gender) {

        Map<String, Object> postParam = new HashMap<>();
        postParam.put(Constant.USER_NAME, username);
        postParam.put(Constant.USER_PASSWORD, password);
        postParam.put(Constant.USER_AGE, age);
        postParam.put(Constant.USER_GENDER, gender);

        JSONObject postResponse = HttpUtil.httpPostJSON(RMP_URL, postParam);
        log.info(postResponse.toString());
        /** response json
         * {
         *     "type": "testuser",
         *     "id": 1607505557902,
         *     "userid": 55,
         *     "username": "test",
         *     "password": "123",
         *     "age": 23,
         *     "gender": 1
         * }
         * */
        User user = JSONObject.toJavaObject(postResponse, User.class);

        return user;
    }
}