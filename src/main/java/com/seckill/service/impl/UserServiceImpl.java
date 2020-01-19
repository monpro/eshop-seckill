package com.seckill.service.impl;

import com.seckill.dao.UserDOMapper;
import com.seckill.dataobject.UserDO;
import com.seckill.dataobject.UserPasswordDO;
import com.seckill.service.UserService;
import com.seckill.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private UserPasswordDO userPasswordDO;

    @Override
    public UserModel getUserById(Integer id) {
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);

        return null;


    }

    private UserModel getUserModelFromDataObject(UserDO userDO, UserPasswordDO userPasswordDO){
        if(userDO == null){
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO, userModel);
        if(userPasswordDO != null){
            userModel.setEncryptPassword(userPasswordDO.getEncryptPassword());
        }

        return userModel;
    }
}
