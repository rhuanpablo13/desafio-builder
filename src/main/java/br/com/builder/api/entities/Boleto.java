package br.com.builder.api.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.builder.api.responses.BoletoResponse;
import br.com.builder.api.utils.DataUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "BOLETO")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Boleto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String numero;
    private Date dataPagamento;
    private Date dataVencimento;
    private Integer diasAtraso;
    private Double valorMulta;
    private Double valorJuros;
    private Double valorCobrado;
    private Double valorBoleto;


    public BoletoResponse toBoletoResponse() {
        return BoletoResponse.builder()
        .id(id)
        .amount(valorBoleto)
        .due_date(DataUtils.toString(dataVencimento, DataUtils.yyyyMMdd_HHmmss))
        .fine_amount_calculated(valorMulta)
        .interest_amount_calculated(valorJuros)
        .original_amount(valorBoleto)
        .payment_date(DataUtils.toString(dataPagamento, DataUtils.yyyyMMdd_HHmmss))
        .build();
    }
}
