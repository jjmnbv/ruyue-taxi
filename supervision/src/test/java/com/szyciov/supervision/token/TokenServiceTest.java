package com.szyciov.supervision.token;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import com.szyciov.supervision.SupervisionApplicationTests;
import com.szyciov.supervision.token.service.TokenService;
import com.szyciov.supervision.util.GzwycResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Created by admin on 2017/7/7.
 */
public class TokenServiceTest extends SupervisionApplicationTests{
    private @Autowired TokenService tokenService;

    @Test
    public void testToken() throws IOException {
        GzwycResult token = tokenService.getToken();
        assertThat(token, notNullValue());
        assertThat(token.getStatus(), is(200));
        assertThat("s",is("s"));
        System.out.println(token.getContent());
    }

}
