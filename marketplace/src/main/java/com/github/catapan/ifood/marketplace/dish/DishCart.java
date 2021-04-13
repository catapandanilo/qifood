package com.github.catapan.ifood.marketplace.dish;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.Tuple;
import java.util.ArrayList;
import java.util.List;

public class DishCart {

    public String client;

    public Long dish;

    public static Uni<Long> save(PgPool pgPool, String client, Long dish) {
        return pgPool.preparedQuery("INSERT INTO dish_client (client, dish) VALUES ($1, $2) RETURNING (client)").execute(
            Tuple.of(client, dish))

            .map(pgRowSet -> pgRowSet.iterator().next().getLong("client"));
    }

    public static Uni<List<DishCart>> findCart(PgPool pgPool, String client) {
        return pgPool.preparedQuery("select * from dish_client where client = $1 ").execute(Tuple.of(client))
            .map(pgRowSet -> {
                List<DishCart> list = new ArrayList<>(pgRowSet.size());
                for (Row row : pgRowSet) {
                    list.add(toDishCart(row));
                }
                return list;
            });
    }

    private static DishCart toDishCart(Row row) {
        DishCart dishCart = new DishCart();
        dishCart.client = row.getString("client");
        dishCart.dish = row.getLong("dish");
        return dishCart;
    }

    public static Uni<Boolean> delete(PgPool pgPool, String client) {
        return pgPool.preparedQuery("DELETE FROM dish_client WHERE client = $1").execute(Tuple.of(client))
            .map(pgRowSet -> pgRowSet.rowCount() == 1);

    }

}
