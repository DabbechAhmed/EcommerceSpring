package com.pleaseWorkNow.hope.controller;

import com.pleaseWorkNow.hope.exceptions.AlreadyExistsException;
import com.pleaseWorkNow.hope.exceptions.ResourceNotFoundException;
import com.pleaseWorkNow.hope.model.User;
import com.pleaseWorkNow.hope.model.UserDto;
import com.pleaseWorkNow.hope.request.CreateUserRequest;
import com.pleaseWorkNow.hope.request.UpdateUserRequest;
import com.pleaseWorkNow.hope.response.ApiResponse;
import com.pleaseWorkNow.hope.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final IUserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable  Long userId){
        try {
            User user = userService.getUserById(userId);
            UserDto userDto= userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("User retrieved success",userDto));
        } catch (ResourceNotFoundException e) {
          return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request){
        try {
            User user = userService.createUser(request);
            UserDto userDto= userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("User created success",userDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UpdateUserRequest request, @PathVariable Long userId){
        try {
            User user = userService.updateUser(request,userId);
            UserDto userDto= userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("User updated success",userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("User deleted success",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
}
