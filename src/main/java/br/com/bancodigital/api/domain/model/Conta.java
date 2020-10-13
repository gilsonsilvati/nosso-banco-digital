package br.com.bancodigital.api.domain.model;

import br.com.bancodigital.api.domain.model.base.EntidadeBase;
import br.com.bancodigital.api.domain.model.enums.TipoConta;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
@DynamicUpdate
public class Conta extends EntidadeBase {

    @NonNull
    private Integer agencia;

    @NonNull
    private Integer conta;

    @NonNull
    @OneToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_conta")
    private TipoConta tipo;

    private Integer codigoBanco;
    private BigDecimal saldo;

    @PrePersist
    private void prePersist() {
        tipo = TipoConta.CORRENTE;
        codigoBanco = 341;
        saldo = BigDecimal.ZERO;
    }

}
