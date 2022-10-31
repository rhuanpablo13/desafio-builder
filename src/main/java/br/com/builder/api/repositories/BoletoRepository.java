package br.com.builder.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.builder.api.entities.Boleto;

@Repository
public interface BoletoRepository extends JpaRepository<Boleto, Integer> {
    
    /**
     * 
     * @param numero
     * @return
     */
    Optional<Boleto> findByNumero(@Param("numero") String numero);
}
