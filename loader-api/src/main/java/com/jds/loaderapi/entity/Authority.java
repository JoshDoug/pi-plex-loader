package com.jds.loaderapi.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Authority {

    @Id
    private String id;

    public Authority() {
    }

    public Authority(String id) {
        this.id = id;
    }
}
