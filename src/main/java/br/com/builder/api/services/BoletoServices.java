package br.com.builder.api.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import br.com.builder.api.entities.Boleto;
import br.com.builder.api.repositories.BoletoRepository;
import br.com.builder.api.requests.BoletoRequest;
import br.com.builder.api.response.Response;
import br.com.builder.api.responses.BoletoApiResponse;
import br.com.builder.api.utils.DataUtils;

@Service
public class BoletoServices {
    
    private String uri = "https://vagas.builders/api/builders/bill-payments/codes";


    // 1% a cada 30 dias: 1 ÷ 30 = 0,033% ao dia
    // @Value("TAXA")
    private Double TAXA = 1.0;

    // A multa por atraso deve ser de 2%
    // @Value("MULTA_ATRASO")
    private Double MULTA_ATRASO = 2.0;

    @Autowired
    private BoletoRepository boletoRepository;



    public Response<BoletoApiResponse> buscarBoletoApi(String boletoCodigo, String token) {
        
        Response<BoletoApiResponse> response = new Response<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", token);
        
        String boletoJson = BoletoRequest.builder().code(boletoCodigo).build().getJson();
        if (boletoJson == null) {
            response.addError("Falha ao recuperar boleto");
            return response;
        }

        HttpEntity<String> entity = new HttpEntity<String>(boletoJson, headers);
        
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(uri, entity, String.class);
            response.setData(BoletoApiResponse.jsonToObject(responseEntity.getBody()));
        } catch(RestClientException e) {
            response.addError(e.getMessage());
        }       

        return response;
    }


    /**
     * 
     * @param boleto
     * @return
     */
    public Optional<Boleto> salvarBoleto(Boleto boleto) {
        if (boleto != null) {
            return Optional.of(boletoRepository.save(boleto));
        }
        return Optional.empty();
    }


    /**
     * 
     * @param id
     * @return
     */
    public Optional<Boleto> buscarBoleto(Integer id) {
        if (id != null) {
            return boletoRepository.findById(id);
        }
        return Optional.empty();
    }


    /**
     * 
     * @param numero
     * @return
     */
    public Optional<Boleto> buscarBoleto(String numero) {
        if (numero != null) {
            return boletoRepository.findByNumero(numero);
        }
        return Optional.empty();
    }


    /**
     * 
     * @param numero
     * @return
     */
    public Boolean excluirBoleto(String numero) {
        if (numero != null) {
            Optional<Boleto> boleto = boletoRepository.findByNumero(numero);
            if (boleto.isPresent()) {
                boletoRepository.deleteById(boleto.get().getId());
                return true;
            }
        }
        return false;
    }


    /**
     * 
     * @param id
     * @return
     */
    public Boolean excluirBoleto(Integer id) {
        if (id != null) {
            Optional<Boleto> boleto = boletoRepository.findById(id);
            if (boleto.isPresent()) {
                boletoRepository.deleteById(boleto.get().getId());
                return true;
            }
        }
        return false;
    }


    /**
     * 
     * @return
     */
    public List<Boleto> buscarTodos() {
        return boletoRepository.findAll();
    }


    /**
     * Calcular o valor final de um boleto considerando sua data de vencimento e data de pagamento
     * @param request
     * @return
     */
    public Boleto calcularBoleto(BoletoApiResponse request, String paymentDate) {
        Date dataPagamento = DataUtils.toDate(paymentDate, DataUtils.yyyyMMdd);
        Date dataVencimento = DataUtils.toDate(request.getDue_date(), DataUtils.yyyyMMdd);

        Integer quantidadeDiasAtraso = DataUtils.diferencaEmDiasEntreDatas(dataVencimento, dataPagamento);

        Boleto boleto = Boleto.builder()
        .dataPagamento(dataPagamento)
        .dataVencimento(dataVencimento)
        .diasAtraso(quantidadeDiasAtraso)
        .numero(request.getCode())
        .valorBoleto(request.getAmount())
        .build();

        if (quantidadeDiasAtraso <= 0) {
            return boleto;
        }

        Double taxaJuros = (TAXA / 30) * quantidadeDiasAtraso;
        Double multa = (request.getAmount() / 100)  * MULTA_ATRASO;
        Double valorFinal = request.getAmount() + multa + taxaJuros;

        BigDecimal bd = BigDecimal.valueOf(valorFinal);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        valorFinal = bd.doubleValue();

        boleto.setValorCobrado(valorFinal);
        boleto.setValorJuros(valorFinal);
        boleto.setValorMulta(multa);
        return boleto;
    }
}
