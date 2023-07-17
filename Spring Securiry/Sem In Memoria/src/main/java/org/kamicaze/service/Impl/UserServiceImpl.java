package org.kamicaze.service.Impl;
import org.kamicaze.domain.entity.Usuario;
import org.kamicaze.domain.repository.Usuarios;
import org.kamicaze.exception.SenhaInvalidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Usuarios usuarios;

    @Transactional
    public Usuario salvar (Usuario u){
        return usuarios.save(u);
    }
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Usuario user = usuarios.findByLogin(s).orElseThrow(()-> new UsernameNotFoundException("User not found lll"));

        String [] roles = user.isAdmin()? new String[]{"ADMIN","USER"} :new String[]{"USER"};

        return User
                .builder()
                .username(user.getLogin())
                .password(user.getSenha())
                .roles(roles)
                .build();
    }

    public UserDetails autenticar(Usuario usuario){
        UserDetails user = loadUserByUsername(usuario.getLogin());
        boolean verificar = passwordEncoder.matches(usuario.getSenha(), user.getPassword());
        if(verificar){
            return user;
        }
        throw new SenhaInvalidException();
    }
    /*
    LOGIN EM MEMORIA
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if(!s.equals("ricardo")){
            throw new UsernameNotFoundException("User errado");
        }
        return User
                .builder()
                .username("ricardo")
                .password(passwordEncoder.encode("123"))
                .roles("USER","ADMIN")
                .build();
    }*/
}
