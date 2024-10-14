package com.service;

import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

@Singleton
@Startup
public class SchedulerService {

    private UserService userService;
    private TicketService ticketService;

    public SchedulerService() {
        this.userService = new UserService();
        this.ticketService = new TicketService();
    }

    @Schedule(second = "0", minute = "0", hour = "0", persistent = false)
    public void scheduleResetDailyReplaceTokens() {
        userService.resetDailyReplaceTokens();
    }
    @Schedule(second = "0", minute = "0", hour = "0", dayOfMonth = "1", persistent = false)
    public void scheduleResetMonthlyDeleteTokens() {
        userService.resetMonthlyDeleteTokens();
    }

//   @Schedule(second = "*/5", minute = "*", hour = "*", persistent = false)
//    public void scheduleCloseOverdueTickets() {
//        ticketService.closeOverdueTickets();
//    }

}