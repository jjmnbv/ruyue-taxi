package com.szyciov.operate.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.szyciov.dto.PagingResponse;
import com.szyciov.op.param.DisableCoooperateParam;
import com.szyciov.op.param.QueryPubCoooperateParam;
import com.szyciov.op.param.ReviewLeasecompanyParam;
import com.szyciov.op.vo.pubCoooperate.QueryCooagreementViewVo;
import com.szyciov.op.vo.pubCoooperate.QueryLeasecompanyVo;
import com.szyciov.op.vo.pubCoooperate.QueryPubCoooperateVo;
import com.szyciov.operate.dao.PubCoooperateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PubCoooperateService {
    @Autowired
    private PubCoooperateDao pubCoooperateDao;


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

        PageHelper.startPage(pageNum, model.getiDisplayLength());
        Page page = (Page) this.pubCoooperateDao.queryPubCoooperateData(model);

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
     * @param companyid 运管ID
     * @return 战略伙伴集合
     */
    public List<QueryLeasecompanyVo> queryLeasecompany(String companyid) {
        List<QueryLeasecompanyVo> list = this.pubCoooperateDao.queryLeasecompany(companyid);
        return list;
    }

    /**
     * 审核战略伙伴
     *
     * @param model 审核所需的数据
     */
    public void reviewLeasecompany(ReviewLeasecompanyParam model) {
        this.pubCoooperateDao.reviewLeasecompany(model);
    }

    /**
     * 禁用战略伙伴
     *
     * @param model 禁用所需数据
     */
    public void disableCoooperate(DisableCoooperateParam model) {
        this.pubCoooperateDao.disableCoooperate(model);
    }

    /**
     * 查看协议
     *
     * @param coooperateId 合作运营id
     * @return 协议内容
     */
    public QueryCooagreementViewVo queryCooagreementView(String coooperateId) {
        return this.pubCoooperateDao.queryCooagreementView(coooperateId);
    }
}
