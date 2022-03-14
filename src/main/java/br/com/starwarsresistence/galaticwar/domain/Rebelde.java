package br.com.starwarsresistence.galaticwar.domain;

import br.com.starwarsresistence.galaticwar.enums.Genero;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Integer idade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genero genero;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "localizacao_id")
    private Localizacao localizacao;

    @Column(nullable = false)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Inventario> inventario;

    @Column(columnDefinition = "NUMBER(2) default 0", insertable = false, updatable = true)
    private Integer reportTraidor;

    @Column
    private boolean traidor;

    @Column(columnDefinition = "NUMBER(2) default 0", insertable = false, updatable = true)
    private Integer quantidadeSolicitacao;

}
