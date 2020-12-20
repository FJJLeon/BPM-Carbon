package edu.bpm.carbon.service;

import edu.bpm.carbon.utils.msgutils.Msg;

import java.util.Map;

public interface CarbonOrderService {

    /**
     * 普通查询碳排放量订单
     * @param params Map 形式的参数
     * @return 查询结果列表
     */
    Msg queryCarbonOrder(Map<String, Object> params);

    /**
     * 查询具体某个出行工具公司的碳排量订单
     * @param companyId 公司 ID
     * @return 该公司所有的订单列表
     */
    Msg queryCarbonOrderByCompany(long companyId);

    /**
     * 查询具体所有公司某种状态碳排量订单
     * @param status 状态
     * @return 所有公司指定状态订单列表
     */
    Msg queryCarbonOrderByStatus(String status);

    /**
     * 创建一个碳排量购买订单，由出行工具公司发起
     * @param companyId 公司 ID
     * @param amount 希望购买的数量，单位 千克
     * @return 新建订单的消息，新建的 CarbonOrder
     */
    Msg createCarbonOrder(long companyId, long amount);

    /**
     *
     * @param orderId
     * @param companyId
     * @Param operation
     * @return
     */
    /**
     * 审核一个订单，由认证机构发起
     * 此服务需要调用一个认证机构服务来 计算此次订单应使用的单价
     * @param orderId 订单 ID
     * @param companyId 该订单来自公司 ID
     * @param operation 该审核操作
     * @return 审核订单返回的消息，修改后的 CarbonOrder
     */
    Msg reviewCarbonOrder(long orderId, long companyId, String operation);
}
