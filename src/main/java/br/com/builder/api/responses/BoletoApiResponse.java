package br.com.builder.api.responses;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoletoApiResponse {
    
    private String code;
    private String due_date;
    private Double amount;
    private String recipient_name;
    private String recipient_document;
    private String type;


    public static BoletoApiResponse jsonToObject(String json) {
        ObjectMapper mapper = new ObjectMapper();
        if (json == null) return null;
        
        try {
            return mapper.readValue(json, BoletoApiResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
