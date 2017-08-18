package com.szyciov.lease.controller;

import com.szyciov.dto.PagingResponse;
import com.szyciov.lease.entity.User;
import com.szyciov.lease.param.pubComplaintManage.QueryPubComplaintsParam;
import com.szyciov.lease.param.pubComplaintManage.UpdateProcessRecordParam;
import com.szyciov.param.Select2Param;
import com.szyciov.util.BaseController;
import com.szyciov.util.TemplateHelper;
import net.sf.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * Created by ZF on 2017/8/14.
 */
@Controller
@RequestMapping(value = "PubComplaintManage")
public class PubComplaintManageController extends BaseController {
    private TemplateHelper templateHelper = new TemplateHelper();

    @RequestMapping(value = "Index", method = RequestMethod.GET)
    public String index() {
        return "resource/pubComplaintManage/index";
    }

    @RequestMapping(value = "processed", method = RequestMethod.GET)
    public String processed() {
        return "resource/pubComplaintManage/processed";
    }

    @RequestMapping(value = "queryPubComplaints", method = RequestMethod.POST)
    @ResponseBody
    public PagingResponse queryPubComplaints(@RequestBody QueryPubComplaintsParam model) {
        User user = getLoginLeUser();
        model.setLeasecompanyid(user.getLeasescompanyid());
        return templateHelper.dealRequestWithToken("/PubComplaintManage/queryPubComplaints", HttpMethod.POST, null,
                model, PagingResponse.class);
    }

    @RequestMapping(value = "queryPubComplaintsUsers", method = RequestMethod.GET)
    @ResponseBody
    public List queryPubComplaintsUsers(Select2Param param) {
        User user = getLoginLeUser();
        param.setCompanyid(user.getLeasescompanyid());
        return templateHelper.dealRequestWithToken("/PubComplaintManage/queryPubComplaintsUsers", HttpMethod.POST, null,
                param, List.class);
    }

    @RequestMapping(value = "queryPubComplaintsordernos", method = RequestMethod.GET)
    @ResponseBody
    public List queryPubComplaintsordernos(Select2Param param) {
        User user = getLoginLeUser();
        param.setCompanyid(user.getLeasescompanyid());
        return templateHelper.dealRequestWithToken("/PubComplaintManage/queryPubComplaintsordernos", HttpMethod.POST, null,
                param, List.class);
    }

    @RequestMapping(value = "queryPubComplaintsJobNum", method = RequestMethod.GET)
    @ResponseBody
    public List queryPubComplaintsJobNum(Select2Param param) {
        User user = getLoginLeUser();
        param.setCompanyid(user.getLeasescompanyid());
        return templateHelper.dealRequestWithToken("/PubComplaintManage/queryPubComplaintsJobNum", HttpMethod.POST, null,
                param, List.class);
    }

    @RequestMapping(value = "queryPubComplaintsDrivers", method = RequestMethod.GET)
    @ResponseBody
    public List queryPubComplaintsDrivers(Select2Param param) {
        User user = getLoginLeUser();
        param.setCompanyid(user.getLeasescompanyid());
        return templateHelper.dealRequestWithToken("/PubComplaintManage/queryPubComplaintsDrivers", HttpMethod.POST, null,
                param, List.class);
    }

    @RequestMapping(value = "queryPubComplaintsFullplateno", method = RequestMethod.GET)
    @ResponseBody
    public List queryPubComplaintsFullplateno(Select2Param param) {
        User user = getLoginLeUser();
        param.setCompanyid(user.getLeasescompanyid());
        return templateHelper.dealRequestWithToken("/PubComplaintManage/queryPubComplaintsFullplateno", HttpMethod.POST, null,
                param, List.class);
    }

    @RequestMapping(value = "queryPubComplaintsTypes", method = RequestMethod.GET)
    @ResponseBody
    public List queryPubComplaintsTypes(Select2Param param) {
        User user = getLoginLeUser();
        param.setCompanyid(user.getLeasescompanyid());
        return templateHelper.dealRequestWithToken("/PubComplaintManage/queryPubComplaintsTypes", HttpMethod.POST, null,
                param, List.class);
    }

    @RequestMapping(value = "updateProcessRecord", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject updateProcessRecord(@RequestBody UpdateProcessRecordParam param) {
        User user = getLoginLeUser();
        param.setProcessor(user.getId());
        param.setUpdater(user.getId());
        param.setProcesstime(new Date());
        param.setUpdatetime(new Date());
        return templateHelper.dealRequestWithToken("/PubComplaintManage/updateProcessRecord", HttpMethod.POST, null,
                param, JSONObject.class);
    }

    @RequestMapping(value = "updateProcessRecordContent", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject updateProcessRecordContent(@RequestBody UpdateProcessRecordParam param) {
        User user = getLoginLeUser();
        param.setUpdater(user.getId());
        param.setUpdatetime(new Date());
        param.setProcessor(user.getId());
        return templateHelper.dealRequestWithToken("/PubComplaintManage/updateProcessRecord", HttpMethod.POST, null,
                param, JSONObject.class);
    }
}
