package com.seckill.controller;

import com.seckill.error.BusinessException;
import com.seckill.error.EnumError;
import com.seckill.response.CommonResponseType;
import com.seckill.service.OrderService;
import com.seckill.service.model.OrderModel;
import com.seckill.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller("user")
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @RequestMapping(value="/createOrder", method = {RequestMethod.POST}, consumes={CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonResponseType createOrder(@RequestParam(name="itemId") Integer itemId,
                                          @RequestParam(name = "amount") Integer amount
                                          ) throws BusinessException {

        Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if(isLogin == null || !isLogin.booleanValue()){
            throw new BusinessException(EnumError.USER_NOT_LOGIN);
        }

        UserModel userModel = (UserModel) httpServletRequest.getSession().getAttribute("LOGIN_USER");

        OrderModel orderModel = orderService.createOrder(userModel.getId(), itemId, amount);

        return CommonResponseType.create(orderModel);
    }
}
