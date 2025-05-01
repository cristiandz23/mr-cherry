package com.MrCherry.app.dto;

import com.MrCherry.app.model.ContactInformation;
import com.MrCherry.app.model.enums.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class UserRequest {

    @Email @NotBlank @NotEmpty
    private String email;
    @NotBlank
    private String password;
    private Role role;
    private ContactInformation contactInformation;


}
