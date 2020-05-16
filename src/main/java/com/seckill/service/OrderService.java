package com.seckill.service;

import com.seckill.error.BusinessException;
import com.seckill.service.model.OrderModel;

public interface OrderService {
    OrderModel createOrder(Integer userId, Integer itemId, Integer promoId, Integer amount) throws BusinessException;


}
