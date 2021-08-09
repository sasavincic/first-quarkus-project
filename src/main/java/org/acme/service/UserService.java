package org.acme.service;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.acme.entity.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.SecurityContext;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserService implements PanacheRepository<User> {

    @Inject
    EntityManager em;

    public void signup(User user) {
        User tmp = find("email", user.email).firstResult();
        if (tmp == null) {
            user.role = "user";
            user.persist();
        } else {
            throw new BadRequestException("User already exists");
        }
    }

    public User findById(Long id){
        System.out.println(id);
        Optional<User> optional = User.findByIdOptional(id);
        return optional.orElseThrow(() -> new BadRequestException("User doesn't exist"));
    }
    public User getMe(SecurityContext securityContext) {
        User me = User.find("email", securityContext.getUserPrincipal().getName()).firstResult();
        return me;
    }

    public void updatePassword(SecurityContext securityContext, String newPassword){
        User me = User.find("email", securityContext.getUserPrincipal().getName()).firstResult();
        System.out.println(me.toString());
        User.update("password = ?1 where id = ?2" , newPassword, me.id);
    }

    public Object getMostLiked() {

        Query countByRecipient = em.createNamedQuery("countByRecipient");

        List countByRecipientList = countByRecipient.getResultList();
//        System.out.println(Arrays.deepToString(countByRecipientList.toArray()));

        Object mostLiked = countByRecipientList.get(0);

        return mostLiked;
    }
}
