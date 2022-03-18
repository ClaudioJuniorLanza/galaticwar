package br.com.starwarsresistence.galaticwar.dto.mapper;

import br.com.starwarsresistence.galaticwar.domain.Rebelde;
import br.com.starwarsresistence.galaticwar.dto.request.RebeldeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RebeldeMapper {

    RebeldeMapper INSTANCE = Mappers.getMapper(RebeldeMapper.class);

    Rebelde toModel(RebeldeDTO rebeldeDTO);

    RebeldeDTO toDTO(Rebelde rebeldeModel);
}
