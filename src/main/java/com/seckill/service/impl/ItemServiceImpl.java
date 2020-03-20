package com.seckill.service.impl;

import com.seckill.error.BusinessException;
import com.seckill.error.EnumError;
import com.seckill.service.ItemService;
import com.seckill.service.model.ItemModel;
import com.seckill.validator.ValidationResult;
import com.seckill.validator.ValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ValidatorImpl validator;

    @Override
    @Transactional
    public ItemModel createItem(ItemModel itemModel) throws BusinessException {
        // parameters validation
        ValidationResult result = validator.validate(itemModel);
        if(result.isHasErrors()){
            throw new BusinessException(EnumError.PARAMETER_INVALIDATION_ERROR, result.getErrorMsg());
        }
        //itemModel -> dataObject

        //write dataObject into database

        //return created Item

        return null;
    }

    @Override
    public List<ItemModel> listItem() {
        return null;
    }

    @Override
    public ItemModel getItemById(Integer id) {
        return null;
    }
}
