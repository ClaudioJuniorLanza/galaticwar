package br.com.starwarsresistence.galaticwar.dto.mapper;

import br.com.starwarsresistence.galaticwar.domain.Rebelde;
import br.com.starwarsresistence.galaticwar.dto.request.RebeldeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RebeldeMapper {

    Rebelde toModel(RebeldeDTO rebeldeDTO);

    RebeldeDTO toDTO(Rebelde rebeldeModel);
}
