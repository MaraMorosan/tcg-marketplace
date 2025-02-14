package com.tcgmarketplace.user.dto;

import com.tcgmarketplace.user.Role;

public record CreateUserDto(
        String username,
        String email,
        String password,
        Role role
) {}
