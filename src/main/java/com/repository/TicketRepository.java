package com.repository;

import com.entities.Ticket;
import com.entities.TicketStatus;
import com.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.util.List;

public class TicketRepository {
    private final EntityManagerFactory entityManagerFactory;

    public TicketRepository() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("DevSync");
    }

    public List<Ticket> getAllTickets() {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            System.out.println("Attempting to retrieve all tickets with tags...");

 // Use JOIN FETCH to load the tags eagerly
List<Ticket> tickets = entityManager.createQuery(
                "SELECT t FROM Ticket t LEFT JOIN FETCH t.tags WHERE t.canDeleteTicket = true", Ticket.class)
        .getResultList();

            return tickets;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }


    public void addTicket(Ticket ticket) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(ticket);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void updateTicket(Ticket ticket) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(ticket);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void deleteTicket(Long ticketId) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Ticket ticket = entityManager.find(Ticket.class, ticketId);
            entityManager.remove(ticket);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public Ticket getTicketById(Long ticketId) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            Ticket ticket = entityManager.find(Ticket.class, ticketId);
            if (ticket != null) {
                Hibernate.initialize(ticket.getTags()); // Force initialization of lazy collection
            }
            return ticket;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }


    public boolean isUserTicket(Long userId, Long ticketId) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            Ticket ticket = entityManager.find(Ticket.class, ticketId);
            return ticket.getUser().getId().equals(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

   public void useDeleteToken(Long ticketId) {
    EntityManager entityManager = null;
    try {
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Ticket ticket = entityManager.find(Ticket.class, ticketId);

        if (ticket == null) {
            throw new IllegalArgumentException("\n\n\n\n\n\nTicket not found for id: " + ticketId + "\n\n\n\n\n\n");
        }

        User user = ticket.getUser();
        if (user != null) {
            user.setMonthlyReplaceTokens(0);
            entityManager.merge(user);

        }

        ticket.setCanDeleteTicket(false);



        entityManager.getTransaction().commit();
    } catch (Exception e) {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
        e.printStackTrace();
    } finally {
        if (entityManager != null) {
            entityManager.close();
        }
    }
}

public void useReplaceToken(Long ticketId) {
    EntityManager entityManager = null;
    try {
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Ticket ticket = entityManager.find(Ticket.class, ticketId);
        ticket.setStatus(TicketStatus.REPLACED);
        ticket.setReplacementTicketRequestDate(LocalDate.now());
        ticket.setCanReplaceTicket(false);

        User user = ticket.getUser();
        if (user != null) {
            Integer dailyReplaceTokens = user.getDailyReplaceTokens();
            if (dailyReplaceTokens > 0) {
                user.setDailyReplaceTokens(dailyReplaceTokens - 1);
                entityManager.merge(user);
            }
        }

        entityManager.getTransaction().commit();
    } catch (Exception e) {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
        e.printStackTrace();
    } finally {
        if (entityManager != null) {
            entityManager.close();
        }
    }
}

    public void closeOverdueTickets() {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.createQuery(
                    "UPDATE Ticket t " +
                            "SET t.status = :closedStatus " +
                            "WHERE t.status != com.entities.TicketStatus.IN_PROGRESS " +
                            "AND t.deadline < CURRENT_DATE")
                    .setParameter("closedStatus", TicketStatus.CLOSED)
                    .executeUpdate();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }



//    public void doubleTokenForUserThatdontHaveResponseOfManger
}