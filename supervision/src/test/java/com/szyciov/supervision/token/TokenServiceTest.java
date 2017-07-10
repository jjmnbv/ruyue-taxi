package com.szyciov.supervision.token;

import com.szyciov.supervision.SupervisionApplicationTests;
import com.szyciov.supervision.token.service.TokenService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by admin on 2017/7/7.
 */
public class TokenServiceTest extends SupervisionApplicationTests{
    private @Autowired TokenService tokenService;

//    @Test
    public void testToken() throws IOException {
        String token = tokenService.getToken();
        assertThat(token, notNullValue());
        assertThat("s",is("s"));
        System.out.println(token);
    }

}
