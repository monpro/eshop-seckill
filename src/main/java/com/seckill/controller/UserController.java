package com.seckill.controller;

import com.seckill.controller.viewobject.UserVO;
import com.seckill.error.BusinessException;
import com.seckill.response.CommonResponseType;
import com.seckill.service.UserService;
import com.seckill.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



@Controller("user")
@RequestMapping("/user")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @RequestMapping("/get")
    @ResponseBody
    public CommonResponseType getUser(@RequestParam(name="id") Integer id) throws BusinessException {
        UserModel userModel = userService.getUserById(id);
        if(userModel == null){
            userModel.setEncryptPassword("sdf");
//            throw new BusinessException(EnumError.USER_NOT_EXIST);
        }
        UserVO userVO = getVOFromModel(userModel);

        return CommonResponseType.create(userVO);
    }

    private UserVO getVOFromModel(UserModel userModel){
        if(userModel == null){
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel, userVO);
        return userVO;
    }

}
