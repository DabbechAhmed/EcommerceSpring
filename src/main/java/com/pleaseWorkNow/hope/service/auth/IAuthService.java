package com.pleaseWorkNow.hope.service.auth;

import com.pleaseWorkNow.hope.request.LoginRequest;
import com.pleaseWorkNow.hope.request.RegisterRequest;
import com.pleaseWorkNow.hope.response.JwtResponse;

public interface IAuthService {

    JwtResponse login(LoginRequest request);
    JwtResponse register(RegisterRequest request);

}
