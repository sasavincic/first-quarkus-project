package org.acme.service;

import org.acme.entity.User;
import org.acme.entity.UserLike;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@ApplicationScoped
public class LikeService {

    @Inject
    UserService userService;

    @Inject
    EntityManager em;

    public void like(SecurityContext securityContext, Long likeRecipientId) {
        User likeGiver = User.find("email", securityContext.getUserPrincipal().getName()).firstResult();
        User likeRecipient = userService.findById(likeRecipientId);

        if (likeGiver == likeRecipient) {
            throw new BadRequestException("You cannot like yourself!");
        } else {
            List validLikeList = em.createNamedQuery("validLike")
                    .setParameter("likeG", likeGiver)
                    .setParameter("likeR", likeRecipient)
                    .getResultList();
            if (validLikeList.isEmpty()) {
                UserLike userLike = new UserLike();
                userLike.likeGiver = likeGiver;
                userLike.likeRecipient = likeRecipient;
                userLike.persist();
            } else {
                throw new BadRequestException("You have already liked this user!");
            }
        }
    }

    public void unlike(SecurityContext securityContext, Long likeRecipientId) {
        User likeGiver = User.find("email", securityContext.getUserPrincipal().getName()).firstResult();
        User likeRecipient = userService.findById(likeRecipientId);

        List validLikeList = em.createNamedQuery("validLike")
                .setParameter("likeG", likeGiver)
                .setParameter("likeR", likeRecipient)
                .getResultList();

        if (!validLikeList.isEmpty()){
            UserLike userLike = (UserLike) validLikeList.get(0);
            userLike.delete();
        } else {
            throw new BadRequestException("You haven't liked this user yet!");
        }
    }
}
