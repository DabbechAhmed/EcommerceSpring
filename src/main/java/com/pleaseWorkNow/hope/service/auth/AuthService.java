package com.pleaseWorkNow.hope.service.auth;

import com.pleaseWorkNow.hope.exceptions.AlreadyExistsException;
import com.pleaseWorkNow.hope.exceptions.ResourceNotFoundException;
import com.pleaseWorkNow.hope.model.Role;
import com.pleaseWorkNow.hope.model.User;
import com.pleaseWorkNow.hope.repository.RoleRepository;
import com.pleaseWorkNow.hope.repository.UserRepository;
import com.pleaseWorkNow.hope.request.LoginRequest;
import com.pleaseWorkNow.hope.request.RegisterRequest;
import com.pleaseWorkNow.hope.response.JwtResponse;
import com.pleaseWorkNow.hope.security.jwt.JwtUtils;
import com.pleaseWorkNow.hope.security.user.ShopUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService{
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Override
    public JwtResponse login(LoginRequest request) {
        return null;
    }

    @Override
    public JwtResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail()))
            throw new AlreadyExistsException("User with email " + request.getEmail() + " already exists");

        String roleName= request.getRole() != null ? request.getRole() : "ROLE_USER";

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        Set<Role> roles= new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateTokenForUser(authentication);
        ShopUserDetails userDetails = (ShopUserDetails) authentication.getPrincipal();
        return new JwtResponse(userDetails.getId(), jwt);
    }
}
