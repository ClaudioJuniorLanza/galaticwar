package br.com.starwarsresistence.galaticwar.domain;

import br.com.starwarsresistence.galaticwar.enums.Genero;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "rebelde")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rebelde {

    @Schema(description = "Codigo do rebelde")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Nome do rebelde")
    @Column(nullable = false)
    private String nome;

    @Schema(description = "Idade do rebelde")
    @Column(nullable = false)
    private Integer idade;

    @Schema(description = "Genero do rebelde - MASCULINO, FEMININO")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genero genero;

    @Schema(description = "Codigo de referencia da localizacao do rebelde")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "localizacao_id")
    private Localizacao localizacao;

    @Schema(description = "Lista de itens do inventário do rebelde")
    @Column(nullable = false)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Inventario> inventario;

    @Schema(description = "Quantidade de vezes que foi reportado o reelde como traidor")
    @Column(columnDefinition = "NUMBER(2) default 0", insertable = false, updatable = true)
    private Integer reportTraidor;

    @Schema(description = "Rebelde traidor")
    @Column
    private boolean traidor;

    @Schema(description = "Quantidade de solicitações de troca")
    @Column(columnDefinition = "NUMBER(2) default 0", insertable = false, updatable = true)
    private Integer quantidadeSolicitacao;

}
