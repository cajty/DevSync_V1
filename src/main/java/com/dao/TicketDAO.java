package com.dao;

import com.entities.Ticket;
import com.config.DbConnection;
import jakarta.persistence.EntityManager;

import java.util.List;

public class TicketDAO {
    private EntityManager entityManager;

    public TicketDAO() {
        this.entityManager = DbConnection.getInstance().getEntityManager();
    }

 public List<Ticket> getAllTickets() {
    try {
        // Enable logging of when the method is called
        System.out.println("Attempting to retrieve all tickets with tags...");

        // Use JOIN FETCH to eagerly load tags
        List<Ticket> tickets = entityManager.createQuery(
                "SELECT t FROM Ticket t JOIN FETCH t.tags", Ticket.class).getResultList();

        for (Ticket ticket : tickets) {
        System.out.println("Ticket retrieved:\n\n\n\n\n\n\n\n " + ticket.getTags() + "\n\n\n\n\n\n\n\n");
        }


        return tickets;
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}


    public void addTicket(Ticket ticket) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(ticket);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }


    public void updateTicket(Ticket ticket) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(ticket);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    public void deleteTicket(Long ticketId) {
        try {
            entityManager.getTransaction().begin();
            Ticket ticket = entityManager.find(Ticket.class, ticketId);
            entityManager.remove(ticket);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }



}



