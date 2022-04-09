package br.com.starwarsresistence.galaticwar.service;

import br.com.starwarsresistence.galaticwar.domain.Usuario;
import br.com.starwarsresistence.galaticwar.exceptions.SenhaInvalidaException;
import br.com.starwarsresistence.galaticwar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /*
    @Transactional
    public Usuario save(Usuario usuario){
        String senhaCriptografada = passwordEncoder.encode(usuario.getPass());
        usuario.setPass(senhaCriptografada);
        return userRepository.save(usuario);
    }

    public UserDetails autenticar(Usuario usuario){
        UserDetails user = loadUserByUsername(usuario.getEmail());
        boolean senhasBatem = passwordEncoder.matches(usuario.getPass(), user.getPassword());
        if(senhasBatem){
            return user;
        }
        throw new SenhaInvalidaException();
    }
    */
    @Override
    public UserDetails loadUserByUsername(String loginUsuario) throws UsernameNotFoundException {
        Usuario usuario = new Usuario();

        String[] roles = new String[] {"ADMIN", "USER"};
        //String[] roles = (String[]) usuario.getPerfis().stream().map(Perfil::getName).toArray();

        return User.builder()
                .username("clanca")
                .password("123")
                .roles(roles)
                .build();
    }
}
