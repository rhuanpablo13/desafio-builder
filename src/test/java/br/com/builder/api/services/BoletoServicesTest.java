package br.com.builder.api.services;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.builder.api.entities.Boleto;
import br.com.builder.api.response.Response;
import br.com.builder.api.responses.BoletoApiResponse;
import br.com.builder.api.responses.TokenResponse;

@RunWith(SpringRunner.class)
@SpringBootTest
// @ActiveProfiles("test")
public class BoletoServicesTest {
    

    private static final String BOLETO_260 = "34191790010104351004791020150008291070026000"; // 2022-09-03

    @Autowired
    private BoletoServices boletoServices;

    @Autowired
    private TokenServices tokenServices;
    
    private String token;

    private Response<BoletoApiResponse> boletoApiResponse;

    
    @Before
	public void setUp() {  
        System.out.println("Recuperando token");      
        TokenResponse response = tokenServices.getToken();
        token = response.getToken();

	}
    
    
    @Test
    public void calcularBoleto_260_DataPagamento_DataVencimento_Iguais_Test() {        
        
        System.out.println("Calculando boleto com data de pagamento e vencimento iguais");

        boletoApiResponse = boletoServices.buscarBoletoApi(BOLETO_260, token);
        System.out.println("Token: " + token);

        System.out.println(boletoApiResponse.getData().toString());
        Boleto calcularBoleto = boletoServices.calcularBoleto(boletoApiResponse.getData(), "2022-09-15");
        assertTrue(calcularBoleto.getValorBoleto().compareTo(260.0) == 0);
    }

    @Test
    public void calcularBoleto_260_DataPagamento_1DiaVencimento_Test() {        
        
        System.out.println("Calculando boleto com data de pagamento um dia após o vencimento");

        boletoApiResponse = boletoServices.buscarBoletoApi(BOLETO_260, token);
        System.out.println("Token: " + token);

        System.out.println(boletoApiResponse.getData().toString());
        Boleto calcularBoleto = boletoServices.calcularBoleto(boletoApiResponse.getData(), "2022-09-16");
        assertTrue(calcularBoleto.getValorBoleto().compareTo(265.23) == -1);
    }

}
