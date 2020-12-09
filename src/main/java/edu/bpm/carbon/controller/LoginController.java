package edu.bpm.carbon.controller;

import ch.qos.logback.core.joran.spi.InterpretationContext;
import edu.bpm.carbon.dao.UserDao;
import edu.bpm.carbon.entity.User;
import edu.bpm.carbon.service.UserService;
import edu.bpm.carbon.utils.httputils.HttpUtil;
import edu.bpm.carbon.utils.msgutils.Msg;
import edu.bpm.carbon.utils.msgutils.MsgCode;
import edu.bpm.carbon.utils.msgutils.MsgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class LoginController {

    @GetMapping("/hello")
    public Msg hello() {
        return MsgUtil.makeMsg(MsgUtil.SUCCESS, "hello world");
    }


    @Autowired
    UserService userService;

    @RequestMapping("/carbonLogin")
    public Msg login(@RequestBody Map<String, String> params) {

//        for (String key : params.keySet()) {
//            log.info(key);
//        }

        Msg msg = userService.checkUser(params.get("username"), params.get("password"));
        return msg;
    }


}
