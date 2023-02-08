package com.mjozwk.featureflag.service.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_feature")
@Getter
@Setter
public class UserFeature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_feature_user_id"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "feature_id", foreignKey = @ForeignKey(name = "fk_user_feature_feature_id"))
    private FeatureFlag feature;

    @Column(name = "is_enabled_for_user")
    private boolean isEnabledForUser;
}
