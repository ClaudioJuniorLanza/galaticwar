package br.com.starwarsresistence.galaticwar.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CredenciaisDTO {

    private String email;
    private String pass;
}
