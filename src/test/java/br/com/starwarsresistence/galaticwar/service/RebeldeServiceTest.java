package br.com.starwarsresistence.galaticwar.service;

import br.com.starwarsresistence.galaticwar.builder.RebeldeDTOBuilder;
import br.com.starwarsresistence.galaticwar.domain.Rebelde;
import br.com.starwarsresistence.galaticwar.dto.mapper.RebeldeMapper;
import br.com.starwarsresistence.galaticwar.dto.request.RebeldeDTO;
import br.com.starwarsresistence.galaticwar.exceptions.RebeldeNotFoundException;
import br.com.starwarsresistence.galaticwar.repository.RebeldeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RebeldeServiceTest {

    @Mock
    private RebeldeRepository rebeldeRepository;

    private RebeldeMapper rebeldeMapper = RebeldeMapper.INSTANCE;

    @InjectMocks
    private RebeldeService rebeldeService;

    @Test
    void shouldThrowExceptionWhenNotExistsRebelde(){
        RebeldeDTO expectedRebeldeDTO = RebeldeDTOBuilder.builder().build().toRebeldeDTO();

        when(rebeldeRepository.findById(expectedRebeldeDTO.getId())).thenReturn(Optional.empty());

        assertThrows(RebeldeNotFoundException.class, () -> rebeldeService.verifyIsExistsRebelde(expectedRebeldeDTO.getId()));
    }

    @Test
    void shouldBeIdValidWhenReturnARebelde() throws RebeldeNotFoundException {
        RebeldeDTO expectedFoundRebeldeDTO = RebeldeDTOBuilder.builder().build().toRebeldeDTO();
        Rebelde expectedFoundRebelde = rebeldeMapper.toModel(expectedFoundRebeldeDTO);

        when(rebeldeRepository.findById(expectedFoundRebelde.getId())).thenReturn(Optional.of(expectedFoundRebelde));

        RebeldeDTO foundRebeldeDTO = rebeldeService.verifyIsExistsRebelde(expectedFoundRebeldeDTO.getId());

        assertThat(foundRebeldeDTO, is(equalTo(expectedFoundRebeldeDTO)));
    }

    @Test
    void shouldBeFindAllWhenReturnRebeldeList(){
        RebeldeDTO expectedFoundRebeldeDTO = RebeldeDTOBuilder.builder().build().toRebeldeDTO();
        Rebelde expectedFoundRebelde = rebeldeMapper.toModel(expectedFoundRebeldeDTO);

        when(rebeldeRepository.findAll()).thenReturn(Collections.singletonList(expectedFoundRebelde));

        List<RebeldeDTO> foundListRebeldeDTO = rebeldeService.findAll();

        assertThat(foundListRebeldeDTO, is(not(empty())));
        assertThat(foundListRebeldeDTO.get(0), is(equalTo(expectedFoundRebeldeDTO)));
    }

    @Test
    void shouldBeFindAllWhenReturnAEmptyList(){
        when(rebeldeRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        List<RebeldeDTO> foundListRebeldeDTO = rebeldeService.findAll();

        assertThat(foundListRebeldeDTO, is(empty()));
    }

    @Test
    void shouldBeRebeldeValidWhenRebeldeIsNotTraidor(){

        RebeldeDTO expectedFoundRebeldeDTO = RebeldeDTOBuilder.builder().build().toRebeldeDTO();
        Rebelde expectedFoundRebelde = rebeldeMapper.toModel(expectedFoundRebeldeDTO);

        assertFalse(expectedFoundRebelde.isTraidor());

    }

    @Test
    void shouldBeNotRebeldeValidWhenRebeldeIsTraidor(){

        RebeldeDTO expectedFoundRebeldeDTO = RebeldeDTOBuilder.builder().build().toRebeldeDTO();
        expectedFoundRebeldeDTO.setTraidor(true);
        Rebelde expectedFoundRebelde = rebeldeMapper.toModel(expectedFoundRebeldeDTO);

        assertTrue(expectedFoundRebelde.isTraidor());

    }

    @Test
    void shouldBeRelatedTraidor() throws RebeldeNotFoundException {
        RebeldeDTO expectedFoundRebeldeDTO = RebeldeDTOBuilder.builder().build().toRebeldeDTO();
        Rebelde expectedFoundRebelde = rebeldeMapper.toModel(expectedFoundRebeldeDTO);
        expectedFoundRebelde.setReportTraidor(2);

        when(rebeldeRepository.findById(expectedFoundRebelde.getId())).thenReturn(Optional.of(expectedFoundRebelde));
        when(rebeldeRepository.save(expectedFoundRebelde)).thenReturn(expectedFoundRebelde);

        rebeldeService.relatedTraidor(expectedFoundRebelde.getId());

        assertEquals(3, expectedFoundRebelde.getReportTraidor());
    }

}
