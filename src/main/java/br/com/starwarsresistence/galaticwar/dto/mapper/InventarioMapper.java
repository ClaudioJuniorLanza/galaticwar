package br.com.starwarsresistence.galaticwar.dto.mapper;

import br.com.starwarsresistence.galaticwar.domain.Inventario;
import br.com.starwarsresistence.galaticwar.dto.request.InventarioDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventarioMapper {

    Inventario toModel(InventarioDTO inventarioDTO);
    InventarioDTO toDTO(Inventario inventario);
}
