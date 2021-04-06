package com.github.catapan.ifood.register;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "restaurant")
public class Restaurant extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String owner;

    public String cnpj;

    public String name;

    @ManyToOne
    public Localization localization;

    @CreationTimestamp
    public Date dateCreation;

    @CreationTimestamp
    public Date dataUpdate;
}
