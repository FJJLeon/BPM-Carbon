package edu.bpm.carbon.service;

import edu.bpm.carbon.utils.msgutils.Msg;

public interface TravelRecordService {

    Msg startTravel(long userid, String toolType);

    Msg endTravel(long travelRecordID, long userid);

}
