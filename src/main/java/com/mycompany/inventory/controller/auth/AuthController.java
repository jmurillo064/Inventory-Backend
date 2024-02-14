package com.mycompany.inventory.controller.auth;

import com.mycompany.inventory.response.AuthResponseRest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class  AuthController {

    private final AuthService authService;

    @PostMapping(value = "login")
    public ResponseEntity<AuthResponseRest> login(@RequestBody LoginRequest request){
        ResponseEntity<AuthResponseRest> response = authService.login(request);
        return response;
    }

    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }
}
