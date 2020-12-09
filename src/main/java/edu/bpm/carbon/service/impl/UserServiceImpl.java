package edu.bpm.carbon.service.impl;

import edu.bpm.carbon.dao.UserDao;
import edu.bpm.carbon.entity.User;
import edu.bpm.carbon.service.UserService;
import edu.bpm.carbon.utils.msgutils.Msg;
import edu.bpm.carbon.utils.msgutils.MsgCode;
import edu.bpm.carbon.utils.msgutils.MsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        if (u.getId() == 0) {
            log.info("empty user");
            return MsgUtil.makeMsg(MsgCode.ERROR);
        }
        else {
            return MsgUtil.makeMsg(MsgCode.SUCCESS);
        }
    }
}
