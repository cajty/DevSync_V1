package com.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "ticket_tags",
            joinColumns = @JoinColumn(name = "ticket_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "deadline", nullable = false)
    private LocalDate deadline;

    @Column(name = "can_replace_ticket", nullable = false)
    private boolean canReplaceTicket = true;

    @Column(name = "replacement_ticket_request_date", nullable = false)
    private LocalDate replacementTicketRequestDate;

    public boolean isCanDeleteTicket() {
        return canDeleteTicket;
    }

    public void setCanDeleteTicket(boolean canDeleteTicket) {
        this.canDeleteTicket = canDeleteTicket;
    }

    @Column(name = "can_delete_ticket", nullable = false)
    private boolean canDeleteTicket = true;

    @Column(name = "status", nullable = false)
    private TicketStatus status;

    // No-argument constructor
    public Ticket() {
    }

    public Ticket(String title, String description, LocalDate deadline, TicketStatus status) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.deadline = deadline;
    }

    public Long getId() {
        return id;
    }

    public Set<Tag> getTags() {
        return tags;
    }
    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public LocalDate getReplacementTicketRequestDate() {
        return replacementTicketRequestDate;
    }

    public void setReplacementTicketRequestDate(LocalDate replacementTicketRequestDate) {
        this.replacementTicketRequestDate = replacementTicketRequestDate;
    }

    public void setDeadline(LocalDate date_end) {
        this.deadline = date_end;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean getCanReplaceTicket() {
        return canReplaceTicket;
    }

    public void setCanReplaceTicket(boolean canReplaceTicket) {
        this.canReplaceTicket = canReplaceTicket;
    }

    public boolean getCanDeleteTicket() {
        return canDeleteTicket;
    }


    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", title='" + title + '\n' +
                ", description='" + description + '\n' +
                ", status='" + status + '\n' +
                "canReplaceTicket=" + canReplaceTicket +
                "canDeleteTicket=" + canDeleteTicket +
                ", user=" + user +
                ", deadline=" + deadline +
                ", tags=" + tags +
                '}';
    }
}