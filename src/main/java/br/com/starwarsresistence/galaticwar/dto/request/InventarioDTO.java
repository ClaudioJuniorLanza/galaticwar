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
public class InventarioDTO {

    private Long id;
    @NotEmpty
    private String itemInventario;
    @NotEmpty
    private Integer quantidade;
}
