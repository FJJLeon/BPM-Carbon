package edu.bpm.carbon.controller;

import edu.bpm.carbon.constant.Constant;
import edu.bpm.carbon.service.UserService;
import edu.bpm.carbon.utils.msgutils.Msg;
import edu.bpm.carbon.utils.msgutils.MsgCode;
import edu.bpm.carbon.utils.msgutils.MsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class RegisterController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Msg register(@RequestBody Map<String, String> params) {
        for (Map.Entry<String, String> e : params.entrySet()) {
            log.info(e.getKey() + " : " + e.getValue());
        }
        String username = params.get(Constant.USER_NAME);
        String password = params.get(Constant.USER_PASSWORD);
        String age = params.getOrDefault(Constant.USER_AGE, "0");
        int ageInt = Integer.parseInt(age);
        String gender = params.getOrDefault(Constant.USER_GENDER, "0");
        int genderInt = Integer.parseInt(gender);



        Msg msg = userService.register(username, password, ageInt, genderInt);
        return msg;
    }
}
