package edu.bpm.carbon.constant;

public class Constant {
    public static final String USER_RESOURCE = "Testuser";
    public static final String USER_ID = "id";
    public static final String USER_NAME = "username";
    public static final String USER_PASSWORD = "password";
    public static final String USER_AGE = "age";
    public static final String USER_GENDER = "gender";
    public static final String USER_CREDIT = "credit";
    public static final String USER_USERDESC = "userdesc";
    public static final String USER_TRAVELRECORDS = "usertravelrecords";
    public static final String USER_EXCHANGERECORDS = "userexchanges";
    public static final String USER_ISTRAVELING = "istraveling";

    // TR = TravelRecord
    public static final String TR_RESOURCE = "Travelrecord";
    public static final String TR_ID = "id";
    public static final String TR_USERID = "userid";
    public static final String TR_VEHICLETYPE = "vehicletype";
    public static final String TR_STARTTIME = "starttime";
    public static final String TR_ENDTIME = "endtime";
    public static final String TR_ISFINISHED = "isfinished";
    public static final String TR_USERNAME = "username";
    public static final String TR_CREDIT = "credit";

    public static final String VEHICLETYPE_BIKE = "bike";
    public static final String VEHICLETYPE_BUS = "bus";
    public static final String VEHICLETYPE_SUBWAY = "subway";

    public static final String REWARD_RESOURCE = "Reward";
    public static final String REWARD_ID = "id";
    public static final String REWARD_NAME = "name";
    public static final String REWARD_CREDIT = "credit";
    public static final String REWARD_INVENTORY = "inventory";
    public static final String REWARD_EXCHANGED = "exchanged";
    public static final String REWARD_PRICE = "price";
    public static final String REWARD_IMAGE = "image";

    // ER = ExchangeRecord
    public static final String ER_RESOURCE = "Exchangerecord";
    public static final String ER_ID = "id";
    public static final String ER_USERID = "userid";
    public static final String ER_USERNAME = "username";
    public static final String ER_REWARDID = "rewardid";
    public static final String ER_QUANTITY = "quantity";            // 兑换该奖励的数量
    public static final String ER_REWARDUNIT = "unitcredit";        // 该奖励单件所需积分
    public static final String ER_CREDITCOST = "totalcredit";        // 该兑换所用的积分 = rewardunit * quantity
    public static final String ER_EXCHANGETIMEd = "exchangetime";   // 兑换时间
    public static final String ER_REWARDNAME = "rewardname";
    public static final String ER_REWARDINFO = "rewardinfo";
}
