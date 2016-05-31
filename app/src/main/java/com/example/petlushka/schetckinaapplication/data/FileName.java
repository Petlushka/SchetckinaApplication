package com.example.petlushka.schetckinaapplication.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Irina on 20.05.2016.
 */
public class FileName extends RealmObject {

    private String name;

    @PrimaryKey
    private Integer id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
