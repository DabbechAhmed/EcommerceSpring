package com.pleaseWorkNow.hope.model;

import com.pleaseWorkNow.hope.dto.CartDto;
import com.pleaseWorkNow.hope.dto.OrderDto;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<OrderDto> orders;
    private CartDto cart;

}
