package br.com.builder.api.services;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.builder.api.responses.TokenResponse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TokenServicesTest {
    
    @Autowired
    private TokenServices tokenServices;


    @Test
    public void getTokenTest() {
        TokenResponse token = tokenServices.getToken();
        System.out.println(token.toString());

        assertNotNull(token);
    }
}
