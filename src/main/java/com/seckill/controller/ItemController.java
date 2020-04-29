package com.seckill.controller;

import com.seckill.controller.viewobject.ItemVO;
import com.seckill.error.BusinessException;
import com.seckill.response.CommonResponseType;
import com.seckill.service.ItemService;
import com.seckill.service.model.ItemModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Controller("/item")
@RequestMapping("/item")
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class ItemController extends BaseController{

    @Autowired
    private ItemService itemService;

    public CommonResponseType createItem(
            @RequestParam(name="title")String title,
            @RequestParam(name="description")String description,
            @RequestParam(name="price") BigDecimal price,
            @RequestParam(name="stock")Integer stock,
            @RequestParam(name="imgUrl")String imgUrl) throws BusinessException {
        ItemModel itemModel = new ItemModel();
        itemModel.setTitle(title);
        itemModel.setDescription(description);
        itemModel.setPrice(price);
        itemModel.setStock(stock);
        itemModel.setImgUrl(imgUrl);

        ItemModel createdItemModel = itemService.createItem(itemModel);

        ItemVO itemVO = convertModelToVo(createdItemModel);

        return CommonResponseType.create(itemVO);

    }

    @RequestMapping(value="get", method = {RequestMethod.GET}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonResponseType getItem(@RequestParam(name="id")Integer id){
        ItemModel itemModel = itemService.getItemById(id);

        ItemVO itemVO = convertModelToVo(itemModel);

        return CommonResponseType.create(itemVO);
    }

    @RequestMapping(value="list", method = {RequestMethod.GET})
    @ResponseBody
    public CommonResponseType listItem(@RequestParam(name="id")Integer id){
        List<ItemModel> itemModelList = itemService.listItem();
        List<ItemVO> itemVOList = itemModelList.stream().map(itemModel -> {
            ItemVO itemVO = this.convertModelToVo(itemModel);
            return itemVO;
        }).collect(Collectors.toList());

        return CommonResponseType.create(itemVOList);

    }

    private ItemVO convertModelToVo(ItemModel itemModel){
        if(itemModel == null)
            return null;
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemModel, itemVO);

        return itemVO;
    }
}
