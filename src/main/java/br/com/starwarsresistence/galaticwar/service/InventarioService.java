package br.com.starwarsresistence.galaticwar.service;

import br.com.starwarsresistence.galaticwar.dto.mapper.InventarioMapper;
import br.com.starwarsresistence.galaticwar.dto.request.InventarioDTO;
import br.com.starwarsresistence.galaticwar.repository.InventarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class InventarioService {

    private final InventarioRepository inventarioRepository;
    private final InventarioMapper inventarioMapper;

    public List<InventarioDTO> findAll(){
        return inventarioRepository.findAllItensInventario().stream()
                .map(inventarioMapper::toDTO).collect(Collectors.toList());
    }

}
