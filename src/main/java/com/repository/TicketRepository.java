package com.repository;

import com.entities.Ticket;
import com.entities.TicketStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

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
            List<Ticket> tickets = entityManager.createQuery(
                    "SELECT t FROM Ticket t JOIN FETCH t.tags", Ticket.class).getResultList();
            for (Ticket ticket : tickets) {
                System.out.println("Ticket retrieved: " + ticket.getTags());
            }
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
            return entityManager.find(Ticket.class, ticketId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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
                            "WHERE t.status != com.entities.TicketStatus.IN_PROGRESS  " +
                            "AND t.deadline < CURRENT_DATE")
                    .setParameter("closedStatus", com.entities.TicketStatus.CLOSED)
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
}