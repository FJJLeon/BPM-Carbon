package edu.bpm.carbon.service.impl;

import edu.bpm.carbon.constant.Constant;
import edu.bpm.carbon.dao.UserDao;
import edu.bpm.carbon.entity.User;
import edu.bpm.carbon.service.UserService;
import edu.bpm.carbon.utils.msgutils.Msg;
import edu.bpm.carbon.utils.msgutils.MsgCode;
import edu.bpm.carbon.utils.msgutils.MsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public Msg checkUser(String username, String password) {
        User u = userDao.checkUser(username, password);
        log.info((u.toString()));

        // JSON to java object, if {}, still not null
        if (u != null && u.getId() == 0) {
            log.info("empty user");
            return MsgUtil.makeMsg(MsgCode.ERROR);
        }
        else {
            return MsgUtil.makeMsg(MsgCode.SUCCESS);
        }
    }

    @Override
    public Msg register(String username, String password, int age, int gender) {
        log.info("username : " + username);
        // 用户名重复检查
        Map<String, Object> dupMap = new HashMap<String, Object>(){{
            put(Constant.USER_NAME, username);
        }};
        List<User> dup = userDao.queryUser(dupMap);
        if (dup.isEmpty() == false) {
            return MsgUtil.makeMsg(MsgCode.ERROR, "用户名已存在");
        }

        User u = userDao.register(username, password, age, gender);
        log.info(u.toString());

        if (u != null && u.getId() == 0) {
            return MsgUtil.makeMsg(MsgCode.ERROR);
        }
        else {
            return MsgUtil.makeMsg(MsgCode.SUCCESS);
        }
    }
}
