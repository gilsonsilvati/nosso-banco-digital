package br.com.bancodigital.api.domain.model;

import br.com.bancodigital.api.domain.model.base.EntidadeBase;
import br.com.bancodigital.api.domain.model.enums.FormatoDocumento;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
@Getter @Setter
@DynamicUpdate
public class Documento extends EntidadeBase {

    @NotBlank(message = "Nome do documento é obrigatório")
    private String nome;

    @NotBlank(message = "Arquivo base64 é obrigatório")
    @Column(name = "arquivo_base64")
    private String arquivoBase64;

    @Enumerated(EnumType.STRING)
    private FormatoDocumento formato = FormatoDocumento.PNG;

    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "id_proposta")
    private Proposta proposta;

    @PrePersist @PreUpdate
    private void prePersistPreUpdate() {
        data = LocalDate.now();
    }

}
