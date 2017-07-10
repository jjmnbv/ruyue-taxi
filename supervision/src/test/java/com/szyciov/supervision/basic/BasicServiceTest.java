package com.szyciov.supervision.basic;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import com.szyciov.supervision.SupervisionApplication;
import com.szyciov.supervision.SupervisionApplicationTests;
import com.szyciov.supervision.basic.request.PlatformCompanyOperation;
import com.szyciov.supervision.basic.service.BasicService;
import com.szyciov.supervision.util.EntityInfoList;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Created by admin on 2017/7/10.
 */
public class BasicServiceTest extends SupervisionApplicationTests{
    private @Autowired BasicService basicService;
    @Test
    public void test() throws IOException {

        EntityInfoList<PlatformCompanyOperation> list = basicService.sendPlatformCompanyOperation();

        assertThat(list, notNullValue());
    }
}
