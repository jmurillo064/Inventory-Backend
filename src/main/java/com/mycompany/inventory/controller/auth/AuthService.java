package com.mycompany.inventory.controller.auth;

import com.mycompany.inventory.Utils.Role;
import com.mycompany.inventory.dao.IUserDao;
import com.mycompany.inventory.jwt.JwtService;
import com.mycompany.inventory.model.User;
import com.mycompany.inventory.response.AuthResponseRest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final IUserDao iUserDao;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

//    public AuthResponse login(LoginRequest request){
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
//        } catch (AuthenticationException e) {
//            log.error("Error en authentication: {}", e.getMessage(), e);
//        }
//        UserDetails user = iUserDao.findByUsername(request.getUsername())
//                .orElseThrow();
//        String token = jwtService.getToken(user);
//        return AuthResponse.builder()
//                .token(token)
//                .build();
//    }

    public ResponseEntity<AuthResponseRest>  login(LoginRequest request){
        AuthResponseRest response = new AuthResponseRest();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            UserDetails user = iUserDao.findByUsername(request.getUsername())
                    .orElseThrow();
            String token = jwtService.getToken(user);
            response.getAuthResponse().setToken(token);
            response.setMetadata("Respuesta OK", "00", "Inicio de sesión exitoso");
        } catch (BadCredentialsException e) {
            response.setMetadata("Respuesta Error", "-1", "Credenciales inválidas");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        } catch (UsernameNotFoundException e) {
            log.error("Error en authentication: {}", e.getMessage(), e);
            response.setMetadata("Respuesta Error", "-1", "Usuario no encontrado");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (AuthenticationException e) {
            log.error("Error en authentication: {}", e.getMessage(), e);
            response.setMetadata("Respuesta Error", "-1", "No autorizado");
            return new ResponseEntity<AuthResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error("Error en el servidor: {}", e.getMessage(), e);
            response.setMetadata("Respuesta Error", "-1", "Error en el inicio de sesión");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<AuthResponseRest>(response, HttpStatus.OK);
    }

    public AuthResponse register(RegisterRequest request){
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .role(Role.USER)
                .build();
        iUserDao.save(user);
        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }

}
