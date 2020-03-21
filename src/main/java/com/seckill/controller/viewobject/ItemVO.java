package com.seckill.controller.viewobject;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

public class ItemVO {
    private Integer id;

    private String title;

    private BigDecimal price;

    private Integer stock;

    private String description;

    private Integer sales;

    private String imgUrl;
}
