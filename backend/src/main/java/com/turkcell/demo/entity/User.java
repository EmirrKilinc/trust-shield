package com.turkcell.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "local_users")
public class User {
    //user_id, name, city, segment
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "city")
    private String city;

    @Column(name = "segment")
    private String segment;

    public User() {
    }

    public User(String name, String city, String segment) {
        this.name = name;
        this.city = city;
        this.segment = segment;
    }

    public String getUserId() {
        return "U" + id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }
}
