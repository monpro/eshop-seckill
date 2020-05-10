package com.seckill.service.impl;

import com.seckill.dao.OrderDOMapper;
import com.seckill.dataobject.OrderDO;
import com.seckill.error.BusinessException;
import com.seckill.error.EnumError;
import com.seckill.service.ItemService;
import com.seckill.service.OrderService;
import com.seckill.service.UserService;
import com.seckill.service.model.ItemModel;
import com.seckill.service.model.OrderModel;
import com.seckill.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderDOMapper orderDOMapper;

    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer itemId, Integer amount) throws BusinessException {
        ItemModel itemModel = itemService.getItemById(itemId);
        if(itemModel == null){
            throw new BusinessException(EnumError.PARAMETER_INVALIDATION_ERROR, "doesn't exist this model");
        }

        UserModel userModel = userService.getUserById(userId);
        if(userModel == null){
            throw new BusinessException(EnumError.PARAMETER_INVALIDATION_ERROR, "doesn't exist this user");
        }

        if(amount <= 0 || amount > 99){
            throw new BusinessException(EnumError.PARAMETER_INVALIDATION_ERROR, "amount not in range");

        }

        boolean result = itemService.decreaseStock(itemId, amount);
        if(!result){
            throw new BusinessException(EnumError.STOCK_NOT_ENOUGH);
        }

        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setItemId(itemId);
        orderModel.setAmount(amount);
        orderModel.setItemPrice(itemModel.getPrice());
        orderModel.setOrderPrice(itemModel.getPrice().multiply(new BigDecimal(amount)));

        OrderDO orderDO = convertOrderModelToDO(orderModel);
        orderDOMapper.insertSelective(orderDO);

        return null;
    }


    public OrderDO convertOrderModelToDO(OrderModel orderModel){
        if(orderModel == null){
            return null;
        }

        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderModel, orderDO);
        return orderDO;

    }
}
