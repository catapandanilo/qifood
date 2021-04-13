package com.github.catapan.ifood.register.panache;

import com.github.catapan.ifood.register.restaurant.Restaurant;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import io.quarkus.panache.common.Sort.Direction;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class PanacheQueries {

    public void exampleSelects() {
        // -- Class --

        //findAll
        PanacheQuery<Restaurant> findAll = Restaurant.findAll();
        Restaurant.findAll(Sort.by("name").and("id", Direction.Ascending));

        PanacheQuery<Restaurant> page = findAll.page(Page.of(3, 10));

        //find sem sort
        Map<String, Object> params = new HashMap<>();
        params.put("name", ""); //same \/
        Restaurant.find("select r from Restaurant where name = :name", params);

        String name = "";
        Restaurant.find("select r from Restaurant where name = $1", name);

        Restaurant.find("select r from Restaurant where name = :name and id = :id",
            Parameters.with("name", "").and("id", 1L));

        //Atalho para findAll.stream, mas precisa de transacao se nao o cursor pode fechar antes
        Restaurant.stream("select r from Restaurant where name = :name", params);

        Restaurant.stream("select r from Restaurant where name = $1", name);

        Restaurant.stream("select r from Restaurant where name = :name and id = :id",
            Parameters.with("name", "").and("id", 1L));

        //find by id
        Restaurant findById = Restaurant.findById(1L);

        //Persist
        Restaurant.persist(findById, findById);

        //Delete
        Restaurant.delete("delete Restaurant where name = :name", params);

        Restaurant.delete("delete Restaurant where name = $1", name);

        Restaurant.delete("name = :name and id = :id",
            Parameters.with("name", "").and("id", 1L));

        //Update
        Restaurant.update("update Restaurant where name = :name", params);

        Restaurant.update("update Restaurant where name = $1", name);

        //Count
        Restaurant.count();

        //-- Métodos de objeto--
        Restaurant restaurant = new Restaurant();

        restaurant.persist(); //sem flush

        restaurant.isPersistent();

        restaurant.delete(); //delete db instance

    }

}

//BEGIN - RECOMMENDED
@Entity
class MyEntity1 extends PanacheEntity {
    public String name;

    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}

@Entity
class MyEntity2 extends PanacheEntityBase {

    @Id
    @GeneratedValue
    public Long id;

    public String name;

    public MyEntity2() {
    }
}
//END - RECOMMENDED

@Entity
class MyEntity3 {

    @Id
    @GeneratedValue
    public Long id;

    public String name;

    public MyEntity3() {
    }
}

@ApplicationScoped
class MeuRepository implements PanacheRepository<MyEntity3> {

}
