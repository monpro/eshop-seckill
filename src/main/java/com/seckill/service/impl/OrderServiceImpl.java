package com.seckill.service.impl;

import com.seckill.dao.OrderDOMapper;
import com.seckill.dao.SequenceDOMapper;
import com.seckill.dataobject.OrderDO;
import com.seckill.dataobject.SequenceDO;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private SequenceDOMapper sequenceDOMapper;

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderDOMapper orderDOMapper;

    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer itemId, Integer promoId, Integer amount) throws BusinessException {
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

        if(promoId != null){
            if(promoId.intValue() != itemModel.getPromoModel().getId()){
                throw new BusinessException(EnumError.PARAMETER_INVALIDATION_ERROR, "promo is not existed with this item");
            }else if(itemModel.getPromoModel().getStatus().intValue() != 2){
                throw new BusinessException(EnumError.PARAMETER_INVALIDATION_ERROR, "promo is not start");

            }
        }



        boolean result = itemService.decreaseStock(itemId, amount);
        if(!result){
            throw new BusinessException(EnumError.STOCK_NOT_ENOUGH);
        }

        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setItemId(itemId);
        orderModel.setAmount(amount);
        if(promoId != null){
            orderModel.setItemPrice(itemModel.getPromoModel().getPromoPrice());
        }else{
            orderModel.setItemPrice(itemModel.getPrice());
        }
        orderModel.setPromoId(promoId);
        orderModel.setOrderPrice(orderModel.getItemPrice().multiply(new BigDecimal(amount)));

        orderModel.setId(generateOrderNo());
        OrderDO orderDO = convertOrderModelToDO(orderModel);
        orderDOMapper.insertSelective(orderDO);

        itemService.increaseSales(itemId, amount);
        return orderModel;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    String generateOrderNo(){
        StringBuilder stringBuilder = new StringBuilder();
        //get data string
        LocalDateTime now = LocalDateTime.now();
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-", "");
        stringBuilder.append(nowDate);

        //get sequence string
        int sequence = 0;

        SequenceDO sequenceDO = sequenceDOMapper.getSequenceByName("order_info");
        sequence = sequenceDO.getCurrentValue();
        sequenceDO.setCurrentValue(sequenceDO.getCurrentValue() + sequenceDO.getStep());
        sequenceDOMapper.updateByPrimaryKey(sequenceDO);

        String sequenceStr  = String.valueOf(sequence);
        for(int i = 0; i < 6 - sequenceStr.length(); i++){
            stringBuilder.append(0);
        }
        stringBuilder.append(sequenceStr);

        //hardcode partition string
        stringBuilder.append("11");
        return stringBuilder.toString();
    }



    public OrderDO convertOrderModelToDO(OrderModel orderModel){
        if(orderModel == null){
            return null;
        }

        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderModel, orderDO);
        orderDO.setItemPrice(orderModel.getItemPrice().doubleValue());
        orderDO.setOrderPrice(orderModel.getOrderPrice().doubleValue());

        return orderDO;

    }

}
