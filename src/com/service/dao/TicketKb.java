/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.service.db.Ticket;

/**
 *This class holds the objects of the Tickets that has been created by the CRs
 * @author Janakiraman
 */
public class TicketKb {
    private static HashMap<String, Ticket> tickets = new HashMap<String, Ticket>();
    private static TicketKb INSTANCE = new TicketKb();
    
    public static TicketKb getInstance()
    {
        return INSTANCE;
    }
    public HashMap<String, Ticket> getTickets()
    {
        return tickets;
    }
    public void addTicket(Ticket ticket)
    {
        tickets.put(ticket.getTicketId(), ticket);
    }
    public Ticket getRequestedTicket(String ticketId)
    {
        return tickets.get(ticketId);
    }
}
