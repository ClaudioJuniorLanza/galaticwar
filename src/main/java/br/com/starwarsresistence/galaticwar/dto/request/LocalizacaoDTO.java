package br.com.starwarsresistence.galaticwar.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocalizacaoDTO {

    @NotEmpty
    private String nomeGalaxia;
    @NotEmpty
    private String latitude;
    @NotEmpty
    private String longitude;
}
