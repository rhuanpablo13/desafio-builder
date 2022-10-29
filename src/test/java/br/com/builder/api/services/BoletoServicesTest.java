package br.com.builder.api.services;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.builder.api.entities.Boleto;
import br.com.builder.api.requests.BoletoRequest;

@RunWith(SpringRunner.class)
@SpringBootTest
// @ActiveProfiles("test")
public class BoletoServicesTest {
    
    @Autowired
    private BoletoServices boletoServices = new BoletoServices();

    
    public static void main(String[] args) {
        
        // DataUtils.toDate("2022-09-13 14:29:10", DataUtils.yyyyMMdd_HHmmss);


        BoletoServicesTest teste = new BoletoServicesTest();
        BoletoRequest request = new BoletoRequest();

        request.setDataPagamento("2022-09-15 14:29:10");
        request.setDataVencimento("2022-09-13 14:29:10");
        request.setNumero("34191790010104351004791020150008291070026000");
        request.setValorBoleto(150.0);

        Boleto boleto = teste.boletoServices.calcularBoleto(request);
        System.out.println(boleto.toString());

    }



    @Test
    public void calcularBoletoTest() {
        BoletoServicesTest teste = new BoletoServicesTest();
        BoletoRequest request = new BoletoRequest();

        request.setDataPagamento("2022-09-15 14:29:10");
        request.setDataVencimento("2022-09-13 14:29:10");
        request.setNumero("34191790010104351004791020150008291070026000");
        request.setValorBoleto(150.0);

        Boleto boleto = teste.boletoServices.calcularBoleto(request);
        System.out.println(boleto.toString());
    }
}
