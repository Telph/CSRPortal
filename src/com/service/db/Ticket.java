/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.db;

import java.util.Date;

/**
 * This class is the bluprint for the Tickets (properties and actions) that is created as part of CSR Portal
 * @author Janakiraman
 */
public class Ticket {
    private String ticketId;
    private String customerName;
    private String contactTelAreaCode;
    private String contactTelNumber;
    private String emailId;
    private String shallContact;
    private String contactSource;
    private String areaOfComplaint;
    private String description;
    private String comment;
    private String assignedBy;
    private String assignedTo;
    private String assignedDate;
    private String status;
    private String lastUpdated;
    private String closedOn;
    
    private static int counter = 1000; 
    
    public Ticket()
    {
        this.ticketId = "CSR"+counter++;
    }
    
    public String getLastUpdated()
    {
        return this.lastUpdated;
    }
    
    public void setLastUpdated(String lastUpdated)
    {
        this.lastUpdated = lastUpdated;
    }
    
    public String getClosedOn()
    {
        return this.closedOn;
    }
    
    public void setClosedOn(String closedOn)
    {
        this.closedOn = closedOn;
    }
    
    public String getTicketId()
    {
        return this.ticketId;
    }
    public String getCustomerName()
    {
        return this.customerName;
    }
    public String getContactTelAreaCode()
    {
        return this.contactTelAreaCode;
    }
    public String getContactNumber()
    {
        return this.contactTelNumber;
    }
    public String getEmailId()
    {
        return this.emailId;
    }
    public String getShallContact()
    {
        return this.shallContact;
    }
    public String getContactSource()
    {
        return this.contactSource;
    }
    public String getComment()
    {
        return this.comment;
    }
    public String getDescription()
    {
        return this.description;
    }
    public String getCreatorName()
    {
        return this.assignedBy;
    }
    public String getAssignee()
    {
        return this.assignedTo;
    }
    public String getAssignedDate()
    {
        return this.assignedDate;
    }
    public String getStatus()
    {
        return this.status;
    }
    public void setTicketId(String ticketId)
    {
        this.ticketId = ticketId;
    }
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }
    public void setContactTelAreaCode(String areaString)
    {
        this.contactTelAreaCode = areaString;
    }
    public void setContactNumber(String telNumber)
    {
        this.contactTelNumber = telNumber;
    }
    public void setEmailId(String emailId)
    {
        this.emailId = emailId;
    }
    public void setShallContact(String shallContact)
    {
        this.shallContact = shallContact;
    }
    public void setContactSource(String contactSource)
    {
        this.contactSource = contactSource;
    }
    public void setComment(String comment)
    {
        this.comment = comment;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }
    public void setAssignedBy(String assignedBy)
    {
        this.assignedBy = assignedBy;
    }
    public void setAssignee(String assignee)
    {
        this.assignedTo = assignee;
    }
    public void setAssignedDate(String assignedDate)
    {
        this.assignedDate = assignedDate;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }
    public String getAreaOfComplaint() {
		return areaOfComplaint;
	}

	public void setAreaOfComplaint(String areaOfComplaint) {
		this.areaOfComplaint = areaOfComplaint;
	}    
            
}
