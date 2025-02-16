package com.tcgmarketplace.user;

import com.tcgmarketplace.user.dto.CreateUserDto;
import com.tcgmarketplace.user.dto.UpdateUserDto;
import com.tcgmarketplace.user.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto getUserById(Integer id);
    UserDto findUserByUsername(String username);
    UserDto createUser(CreateUserDto dto);
    UserDto updateUser(Integer id, UpdateUserDto dto);
    void deleteUser(Integer id);
}
