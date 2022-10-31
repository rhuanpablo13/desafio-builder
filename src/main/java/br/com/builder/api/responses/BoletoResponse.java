package br.com.builder.api.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoletoResponse {
    
    private Integer id;
    private Double original_amount;
    private Double amount;
    private String due_date;
    private String payment_date;
    private Double interest_amount_calculated; // juros
    private Double fine_amount_calculated; // multa
}
