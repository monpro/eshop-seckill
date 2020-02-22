package com.seckill.controller;

import com.seckill.controller.viewobject.UserVO;
import com.seckill.error.BusinessException;
import com.seckill.error.EnumError;
import com.seckill.response.CommonResponseType;
import com.seckill.service.UserService;
import com.seckill.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@Controller("user")
@RequestMapping("/user")
@CrossOrigin
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;


    public CommonResponseType register(@RequestParam(name="telephone")String telephone,
                                       @RequestParam(name="otpCode")String otpCode,
                                       @RequestParam(name="name")String name,
                                       @RequestParam(name="gender")String gender,
                                       @RequestParam(name="age")String age) throws BusinessException {
       String inSessionOtpCode = (String) this.httpServletRequest.getSession().getAttribute(telephone);
       if(!inSessionOtpCode.equals(otpCode)){
           throw new BusinessException(EnumError.PARAMETER_INVALIDATION_ERROR, "otp code is wrong");
       }

       return null;
    }



    @RequestMapping(value = "/getotp", method={RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonResponseType getOtp(@RequestParam(name="telephone")String telephone){
        Random random = new Random();
        int randomInt = random.nextInt(99999);
        randomInt += 10000;
        String otpCode = String.valueOf(randomInt);

        httpServletRequest.getSession().setAttribute(telephone, otpCode);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("optCode", otpCode);
        return CommonResponseType.create(response);
    }

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
