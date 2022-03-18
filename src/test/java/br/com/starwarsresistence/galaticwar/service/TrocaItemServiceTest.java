package br.com.starwarsresistence.galaticwar.service;

import br.com.starwarsresistence.galaticwar.builder.RebeldeDTOBuilder;
import br.com.starwarsresistence.galaticwar.builder.TrocaItemDTOBuilder;
import br.com.starwarsresistence.galaticwar.domain.TrocaItem;
import br.com.starwarsresistence.galaticwar.dto.mapper.TrocaItemMapper;
import br.com.starwarsresistence.galaticwar.dto.request.RebeldeDTO;
import br.com.starwarsresistence.galaticwar.dto.request.TrocaItemDTO;
import br.com.starwarsresistence.galaticwar.exceptions.TraidorException;
import br.com.starwarsresistence.galaticwar.repository.TrocaItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrocaItemServiceTest {

    @Mock
    private TrocaItemRepository trocaItemRepository;

    private TrocaItemMapper trocaItemMapper = TrocaItemMapper.INSTANCE;

    @InjectMocks
    private TrocaItemService trocaItemService;

    @Test
    void shouldThrowExceptionWhenHasTraidor(){
        RebeldeDTO expectedFoundRebeldeDTO = RebeldeDTOBuilder.builder().build().toRebeldeDTO();
        expectedFoundRebeldeDTO.setTraidor(true);
        List<RebeldeDTO> rebeldeDTOList = Arrays.asList(expectedFoundRebeldeDTO);

        assertThrows(TraidorException.class, () -> trocaItemService.hasTraidor(rebeldeDTOList));

    }

    @Test
    void shouldBeSearchSolicitacaoEnviadaWhenReturnATrocaItemList(){
        TrocaItemDTO expectedFoundTrocaItemDTO = TrocaItemDTOBuilder.builder().build().toTrocaItemDTO();
        TrocaItem trocaItem = trocaItemMapper.toModel(expectedFoundTrocaItemDTO);

        when(trocaItemRepository.findByIdRebeldeSolicitante(expectedFoundTrocaItemDTO.getIdRebeldeSolicitante())).thenReturn(Collections.singletonList(trocaItem));

        List<TrocaItemDTO> itemDTOList = trocaItemService.searchSolicitacaoTrocaEnviada(expectedFoundTrocaItemDTO.getIdRebeldeSolicitante());

        assertThat(itemDTOList, is(not(empty())));

    }

    @Test
    void shouldBeSearchSolicitacaoRecebidaWhenReturnATrocaItemList(){
        TrocaItemDTO expectedFoundTrocaItemDTO = TrocaItemDTOBuilder.builder().build().toTrocaItemDTO();
        TrocaItem trocaItem = trocaItemMapper.toModel(expectedFoundTrocaItemDTO);

        when(trocaItemRepository.findByIdRebeldeSolicitante(expectedFoundTrocaItemDTO.getIdRebeldeSolicitado())).thenReturn(Collections.singletonList(trocaItem));

        List<TrocaItemDTO> itemDTOList = trocaItemService.searchSolicitacaoTrocaEnviada(expectedFoundTrocaItemDTO.getIdRebeldeSolicitado());

        assertThat(itemDTOList, is(not(empty())));

    }


}
