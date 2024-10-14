package com.service;

import com.repository.TicketRepository;
import com.entities.Tag;
import com.entities.Ticket;
import com.repository.UserRepository;

import javax.swing.text.html.HTML;
import java.util.List;
import java.util.Set;

public class TicketService {

    private TicketRepository ticketRepository;
    private TagService tagService;
    private UserRepository userRepository;



    public TicketService() {
        this.ticketRepository = new TicketRepository();
        this.tagService = new TagService();
        this.userRepository = new UserRepository();
    }

    public List<Ticket> getAllTickets() {


        return ticketRepository.getAllTickets();
    }
    public void addTicket(Ticket ticket,List<Integer> tagIds) {
        for(Integer tagId:tagIds){
            Tag tag = tagService.getTag(tagId);
            if(tag!=null){
                ticket.addTag(tag);
            }

        }
        ticketRepository.addTicket(ticket);
    }

    public void updateTicket(Ticket ticket) {
        ticketRepository.updateTicket(ticket);
    }

    public void deleteTicket(Long ticketId) {
        ticketRepository.deleteTicket(ticketId);
    }
    
    public void closeOverdueTickets() {
        ticketRepository.closeOverdueTickets();
    }


    public Ticket canReplace(Long userId, Long ticketId) {
        int userReplaceTokens = userRepository.getUserReplaceTokens(userId);
        Ticket ticket = ticketRepository.getTicketById(ticketId);
        System.out.println("\n\n\n\n\n\n\nUser replace tokens: " + ticket + "\n\n\n\n\n\n\n");
        if (userReplaceTokens > 0 && ticket.getCanReplaceTicket()) {
            return ticket;
        }
        return null;
    }

    public boolean canDelete(Long userId) {
        int userDeleteTokens = userRepository.getUserDeleteTokens(userId);
        return userDeleteTokens > 0;
    }

    public boolean replaceTicketWithToken(Long userId, Long ticketId) {
        System.out.println("\n\n\n\n\n\n\n\n\n\nUser replace tokens: " + userId + "\n\n\n\n\n\n\n");
        if (canReplace(userId, ticketId) != null) {
            System.out.println("\n\n\n\n\n\n\n\n\n\nUser replace tokens: " + userId + "\n\n\n\n\n\n\n");
            ticketRepository.useReplaceToken(ticketId);
            return true;
        }
        return false;
    }

    public boolean deleteTicketWithToken(Long userId, Long ticketId) {
        if(ticketRepository.isUserTicket(userId, ticketId)){
            System.out.println("\n\n\n\n\n" + ticketRepository.isUserTicket(userId, ticketId)+ "\n\n\n\n\n");
            ticketRepository.deleteTicket(ticketId);
            return true;
        }
        if (canDelete(userId)) {
            ticketRepository.useDeleteToken(ticketId);
            return true;
        }
        return false;
    }




}
