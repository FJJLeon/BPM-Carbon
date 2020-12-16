package edu.bpm.carbon.controller.user;

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
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/user")
public class LoginController {

    @GetMapping("/hello")
    public Msg hello() {
        return MsgUtil.makeMsg(MsgUtil.SUCCESS, "hello world");
    }

    @Autowired
    UserService userService;

    /**
     *
     * @param user
     *      username
     *      password
     * @return 登录信息
     */
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Msg login(@RequestBody User user) {

        log.info("login user: {}", user.toString());

        Assert.notNull(user.getUsername(), "username missing");
        Assert.notNull(user.getPassword(), "password missing");

        Msg msg = userService.checkUser(user.getUsername(), user.getPassword());
        return msg;
    }

    @PostMapping(value = "/query", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Msg query(@RequestBody Map<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> e : params.entrySet()) {
            sb.append(e.getKey()).append(" = ").append(e.getValue());
        }
        log.info("query user: {}", sb.toString());

        Msg msg = userService.queryUser(params);

        return msg;
    }


}
