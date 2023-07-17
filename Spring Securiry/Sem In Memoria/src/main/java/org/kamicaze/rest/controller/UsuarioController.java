package org.kamicaze.rest.controller;


import lombok.RequiredArgsConstructor;
import org.kamicaze.domain.entity.Usuario;
import org.kamicaze.exception.SenhaInvalidException;
import org.kamicaze.rest.dto.CredenciaisDTO;
import org.kamicaze.rest.dto.TokenDTO;
import org.kamicaze.security.jwt.JwtService;
import org.kamicaze.service.Impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/users/")
@RequiredArgsConstructor
public class UsuarioController {

    private final UserServiceImpl service;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario salvar (@RequestBody Usuario usuario){
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return service.salvar(usuario);
    }
    @PostMapping("/auth")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciaisDTO){
        try{
            Usuario user = new Usuario();
            user.setLogin(credenciaisDTO.getLogin());
            user.setSenha(credenciaisDTO.getSenha());
            UserDetails userAutenticado = service.autenticar(user);
            String token = jwtService.gerarToken(user);
            return new TokenDTO(user.getLogin(),token);

        }catch (UsernameNotFoundException |SenhaInvalidException  ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        }
    }
}
