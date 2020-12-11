package edu.bpm.carbon.utils.timeutils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 获取形如 "yyyy-MM-dd HH:mm:ss" 的当前时间字符串
     * @return
     */
    public static String getTimeRmpFormat() {
        Date datetime = new Date();
        String currTime = sdf.format(datetime);

        return currTime;
    }

    /**
     *
     * @param rmpSDF RMP平台中保存的 sdf 格式的时间字符串
     * @return 转换得到的 Date 对象
     * @throws ParseException
     */
    public static Date rmpSDF2Date(String rmpSDF){
        try {
            return sdf.parse(rmpSDF);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     *
     * @param rmpSDF RMP平台中保存的 sdf 格式的时间字符串
     * @return 转换得到的 13 位时间戳
     * @throws ParseException
     */
    public static long rmpSDF2TimeStamp(String rmpSDF){
        Date datetime = null;
        try {
            datetime = sdf.parse(rmpSDF);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return datetime.getTime();
    }


}
