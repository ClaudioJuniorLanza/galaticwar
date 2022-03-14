package br.com.starwarsresistence.galaticwar.domain;

import br.com.starwarsresistence.galaticwar.enums.StatusTroca;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long idRebeldeSolicitante;

    @Column(nullable = false)
    private Long idRebeldeSolicitado;

    @Column(nullable = false)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Inventario> listItensRebeldeSolicitante;

    @Column(nullable = false)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Inventario> listItensRebeldeSolicitado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusTroca statusSolicitacaoTroca;

}
