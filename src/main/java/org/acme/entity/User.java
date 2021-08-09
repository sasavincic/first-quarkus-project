package org.acme.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User extends PanacheEntity {
    @Column (unique = true)
    public String email;

    public String password;

    @JsonProperty (access = JsonProperty.Access.READ_ONLY)
    public String role;

    @OneToMany (mappedBy = "likeRecipient")
    @JsonIgnore
    @Transient
    public Set<UserLike> likesRecieved = new HashSet<>();

    @OneToMany (mappedBy = "likeGiver")
    @JsonIgnore
    @Transient
    public Set<UserLike> likesGiven = new HashSet<>();
}
