package edu.bpm.carbon.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import edu.bpm.carbon.constant.Constant;
import edu.bpm.carbon.dao.CarbonOrderDao;
import edu.bpm.carbon.dao.CompanyDao;
import edu.bpm.carbon.entity.CarbonOrder;
import edu.bpm.carbon.entity.Company;
import edu.bpm.carbon.service.CarbonOrderService;
import edu.bpm.carbon.service.certification.CertificationService;
import edu.bpm.carbon.utils.msgutils.Msg;
import edu.bpm.carbon.utils.msgutils.MsgCode;
import edu.bpm.carbon.utils.msgutils.MsgUtil;
import edu.bpm.carbon.utils.timeutils.TimeUtil;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CarbonOrderServiceImpl implements CarbonOrderService {

    @Autowired
    CompanyDao companyDao;

    @Autowired
    CarbonOrderDao orderDao;

    @Autowired
    CertificationService certService;

    @Override
    public Msg queryCarbonOrder(Map<String, Object> params) {
        log.info("queryCarbonOrder: params=[{}]", params.toString());

        List<CarbonOrder> carbonOrders = orderDao.queryCarbonOrder(params);

        JSONObject data = new JSONObject();
        data.put("CarbonOrders", carbonOrders);

        Msg msg = MsgUtil.makeMsg(MsgCode.SUCCESS, "查询碳排量订单", data);

        return msg;
    }

    @Override
    public Msg queryCarbonOrderByCompany(long companyId) {
        log.info("queryCarbonOrderByCompany: companyId=[{}]", companyId);

        List<CarbonOrder> carbonOrders = orderDao.queryCarbonOrderByCompanyId(companyId);

        JSONObject data = new JSONObject();
        data.put("CarbonOrders", carbonOrders);

        Msg msg = MsgUtil.makeMsg(MsgCode.SUCCESS, "查询指定公司碳排量订单", data);

        return msg;
    }

    @Override
    public Msg queryCarbonOrderByStatus(String status) {
        log.info("queryCarbonOrderByCompanyAndStatus");

        List<CarbonOrder> carbonOrders = orderDao.queryCarbonOrderByStatus(status);

        JSONObject data = new JSONObject();
        data.put("CarbonOrders", carbonOrders);

        Msg msg = MsgUtil.makeMsg(MsgCode.SUCCESS, "查询指定状态碳排量订单", data);

        return msg;
    }

    @Override
    public Msg createCarbonOrder(long companyId, long amount) {
        log.info("createCarbonOrder, companyId=[{}], amount=[{}]", companyId, amount);
        // get and check company
        Company company = companyDao.queryCompanyById(companyId);
        if (0 == company.getId()) {
            log.warn("company ID not exist");
            return MsgUtil.makeMsg(MsgCode.ERROR, "公司ID不存在");
        }
        // make create time
        String createTime = TimeUtil.getTimeRmpFormat();
        // construct carbon order
        CarbonOrder co = new CarbonOrder();
        co.setCompanyid(companyId);
        co.setCompanyname(company.getName());
        co.setAmount(amount);
        co.setStatus(Constant.ODSTATUS_PENDING);
        co.setCreatetime(createTime);
        // dao post carbonOrder
        co = orderDao.postCarbonOrder(co);
        Assert.isTrue(co.getId() != 0, "create carbon order fail");
        // insert order(with ID) to company's pending order
        company.setPendingorder(co);
        // dap put company
        companyDao.putCompany(company);

        Msg msg = MsgUtil.makeMsg(MsgCode.SUCCESS, "新建碳订单", (JSONObject) JSONObject.toJSON(co));
        return msg;
    }

    @Override
    public Msg reviewCarbonOrder(long orderId, long companyId, String operation) {
        log.info("reviewCarbonOrder, orderId=[{}], companyId=[{}], operation=[{}]", orderId, companyId, operation);
        // check operation
        if (!operation.equals(Constant.ODSTATUS_REJECT) && !operation.equals(Constant.ODSTATUS_ACCEPT)) {
            log.warn("operation wrong");
            return MsgUtil.makeMsg(MsgCode.ERROR, "操作参数错误");
        }
        // get and check company
        Company company = companyDao.queryCompanyById(companyId);
        log.info("company: [{}]", company.toString());
        if (0 == company.getId()) {
            log.warn("company ID not exist");
            return MsgUtil.makeMsg(MsgCode.ERROR, "公司ID不存在");
        }
        // get order
        CarbonOrder co = company.getPendingorder();
        if (co == null || co.getId() != orderId) {
            log.warn("something wrong in frontend, order/company mismatch");
            return MsgUtil.makeMsg(MsgCode.ERROR, "订单与公司不匹配");
        }
        // make review time
        String reviewTime = TimeUtil.getTimeRmpFormat();
        co.setReviewtime(reviewTime);
        // operation branch
        if (operation.equals(Constant.ODSTATUS_REJECT)) {
            // reject
            co.setStatus(Constant.ODSTATUS_REJECT);
            co.setUnitprice(-1);
            // update order
            co = orderDao.putCarbonOrder(co);
            // remove from pending order
            company.setPendingorder(null);
            // add to reject orders
            company.addRejectorders(co);
        }
        else {
            // accept
            co.setStatus(Constant.ODSTATUS_ACCEPT);

            // get unitPrice and deduct funds
            // unitPrice 厘/kg == 元/吨
            certService.fluctuateUnitPrice();
            double up = certService.getCarbonUnitPrice();
            double totalPriceInLi = up * co.getAmount() * 1000;
            int totalLi = (int) totalPriceInLi;
            co.setUnitprice(up);
            // update company funds and remain
            company.setFunds(company.getFunds() - totalLi);
            company.setRemaincarbonemission(company.getRemaincarbonemission() + co.getAmount() * 10^6);

            // update order
            co = orderDao.putCarbonOrder(co);
            // remove from pending order
            company.setPendingorder(null);
            // add to reject orders
            company.addAcceptorders(co);
        }
        // dao update company
        companyDao.putCompany(company);

        return MsgUtil.makeMsg(MsgCode.SUCCESS, "审核碳订单", (JSONObject) JSONObject.toJSON(co));
    }
}
