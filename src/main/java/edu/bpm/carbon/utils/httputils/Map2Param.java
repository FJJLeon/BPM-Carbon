package edu.bpm.carbon.utils.httputils;

import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.Map;

@Slf4j
public class Map2Param {
    /***
     * Transfer Map Params to RMP URL Params
     *
     * @param resource
     *      目标资源名
     * @param params
     *      以 Map 形式表示的参数表
     * @return
     *      RMP 平台指定格式的 URL 参数，形如 resource.name1=value1&resource.name2=value2
     *
     * TODO: 加上 key 的检查
     */

    public static String genRmpParam(String resource, Map<String, Object> params) {
        if (resource == null || params == null) {
            log.warn("Generate Param Missing");
            return "";
        }

        StringBuilder sb = new StringBuilder();
        Iterator<Map.Entry<String, Object>> entries = params.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, Object> e = entries.next();
            sb.append(resource)
                    .append(".")
                    .append(e.getKey())
                    .append("=")
                    .append(e.getValue());
            if (entries.hasNext()) {
                sb.append("&");
            }
        }

        return sb.toString();
    }
}
