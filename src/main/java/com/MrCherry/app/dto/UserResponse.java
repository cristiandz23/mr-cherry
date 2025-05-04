package com.MrCherry.app.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class UserResponse {

    private Long id;
    private String email;
    private ContactInformationDto contactInformation;
    private List<OrderResponse> orders;


}
