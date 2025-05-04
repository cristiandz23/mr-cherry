package com.MrCherry.app.service.servInterface;

import com.MrCherry.app.model.Address;

public interface IUserService {
    public Address getAddressById(Long userId);
}
