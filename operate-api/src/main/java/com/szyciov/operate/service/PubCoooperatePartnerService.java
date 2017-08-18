package com.szyciov.operate.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.szyciov.dto.PagingResponse;
import com.szyciov.entity.Select2Entity;
import com.szyciov.lease.entity.LeUserNews;
import com.szyciov.message.UserMessage;
import com.szyciov.op.param.pubCoooperatePartner.DisableCoooperateParam;
import com.szyciov.op.param.pubCoooperatePartner.QueryPubCoooperateParam;
import com.szyciov.op.param.pubCoooperatePartner.QueryPubCoooperatePartnerLeaseCompanyParam;
import com.szyciov.op.param.pubCoooperatePartner.ReviewLeasecompanyParam;
import com.szyciov.op.vo.pubCoooperatePartner.QueryCooagreementViewVo;
import com.szyciov.op.vo.pubCoooperatePartner.QueryLeaseCompanyAdminVo;
import com.szyciov.op.vo.pubCoooperatePartner.QueryPubCoooperateVo;
import com.szyciov.operate.dao.PubCoooperatePartnerDao;
import com.szyciov.passenger.util.MessageUtil;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.SMSTempPropertyConfigurer;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
public class PubCoooperatePartnerService {
    @Autowired
    private PubCoooperatePartnerDao pubCoooperatePartnerDao;


    /**
     * 查询所有战略伙伴数据
     *
     * @param model 查询条件
     * @return 战略伙伴信息
     */
    public PagingResponse queryPubCoooperateData(QueryPubCoooperateParam model) {
        int pageNum = 1;

        if (model.getiDisplayLength() != 0) {
            pageNum = model.getiDisplayStart() / model.getiDisplayLength() + 1;
        }

        if (model.getApplicationtimeStart() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(model.getApplicationtimeStart());
            cal.set(Calendar.HOUR, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.MILLISECOND, 0);
            model.setApplicationtimeStart(cal.getTime());
        }

        if (model.getApplicationtimeEnd() != null) {
            Calendar calEnd = Calendar.getInstance();
            calEnd.setTime(model.getApplicationtimeEnd());
            calEnd.set(Calendar.HOUR_OF_DAY, 0);
            calEnd.set(Calendar.MINUTE, 0);
            calEnd.set(Calendar.MILLISECOND, 0);
            calEnd.add(Calendar.DATE, 1);
            model.setApplicationtimeEnd(calEnd.getTime());
        }

        PageHelper.startPage(pageNum, model.getiDisplayLength());
        Page page = (Page) this.pubCoooperatePartnerDao.queryPubCoooperateData(model);

        PagingResponse<QueryPubCoooperateVo> res = new PagingResponse<>();
        res.setAaData(page.getResult());
        res.setiTotalDisplayRecords((int) page.getTotal());
        res.setiTotalRecords((int) page.getTotal());
        res.setsEcho("");

        return res;
    }

    /**
     * 查询战略伙伴
     *
     * @param param 运管ID
     * @return 战略伙伴集合
     */
    public List<Select2Entity> queryLeasecompany(QueryPubCoooperatePartnerLeaseCompanyParam param) {
        List<Select2Entity> list = this.pubCoooperatePartnerDao.queryLeasecompany(param);
        return list;
    }

    /**
     * 审核战略伙伴
     *
     * @param model 审核所需的数据
     */
    public void reviewLeasecompany(ReviewLeasecompanyParam model) {
        this.pubCoooperatePartnerDao.reviewLeasecompany(model);
    }

    /**
     * 禁用战略伙伴
     *
     * @param model 禁用所需数据
     */
    @Transactional(rollbackFor = Exception.class)
    public void disableCoooperate(DisableCoooperateParam model) {
        this.pubCoooperatePartnerDao.disableCoooperate(model);
        this.pubCoooperatePartnerDao.deleteLeaseOrganRef(model.getId());
        this.pubCoooperatePartnerDao.deleteVehicleModelRef(model.getId());
    }

    /**
     * 查看协议
     *
     * @param coooperateId 合作运营id
     * @return 协议内容
     */
    public QueryCooagreementViewVo queryCooagreementView(String coooperateId) {
        return this.pubCoooperatePartnerDao.queryCooagreementView(coooperateId);
    }

    public void sendMsg(String coooId, int state) {
        List<QueryLeaseCompanyAdminVo> list = this.pubCoooperatePartnerDao.queryLeaseCompanyAdminByCoooId(coooId);
        if (list.size() == 0) {
            return;
        }

        String cootype = "";

        if (list.get(0).getCootype() == 0) {
            cootype = "B2B联盟";
        } else if (list.get(0).getCootype() == 1) {
            cootype = "B2C联营";
        }

        String content;
        String title = cootype + "审核通知";

        //   0 - 审核中, 1 - 合作中, 2 - 未达成, 3 - 已终止, 4 - 已过期
        if (state == 2) {
            //模板：贵司申请加盟【业务类型】B2C联营合作，审核未通过，原因：【拒绝原因】。
            content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.operate.service.pubCoooperatepartnerservice.reject", list.get(0).getServicetype(), cootype, list.get(0).getReviewtext());
        } else if (state == 3) {
            //贵司申请加盟${0}业务B2C联营合作，已终止合作。
            content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.operate.service.pubCoooperatepartnerservice.disable", list.get(0).getServicetype(), cootype);
        } else if (state == 1) {
            //贵司申请加盟【业务类型】B2C联营合作，审核通过，可以正常开展联营业务。
            content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.operate.service.pubCoooperatepartnerservice.agree", list.get(0).getServicetype(), cootype);
        } else {
            return;
        }

        List<String> phones = new ArrayList<>();

        for (QueryLeaseCompanyAdminVo vo : list) {
            if (null != vo.getTelphone() && !"".equals(vo.getTelphone())) {
                phones.add(vo.getTelphone());
            }
        }

        UserMessage usermessage = new UserMessage(phones, content, UserMessage.GETSMSCODE);
        MessageUtil.sendMessage(usermessage);

        LeUserNews news;
        JSONObject obj;

        for (QueryLeaseCompanyAdminVo vo : list) {
            obj = new JSONObject();
            news = new LeUserNews();

            obj.put("content", content);
            obj.put("title", title);
            obj.put("type", title);
            news.setContent(obj.toString());
            news.setType("1");
            news.setNewsState("0");
            news.setUserId(vo.getLeid());
            news.setStatus(1);
            news.setCreateTime(new Date());
            news.setTitle(title);
            news.setUpdateTime(new Date());
            news.setId(GUIDGenerator.newGUID());

            this.pubCoooperatePartnerDao.insertLeUserNews(news);
        }
    }
}
