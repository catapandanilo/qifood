package com.github.catapan.ifood.marketplace.dish;

import io.vertx.mutiny.sqlclient.Row;
import java.math.BigDecimal;

public class DishDTO {

    public Long id;

    public String name;

    public String description;

    public BigDecimal price;

    public static DishDTO from(Row row) {
        DishDTO dto = new DishDTO();
        dto.id = row.getLong("id");
        dto.name = row.getString("name");
        dto.description = row.getString("description");
        dto.price = row.getBigDecimal("price");
        return dto;
    }
}
