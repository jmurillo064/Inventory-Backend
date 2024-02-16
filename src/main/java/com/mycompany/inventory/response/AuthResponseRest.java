package com.mycompany.inventory.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponseRest extends ResponseRest{
    private AuthResponse authResponse = new AuthResponse();
}
