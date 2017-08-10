package com.szyciov.lease.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.dto.pubCoooperate.DriverInformationDto;
import com.szyciov.entity.PubCoooperate;
import com.szyciov.entity.PubVehicleModelsRef;
import com.szyciov.lease.entity.LeVehiclemodels;
import com.szyciov.op.param.PubCoooperateQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;

@Controller
public class PubCoooperateController extends BaseController {
    private static final Logger logger = Logger.getLogger(PubCoooperateController.class);

    private TemplateHelper templateHelper = new TemplateHelper();

    @RequestMapping(value = "/PubCoooperate/Index")
    public ModelAndView getPubVehicleIndex(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
        String id = this.getLoginLeUser(request).getLeasescompanyid();
        List<PubCoooperate> list = templateHelper.dealRequestWithToken("/PubCoooperate/GetCompanyNameList/{id}", HttpMethod.GET, userToken,
                null,List.class,id);
        mav.addObject("list",list);
        mav.setViewName("resource/pubCoooperate/index");
        return mav;
    }

    @RequestMapping("/PubCoooperate/GetPubCoooperateByQuery")
    @ResponseBody
    public PageBean getPubCoooperateByQuery(@RequestBody PubCoooperateQueryParam queryParam, HttpServletRequest request,
                                            HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
        queryParam.setKey(this.getLoginLeUser(request).getLeasescompanyid());
        return templateHelper.dealRequestWithToken("/PubCoooperate/GetPubCoooperateByQuery", HttpMethod.POST, userToken,
                queryParam,PageBean.class);
    }
    @RequestMapping("/PubCoooperate/ResourceInformation")
    @ResponseBody
    public ModelAndView resourceInformation(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
        PubCoooperate p = new PubCoooperate();
        p.setId(request.getParameter("id"));
        p.setCompanyid(request.getParameter("companyid"));
        p.setLeasecompanyid(request.getParameter("leasecompanyid"));
        p.setServicetype(Integer.valueOf(request.getParameter("servicetype")));
        mav.addObject("p",p);
        mav.setViewName("resource/pubCoooperate/resourceInformation");
        return mav;
    }
    @RequestMapping("/PubCoooperate/GetResourceInformationByQuery")
    @ResponseBody
    public PageBean getResourceInformationByQuery(@RequestBody PubCoooperateQueryParam queryParam, HttpServletRequest request,
                                                  HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
        queryParam.setKey(request.getParameter("id"));
        queryParam.setServicetype(request.getParameter("servicetype"));
        return templateHelper.dealRequestWithToken("/PubCoooperate/GetResourceInformationByQuery", HttpMethod.POST, userToken,
                queryParam,PageBean.class);
    }
    @RequestMapping(value = "/PubCoooperate/DriverInformation")
    @ResponseBody
    public ModelAndView driverInformation(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
        String id = request.getParameter("leasescompanyid");
        PubCoooperateQueryParam p = new PubCoooperateQueryParam();
        p.setKey(this.getLoginLeUser(request).getLeasescompanyid());//companyid
        p.setLeasescompanyid(id);
        List<DriverInformationDto> list = templateHelper.dealRequestWithToken("/PubCoooperate/GetModelsList", HttpMethod.POST, userToken,
                p,List.class);
        mav.addObject("list", list);
        mav.addObject("leasescompanyid", id);
        mav.addObject("servicetype", request.getParameter("servicetype"));
        mav.setViewName("resource/pubCoooperate/driverInformation");
        return mav;
    }
    @RequestMapping("/PubCoooperate/GetDriverInformationByQuery")
    @ResponseBody
    public PageBean getDriverInformationByQuery(@RequestBody PubCoooperateQueryParam queryParam, HttpServletRequest request,
                                                HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
        queryParam.setKey(this.getLoginLeUser(request).getLeasescompanyid());//companyid
        queryParam.setLeasescompanyid(request.getParameter("leasescompanyid"));
        queryParam.setServicetype(request.getParameter("servicetype"));
        return templateHelper.dealRequestWithToken("/PubCoooperate/GetDriverInformationByQuery", HttpMethod.POST, userToken,
                queryParam,PageBean.class);
    }

    @RequestMapping("/PubCoooperate/GetOriginalModels")
    @ResponseBody
    public DriverInformationDto getOriginalModels(@RequestParam String vehicleid,@RequestParam String leasescompanyid, HttpServletRequest request,
                                                  HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
        Map<String,String> map = new HashMap<String, String>();
        map.put("id",this.getLoginLeUser(request).getLeasescompanyid());
        map.put("vehicleid", vehicleid);
        map.put("leasescompanyid", leasescompanyid);
        return templateHelper.dealRequestWithToken("/PubCoooperate/GetOriginalModels", HttpMethod.POST, userToken,
                map,DriverInformationDto.class);
    }

    @RequestMapping("/PubCoooperate/GetLeVehiclemodels")
    @ResponseBody
    public List<LeVehiclemodels> getLeVehiclemodels(HttpServletRequest request,
                                                    HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
        Map<String,String> map = new HashMap<String, String>();
        map.put("leasescompanyid", this.getLoginLeUser(request).getLeasescompanyid());
        return templateHelper.dealRequestWithToken("/PubCoooperate/GetLeVehiclemodels", HttpMethod.POST, userToken,
                map,List.class);
    }
    @RequestMapping("/PubCoooperate/CreatePubVehicleModelsRef")
    @ResponseBody
    public Map<String, String> createPubVehicleModelsRef(@RequestParam String vehicleid,@RequestParam String leasecompanyid,@RequestParam String vehiclemodelsid,@RequestParam String id,HttpServletRequest request,
                                                         HttpServletResponse response){
        response.setContentType("text/html;charset=utf-8");
        String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
        PubVehicleModelsRef p = new PubVehicleModelsRef();
        p.setVehiclemodelsid(vehiclemodelsid);
        p.setUpdater(this.getLoginLeUser(request).getId());
        p.setCompanyid(this.getLoginLeUser(request).getLeasescompanyid());
        p.setLeasecompanyid(leasecompanyid);
        p.setVehicleid(vehicleid);
        p.setCreater(this.getLoginLeUser(request).getId());
        if(id !=null && id !=""){
            p.setId(id);
            return templateHelper.dealRequestWithToken("/PubCoooperate/UpdatePubVehicleModelsRef", HttpMethod.POST, userToken, p,
                    Map.class);
        }else{
            p.setId(GUIDGenerator.newGUID());
            return templateHelper.dealRequestWithToken("/PubCoooperate/CreatePubVehicleModelsRef", HttpMethod.POST, userToken, p,
                    Map.class);
        }

    }
}
