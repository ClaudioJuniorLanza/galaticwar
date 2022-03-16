package br.com.starwarsresistence.galaticwar.domain;

import br.com.starwarsresistence.galaticwar.enums.StatusTroca;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "troca_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrocaItem {

    @Schema(description = "Codigo da solicitação de troca")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Codigo do rebelde que está realizando a solicitação de troca")
    @Column(nullable = false)
    private Long idRebeldeSolicitante;

    @Schema(description = "Codigo do rebelde que será solicitado a troca")
    @Column(nullable = false)
    private Long idRebeldeSolicitado;

    @Schema(description = "Lista de itens para troca do rebelde solicitante")
    @Column(nullable = false)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Inventario> listItensRebeldeSolicitante;

    @Schema(description = "Lista de itens para troca do rebelde solicitado")
    @Column(nullable = false)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Inventario> listItensRebeldeSolicitado;

    @Schema(description = "Status da solicitação")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusTroca statusSolicitacaoTroca;

}
