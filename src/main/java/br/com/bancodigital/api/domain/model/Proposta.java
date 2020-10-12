package br.com.bancodigital.api.domain.model;

import br.com.bancodigital.api.domain.model.base.EntidadeBase;
import br.com.bancodigital.api.domain.model.enums.StatusProposta;
import br.com.bancodigital.api.domain.model.enums.TipoProposta;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "proposta")
@Getter @Setter
@DynamicUpdate
public class Proposta extends EntidadeBase {

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_proposta")
    private TipoProposta tipo = TipoProposta.ABERTURA_CONTA;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_proposta")
    private StatusProposta status;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    private LocalDate data;

    @PrePersist @PreUpdate
    private void prePersistPreUpdate() {
        data = LocalDate.now();
        status = status == null ? StatusProposta.SOLICITADA : status;
    }

}
