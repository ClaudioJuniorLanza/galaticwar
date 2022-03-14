package br.com.starwarsresistence.galaticwar.repository;

import br.com.starwarsresistence.galaticwar.domain.TrocaItem;
import br.com.starwarsresistence.galaticwar.dto.request.TrocaItemDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrocaItemRepository extends JpaRepository<TrocaItem, Long> {
    List<TrocaItem> findByIdRebeldeSolicitante(Long id);

    List<TrocaItem> findByIdRebeldeSolicitado(Long id);
}
