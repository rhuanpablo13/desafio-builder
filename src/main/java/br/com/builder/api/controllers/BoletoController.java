package br.com.builder.api.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.builder.api.entities.Boleto;
import br.com.builder.api.requests.BoletoRequest;
import br.com.builder.api.response.Response;
import br.com.builder.api.responses.BoletoApiResponse;
import br.com.builder.api.responses.BoletoResponse;
import br.com.builder.api.services.BoletoServices;

@RestController
@RequestMapping("/boleto")
@CrossOrigin(origins = "*")
public class BoletoController {
    

    @Autowired
    private BoletoServices boletoServices;

    @PostMapping
	public ResponseEntity<Response<BoletoResponse>> calcular(@RequestBody BoletoRequest boletoRequest, @RequestHeader("token") String token) {
        
        Response<BoletoResponse> response = new Response<>();
        if (token == null || token.isBlank()) {
            response.addError("Informe um token válido");
            return ResponseEntity.badRequest().body(response); 
        }

        if (boletoRequest == null || boletoRequest.getCode() == null || boletoRequest.getCode().isBlank()) {
            response.addError("Informe um código");
            return ResponseEntity.badRequest().body(response);
        }

        Response<BoletoApiResponse> buscarBoletoApi = boletoServices.buscarBoletoApi(boletoRequest.getCode(), token);
        if (buscarBoletoApi.containErrors()) {
            response.addErrors(buscarBoletoApi.getErrors());
            return ResponseEntity.badRequest().body(response);
        }

        Boleto boleto = boletoServices.calcularBoleto(buscarBoletoApi.getData(), boletoRequest.getPayment_date());
        Optional<Boleto> optional = boletoServices.salvarBoleto(boleto);
        if (optional.isPresent()) {
            response.setData(optional.get().toBoletoResponse());
            return ResponseEntity.ok().body(response);
        }        
        
        return ResponseEntity.badRequest().body(response);
    }


    @GetMapping
	public ResponseEntity<Response<List<BoletoResponse>>> buscarTodos() {
        Response<List<BoletoResponse>> response = new Response<>();
        List<Boleto> boletos = boletoServices.buscarTodos();

        List<BoletoResponse> boletosResponse = boletos.stream().map(Boleto::toBoletoResponse).collect(Collectors.toList());
        response.setData(boletosResponse);
        return ResponseEntity.ok().body(response);
    }


    @GetMapping("/{id}")
	public ResponseEntity<Response<BoletoResponse>> buscar(@PathVariable("id") Integer id) {
        Response<BoletoResponse> response = new Response<>();
        Optional<Boleto> boleto = boletoServices.buscarBoleto(id);
        if (boleto.isPresent()) {
            BoletoResponse boletoResponse = boleto.get().toBoletoResponse();
            response.setData(boletoResponse);
        }
        return ResponseEntity.ok().body(response);
    }


    @GetMapping("/numero/{numero}")
	public ResponseEntity<Response<BoletoResponse>> buscar(@PathVariable("numero") String numero) {
        Response<BoletoResponse> response = new Response<>();
        Optional<Boleto> boleto = boletoServices.buscarBoleto(numero);
        if (boleto.isPresent()) {
            BoletoResponse boletoResponse = boleto.get().toBoletoResponse();
            response.setData(boletoResponse);
        }
        return ResponseEntity.ok().body(response);
    }


    @DeleteMapping("/{id}")
	public ResponseEntity<Response<BoletoResponse>> excluirBoleto(@PathVariable("id") Integer id) {
        Response<BoletoResponse> response = new Response<>();
        Boolean boleto = boletoServices.excluirBoleto(id);
        if (boleto) {
            return ResponseEntity.ok().body(response);
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/numero/{numero}")
	public ResponseEntity<Response<BoletoResponse>> excluirBoleto(@PathVariable("numero") String numero) {
        Response<BoletoResponse> response = new Response<>();
        Boolean boleto = boletoServices.excluirBoleto(numero);
        if (boleto) {
            return ResponseEntity.ok().body(response);
        }
        return ResponseEntity.notFound().build();
    }
}
