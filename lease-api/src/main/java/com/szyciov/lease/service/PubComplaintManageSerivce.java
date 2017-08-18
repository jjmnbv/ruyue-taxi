package com.szyciov.lease.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.szyciov.dto.PagingResponse;
import com.szyciov.lease.dao.PubComplaintManageDao;
import com.szyciov.lease.param.pubComplaintManage.QueryPubComplaintsParam;
import com.szyciov.lease.param.pubComplaintManage.UpdateProcessRecordParam;
import com.szyciov.lease.vo.pubComplaintManage.QueryPubComplaintsVo;
import com.szyciov.param.Select2Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

/**
 * Created by ZF on 2017/8/14.
 */
@Service
public class PubComplaintManageSerivce {
    @Autowired
    private PubComplaintManageDao pubComplaintManageDao;


    public PagingResponse queryPubComplaints(QueryPubComplaintsParam model) {
        int pageNum = 1;

        if (model.getiDisplayLength() != 0) {
            pageNum = model.getiDisplayStart() / model.getiDisplayLength() + 1;
        }

        if (model.getComplainttimestart() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(model.getComplainttimestart());
            cal.set(Calendar.HOUR, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.MILLISECOND, 0);
            model.setComplainttimestart(cal.getTime());
        }

        if (model.getComplainttimeend() != null) {
            Calendar calEnd = Calendar.getInstance();
            calEnd.setTime(model.getComplainttimeend());
            calEnd.set(Calendar.HOUR_OF_DAY, 0);
            calEnd.set(Calendar.MINUTE, 0);
            calEnd.set(Calendar.MILLISECOND, 0);
            calEnd.add(Calendar.DATE, 1);
            model.setComplainttimeend(calEnd.getTime());
        }

        if (model.getProcesstimestart() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(model.getProcesstimestart());
            cal.set(Calendar.HOUR, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.MILLISECOND, 0);
            model.setProcesstimestart(cal.getTime());
        }

        if (model.getProcesstimeend() != null) {
            Calendar calEnd = Calendar.getInstance();
            calEnd.setTime(model.getProcesstimeend());
            calEnd.set(Calendar.HOUR_OF_DAY, 0);
            calEnd.set(Calendar.MINUTE, 0);
            calEnd.set(Calendar.MILLISECOND, 0);
            calEnd.add(Calendar.DATE, 1);
            model.setProcesstimeend(calEnd.getTime());
        }

        PageHelper.startPage(pageNum, model.getiDisplayLength());
        Page page = (Page) this.pubComplaintManageDao.queryPubComplaints(model);

        PagingResponse<QueryPubComplaintsVo> res = new PagingResponse<>();
        res.setAaData(page.getResult());
        res.setiTotalDisplayRecords((int) page.getTotal());
        res.setiTotalRecords((int) page.getTotal());
        res.setsEcho("");

        return res;
    }

    public List queryPubComplaintsUsers(Select2Param param) {
        List list = this.pubComplaintManageDao.queryPubComplaintsUsers(param);
        return list;
    }

    public List queryPubComplaintsordernos(Select2Param param) {
        List list = this.pubComplaintManageDao.queryPubComplaintsordernos(param);
        return list;
    }

    public List queryPubComplaintsJobNum(Select2Param param) {
        List list = this.pubComplaintManageDao.queryPubComplaintsJobNum(param);
        return list;
    }

    public List queryPubComplaintsDrivers(Select2Param param) {
        List list = this.pubComplaintManageDao.queryPubComplaintsDrivers(param);
        return list;
    }

    public List queryPubComplaintsFullplateno(Select2Param param) {
        List list = this.pubComplaintManageDao.queryPubComplaintsFullplateno(param);
        return list;
    }

    public List queryPubComplaintsTypes(Select2Param param) {
        List list = this.pubComplaintManageDao.queryPubComplaintsTypes(param);
        return list;
    }

    public void updateProcessRecord(UpdateProcessRecordParam param) {
        this.pubComplaintManageDao.updateProcessRecord(param);
    }
}
