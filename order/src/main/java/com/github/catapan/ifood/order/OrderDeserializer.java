package com.github.catapan.ifood.order;

import com.github.catapan.ifood.order.dish.DishOrderRealizedDTO;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class OrderDeserializer extends ObjectMapperDeserializer<DishOrderRealizedDTO> {

    public OrderDeserializer() {
        super(DishOrderRealizedDTO.class);
    }

}
