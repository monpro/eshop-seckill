package com.seckill.service;

import com.seckill.error.BusinessException;
import com.seckill.service.model.UserModel;

public interface UserService {

    UserModel getUserById(Integer id);
    void register(UserModel userModel) throws BusinessException;
    UserModel validateLogin(String telephone, String encryptPassword) throws BusinessException;
}
