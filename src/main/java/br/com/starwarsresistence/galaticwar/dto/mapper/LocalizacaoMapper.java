package br.com.starwarsresistence.galaticwar.dto.mapper;

import br.com.starwarsresistence.galaticwar.domain.Localizacao;
import br.com.starwarsresistence.galaticwar.dto.request.LocalizacaoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocalizacaoMapper {

    Localizacao toModel(LocalizacaoDTO localizacaoDTO);

    LocalizacaoDTO toDTO(Localizacao localizacao);
}
