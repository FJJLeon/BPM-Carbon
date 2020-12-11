package edu.bpm.carbon.controller;

import edu.bpm.carbon.constant.Constant;
import edu.bpm.carbon.entity.User;
import edu.bpm.carbon.service.UserService;
import edu.bpm.carbon.utils.msgutils.Msg;
import edu.bpm.carbon.utils.msgutils.MsgCode;
import edu.bpm.carbon.utils.msgutils.MsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/user")
public class RegisterController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Msg register(@RequestBody User user) {

        log.info("register: {}", user.toString());

        Assert.notNull(user.getUsername(), "username missing");
        Assert.notNull(user.getPassword(), "password missing");

        Msg msg = userService.register(user.getUsername(), user.getPassword(), user.getAge(), user.getGender());
        return msg;
    }
}
