package edu.bpm.carbon.dao.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import edu.bpm.carbon.constant.Constant;
import edu.bpm.carbon.dao.UserDao;
import edu.bpm.carbon.entity.User;
import edu.bpm.carbon.utils.httputils.HttpUtil;
import edu.bpm.carbon.utils.httputils.Map2Param;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// for what
@Repository
@Slf4j
public class UserDaoImpl implements UserDao {

    @Value("${rmp.resource.user.url}")
    String USER_URL;
    /**
        // TODO cannot get, property[no] and final[yes]
        String TEST1_URL = PROJECT_URL + Constant.USER_RESOURCE + "/";
        String TEST2_URL = Constant.USER_RESOURCE;
        String TEST3_URL = PROJECT_URL + TEST2_URL;
    */

    @Override
    public List<User> queryUser(Map<String, Object> params) {
        List<User> result = new ArrayList<>();

        String rmpParam = Map2Param.genRmpParam(Constant.USER_RESOURCE, params);
        log.info("queryUser: {}", rmpParam);

        JSONObject queryResp = HttpUtil.httpGetJSON(USER_URL, rmpParam);
        JSONArray userFound = queryResp.getJSONArray(Constant.USER_RESOURCE);
        if (userFound == null) {
            return result;
        }

        JSONObject jsonUser;
        User u;
        for (int i = 0; i < userFound.size(); i++) {
            jsonUser = userFound.getJSONObject(i);
            // u = JSONObject.toJavaObject(jsonUser, User.class);
            // TODO, Must use Gson, or some field missing[userTravelRecord.id&starttime&endtime]
            u = new Gson().fromJson(jsonUser.toString(), User.class);
            result.add(u);
        }

        return result;
    }

    @Override
    public User checkUser(String username, String password) {

        String param = String.format("%s.%s=%s&%s.%s=%s",
                Constant.USER_RESOURCE, Constant.USER_NAME, username,
                Constant.USER_RESOURCE, Constant.USER_PASSWORD, password);
        JSONObject checkUser = HttpUtil.httpGetJSON(USER_URL, param);
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

        JSONObject postResponse = HttpUtil.httpPostJSON(USER_URL, postParam);
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

    @Override
    public User putUser(User user) {
        log.info("putUser: {}", user.toString());

        Map<String, Object> putParam = new HashMap<String, Object>() {{
            put(Constant.USER_ID, user.getId());
            put(Constant.USER_NAME, user.getUsername());
            put(Constant.USER_PASSWORD, user.getPassword());
            put(Constant.USER_AGE, user.getAge());
            put(Constant.USER_GENDER, user.getGender());
            put(Constant.USER_CREDIT, user.getCredit());
            put(Constant.USER_USERDESC, user.getUserdesc());
            put(Constant.USer_TRAVELRECORDS, user.getUsertravelrecords());
        }};
        // put request
        String putURL = USER_URL + "/" + user.getId();
        JSONObject putResponse = HttpUtil.httpPutJSON(putURL, putParam);
        log.info("putResponse: {}", putResponse.toString());

        User result = new Gson().fromJson(putResponse.toString(), User.class);
        return result;
    }
}
