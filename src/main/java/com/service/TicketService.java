package com.service;

import com.dao.TicketDAO;
import com.entities.Tag;
import com.entities.Ticket;

import javax.swing.text.html.HTML;
import java.util.List;
import java.util.Set;

public class TicketService {

    private TicketDAO ticketDAO;
    private TagService tagService;



    public TicketService() {
        this.ticketDAO = new TicketDAO();
        this.tagService = new TagService();
    }

    public List<Ticket> getAllTickets() {


        return ticketDAO.getAllTickets();
    }
    public void addTicket(Ticket ticket,List<Integer> tagIds) {
        for(Integer tagId:tagIds){
            Tag tag = tagService.getTag(tagId);
            if(tag!=null){
                ticket.addTag(tag);
            }

        }
        ticketDAO.addTicket(ticket);
    }

    public void updateTicket(Ticket ticket) {
        ticketDAO.updateTicket(ticket);
    }

    public void deleteTicket(Long ticketId) {
        ticketDAO.deleteTicket(ticketId);
    }


//  public boolean createTicket(Ticket ticket) throws PastDateException {
//    // Implementation here
//    return false;
//}
//
//public boolean completeTicket(Long ticketId) throws DeadlinePassedException {
//    // Implementation here
//    return false;
//}
//
//public boolean replaceTicket(Long ticketId) throws InsufficientTokensException {
//    // Implementation here
//    return false;
//}
//
//public boolean deleteTicket(Long ticketId) throws InsufficientTokensException {
//    // Implementation here
//    return false;
//}

public boolean addTagToTicket(Long ticketId, String tagName) {
    // Implementation here
    return false;
}

public Set<HTML.Tag> getTicketTags(Long ticketId) {
    // Implementation here
    return null;
}


}
