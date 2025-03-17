package com.pleaseWorkNow.hope.service.user;

import com.pleaseWorkNow.hope.model.User;
import com.pleaseWorkNow.hope.model.UserDto;
import com.pleaseWorkNow.hope.request.CreateUserRequest;
import com.pleaseWorkNow.hope.request.UpdateUserRequest;

public interface IUserService {
    User getUserById(Long id);
    User createUser(CreateUserRequest request);
    User updateUser(UpdateUserRequest request, Long id);
    void deleteUser(Long id);

    UserDto convertUserToDto(User user);
}
