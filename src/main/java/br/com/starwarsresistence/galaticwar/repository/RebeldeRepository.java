package br.com.starwarsresistence.galaticwar.repository;

import br.com.starwarsresistence.galaticwar.domain.Rebelde;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface RebeldeRepository extends JpaRepository<Rebelde, Long> {
    List<Rebelde> findByIdIn(List<Long> listRebeldeId);
}
