package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@NamedQuery(
        name="validLike",
        query="SELECT l FROM UserLike l WHERE l.likeGiver = :likeG AND l.likeRecipient = :likeR"
)

@NamedQuery(
        name="countByRecipient",
        query="SELECT COUNT(likeRecipient) AS cnt, likeRecipient FROM UserLike GROUP BY likeRecipient ORDER BY cnt DESC"
)

@Entity
public class UserLike extends PanacheEntity {

    @CreationTimestamp
    public LocalDateTime created;

    @ManyToOne
    public User likeRecipient;

    @ManyToOne
    public User likeGiver;
}
