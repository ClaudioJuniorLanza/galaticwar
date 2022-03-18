package br.com.starwarsresistence.galaticwar.dto.mapper;

import br.com.starwarsresistence.galaticwar.domain.TrocaItem;
import br.com.starwarsresistence.galaticwar.dto.request.TrocaItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TrocaItemMapper {

    TrocaItemMapper INSTANCE = Mappers.getMapper(TrocaItemMapper.class);

    TrocaItem toModel(TrocaItemDTO trocaItemDTO);

    TrocaItemDTO toDTO(TrocaItem trocaItem);
}
