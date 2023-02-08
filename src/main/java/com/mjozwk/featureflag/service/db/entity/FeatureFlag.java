package com.mjozwk.featureflag.service.db.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "feature_flag")
@Getter
@Setter
public class FeatureFlag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "featureName", unique = true)
    private String featureName;

    @Column(name = "isGlobalEnabled")
    private boolean isGlobalEnabled;

    @OneToMany(mappedBy = "feature")
    private List<UserFeature> userFeatures;
}
