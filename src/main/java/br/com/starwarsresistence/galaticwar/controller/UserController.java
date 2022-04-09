package br.com.starwarsresistence.galaticwar.controller;

import br.com.starwarsresistence.galaticwar.domain.Usuario;
import br.com.starwarsresistence.galaticwar.dto.request.CredenciaisDTO;
import br.com.starwarsresistence.galaticwar.dto.request.TokenDTO;
import br.com.starwarsresistence.galaticwar.exceptions.SenhaInvalidaException;
import br.com.starwarsresistence.galaticwar.security.jwt.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private JwtService jwtService;

    private AuthenticationManager authenticationManager;

    @RequestMapping(value = "/autenticar", method = RequestMethod.POST)
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciaisDTO){
        try{
            Usuario usuario = Usuario.builder()
                    .email(credenciaisDTO.getEmail())
                    .pass(credenciaisDTO.getPass()).build();
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(usuario.getEmail(), usuario.getPass());
            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            String token = jwtService.gerarToken(authentication);
            return new TokenDTO(usuario.getEmail(), token);
        }catch(UsernameNotFoundException | SenhaInvalidaException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
