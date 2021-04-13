package com.github.catapan.ifood.order.dish;

import java.math.BigDecimal;

public class DishOrderDTO {

    public String name;

    public String description;

    public BigDecimal price;

    public DishOrderDTO() {
        super();
    }

    public DishOrderDTO(String name, String description, BigDecimal price) {
        super();
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
