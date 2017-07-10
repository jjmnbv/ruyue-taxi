package com.szyciov.supervision.basic.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.szyciov.supervision.Constants;
import com.szyciov.supervision.basic.request.PlatformCompanyOperation;
import com.szyciov.supervision.enums.CommandEnum;
import com.szyciov.supervision.enums.InterfaceType;
import com.szyciov.supervision.enums.RequestType;
import com.szyciov.supervision.util.BasicRequest;
import com.szyciov.supervision.util.EntityInfoList;
import com.szyciov.supervision.util.GzwycApi;
import com.xunxintech.ruyue.coach.io.json.JSONUtil;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/7/10.
 */
@Component
public class BasicService {

    /**
     *
     * @return
     * @throws IOException
     */
    public EntityInfoList<PlatformCompanyOperation> sendPlatformCompanyOperation() throws IOException {
        String token = "8b566d2ec6f647d78ae3d659f533a0b8";
        List<PlatformCompanyOperation> list = new ArrayList<PlatformCompanyOperation>();
        PlatformCompanyOperation info = new PlatformCompanyOperation("Test","RY",1,1,1, Long.valueOf(LocalDateTime.now().format(Constants.DATE_FORMAT)));
        list.add(info);
        EntityInfoList<PlatformCompanyOperation> infoList = new EntityInfoList<PlatformCompanyOperation>(list);
        String result = JSONUtil.toJackson(infoList);
        BasicRequest req = new BasicRequest(result, InterfaceType.BASIC, CommandEnum.PTYYGM, RequestType.REQ,token);

        EntityInfoList<PlatformCompanyOperation> response = GzwycApi.send(req, new TypeReference<EntityInfoList<PlatformCompanyOperation>>(){});

        return response;
    }
}
