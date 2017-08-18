package com.szyciov.operate.controller;

import com.szyciov.dto.PagingResponse;
import com.szyciov.entity.Retcode;
import com.szyciov.op.param.pubComplaintManage.QueryPubComplaintsParam;
import com.szyciov.op.param.pubComplaintManage.UpdateProcessRecordParam;
import com.szyciov.operate.service.PubComplaintManageSerivce;
import com.szyciov.param.Select2Param;
import com.szyciov.util.ApiExceptionHandle;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ZF on 2017/8/14.
 */
@RestController
@RequestMapping(value = "api/PubComplaintManage")
public class PubComplaintManageController extends ApiExceptionHandle {
    @Autowired
    private PubComplaintManageSerivce pubComplaintManageSerivce;

    @RequestMapping(value = "queryPubComplaints", method = RequestMethod.POST)
    public PagingResponse queryPubComplaints(@RequestBody QueryPubComplaintsParam model) {
        return this.pubComplaintManageSerivce.queryPubComplaints(model);
    }

    @RequestMapping(value = "queryPubComplaintsUsers", method = RequestMethod.POST)
    public List queryPubComplaintsUsers(@RequestBody Select2Param param) {
        List list = this.pubComplaintManageSerivce.queryPubComplaintsUsers(param);
        return list;
    }

    @RequestMapping(value = "queryPubComplaintsordernos", method = RequestMethod.POST)
    public List queryPubComplaintsordernos(@RequestBody Select2Param param) {
        List list = this.pubComplaintManageSerivce.queryPubComplaintsordernos(param);
        return list;
    }

    @RequestMapping(value = "queryPubComplaintsJobNum", method = RequestMethod.POST)
    public List queryPubComplaintsJobNum(@RequestBody Select2Param param) {
        List list = this.pubComplaintManageSerivce.queryPubComplaintsJobNum(param);
        return list;
    }

    @RequestMapping(value = "queryPubComplaintsDrivers", method = RequestMethod.POST)
    public List queryPubComplaintsDrivers(@RequestBody Select2Param param) {
        List list = this.pubComplaintManageSerivce.queryPubComplaintsDrivers(param);
        return list;
    }

    @RequestMapping(value = "queryPubComplaintsFullplateno", method = RequestMethod.POST)
    public List queryPubComplaintsFullplateno(@RequestBody Select2Param param) {
        List list = this.pubComplaintManageSerivce.queryPubComplaintsFullplateno(param);
        return list;
    }

    @RequestMapping(value = "queryPubComplaintsTypes", method = RequestMethod.POST)
    public List queryPubComplaintsTypes(@RequestBody Select2Param param) {
        List list = this.pubComplaintManageSerivce.queryPubComplaintsTypes(param);
        return list;
    }

    @RequestMapping(value = "updateProcessRecord", method = RequestMethod.POST)
    public JSONObject updateProcessRecord(@RequestBody UpdateProcessRecordParam param) {
        JSONObject result = new JSONObject();
        try {
            this.pubComplaintManageSerivce.updateProcessRecord(param);
        } catch (Exception e) {
            result.put("status", Retcode.EXCEPTION.code);
            result.put("message", Retcode.EXCEPTION.msg);
        }
        return checkResult(result);
    }
}
