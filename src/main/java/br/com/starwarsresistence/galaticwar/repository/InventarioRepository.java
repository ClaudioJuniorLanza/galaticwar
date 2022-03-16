package br.com.starwarsresistence.galaticwar.repository;


import br.com.starwarsresistence.galaticwar.domain.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {

    @Query(value = "SELECT i.* FROM INVENTARIO i JOIN REBELDE_INVENTARIO ri ON ri.INVENTARIO_ID = i.ID", nativeQuery = true)
    List<Inventario> findAllItensInventario();
}
