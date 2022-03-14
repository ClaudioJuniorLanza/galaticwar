package br.com.starwarsresistence.galaticwar.dto.mapper;

import br.com.starwarsresistence.galaticwar.domain.TrocaItem;
import br.com.starwarsresistence.galaticwar.dto.request.TrocaItemDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TrocaItemMapper {

    TrocaItem toModel(TrocaItemDTO trocaItemDTO);

    TrocaItemDTO toDTO(TrocaItem trocaItem);
}
