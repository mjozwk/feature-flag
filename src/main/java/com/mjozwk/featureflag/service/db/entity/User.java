package com.mjozwk.featureflag.service.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userName", unique = true)
    private String userName;

    @OneToMany(mappedBy = "user")
    private List<UserFeature> userFeatures;
}
