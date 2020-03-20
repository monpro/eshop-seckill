package com.seckill.service.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

public class ItemModel {

    private Integer id;

    @NotBlank(message = "title cannot be null")
    private String title;

    @NotBlank(message = "price cannot be null")
    @Min(value = 0, message = "price should be larger than 0")
    private BigDecimal price;

    @NotBlank(message = "stock cannot be null")
    private Integer stock;

    @NotBlank(message = "description cannot be null")
    private String description;

    private Integer sales;

    @NotBlank(message = "imgUrl cannot be null")
    private String imgUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
