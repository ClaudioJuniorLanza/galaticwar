package br.com.starwarsresistence.galaticwar.security.jwt;

import br.com.starwarsresistence.galaticwar.domain.Usuario;
import br.com.starwarsresistence.galaticwar.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JwtAuthFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    private UserRepository userRepository;

    public JwtAuthFilter(JwtService jwtService, UserRepository userRepository){
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer")){
            String token = authorization.split(" ")[1];
            boolean isValid = jwtService.tokenValido(token);

            if (isValid){
                String loginUsuario = jwtService.obterLoginUsuario(token);
                Optional<Usuario> usuarioOptional = userRepository.findByEmail(loginUsuario);

                if(usuarioOptional.isPresent()){
                    Usuario usuario = usuarioOptional.get();
                    UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(usuario,  null, usuario.getPerfis());
                    SecurityContextHolder.getContext().setAuthentication(user);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
