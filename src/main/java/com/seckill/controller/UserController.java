package com.seckill.controller;

import com.alibaba.druid.util.StringUtils;
import com.seckill.controller.viewobject.UserVO;
import com.seckill.dataobject.UserDO;
import com.seckill.error.BusinessException;
import com.seckill.error.EnumError;
import com.seckill.response.CommonResponseType;
import com.seckill.service.UserService;
import com.seckill.service.model.UserModel;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@Controller("user")
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @RequestMapping(value="/register", method = {RequestMethod.POST}, consumes={CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonResponseType register(@RequestParam(name="telephone")String telephone,
                                       @RequestParam(name="otpCode")String otpCode,
                                       @RequestParam(name="name")String name,
                                       @RequestParam(name="gender")Byte gender,
                                       @RequestParam(name="password")String password,
                                       @RequestParam(name="age")Integer age) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
       String inSessionOtpCode = (String) this.httpServletRequest.getSession().getAttribute(telephone);
       if(!inSessionOtpCode.equals(otpCode)){
           throw new BusinessException(EnumError.PARAMETER_INVALIDATION_ERROR, "otp code is wrong");
       }

       UserModel userModel = new UserModel();
       userModel.setName(name);
       userModel.setGender(gender);
       userModel.setAge(age);
       userModel.setTelephone(telephone);
       userModel.setRegisterMode("byPhone");
       userModel.setEncryptPassword(this.EncodeByMd5(password));

       userService.register(userModel);
       return CommonResponseType.create(null);
    }

    public String EncodeByMd5(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();

        String encPassWord = base64Encoder.encode(md5.digest(password.getBytes("utf-8")));

        return encPassWord;
    }

    @RequestMapping(value="/login", method = {RequestMethod.POST}, consumes={CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonResponseType login(@RequestParam(name="telephone")String telephone,
                                    @RequestParam(name="password")String password) throws BusinessException {
        if(StringUtils.isEmpty(telephone) || StringUtils.isEmpty(password))
            throw new BusinessException(EnumError.PARAMETER_INVALIDATION_ERROR);
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
