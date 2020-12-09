package edu.bpm.carbon.service;

import edu.bpm.carbon.entity.User;
import edu.bpm.carbon.utils.msgutils.Msg;

public interface UserService {

    Msg checkUser(String username, String password);

}
