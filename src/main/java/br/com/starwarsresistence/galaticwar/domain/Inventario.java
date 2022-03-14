package br.com.starwarsresistence.galaticwar.domain;

import br.com.starwarsresistence.galaticwar.enums.ItensInventario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "inventario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ItensInventario itemInventario;

    @Column(nullable = false)
    private Integer quantidade;


}
