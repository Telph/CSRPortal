/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service;

import com.service.dao.ServiceEngineersKb;
import com.service.dao.TicketKb;
import com.service.db.Ticket;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

/**
 * REST Web Service
 *
 * @author Janakiraman
 */
@Path("/portal") //This is defined as the annotation for the class
public class MypathResource
{
	/*
	 * This method is used to create the ticket when CR clicks on "Log" button in the homepage.html
	 * All the parameters required are passed through the request parameter
	 */
    @Path("/createticket")
    @POST
    @Produces("text/plain")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String getText(@DefaultValue("") @FormParam("customername") String customername,
        @DefaultValue("") @FormParam("areacode") String areaCode,
        @DefaultValue("") @FormParam("telnum") String telnum,
        @DefaultValue("") @FormParam("emailid") String emailid,           
        @DefaultValue("No") @FormParam("shallcontact") String shallContact,
        @DefaultValue("") @FormParam("meansofcontact") String meansOfContact,
        @DefaultValue("") @FormParam("complainttype") String areaOfComplaint,
        @DefaultValue("") @FormParam("comment") String comment,
        @DefaultValue("") @FormParam("description") String description,
        @DefaultValue("") @FormParam("createdby") String createdBy,
        @DefaultValue("") @FormParam("assignee") String assignee,
        @DefaultValue("New") @FormParam("status") String status)
    {
        //Already a check is made in the UI to alert the user. 
    	//Another round of check is made to check if empty field is being sent from the server side.
    	//Range or patter validation happens only at the UI part
        if(areaCode.length() > 0 && telnum.length() > 0 && description.length() >0 && customername.length() > 0 && meansOfContact.length() > 0
                && emailid.length() > 0 && createdBy.length() > 0 && assignee.length() > 0 && status.length() > 0 && areaOfComplaint.length()>0)
        {
           
           if((status.equals("Closed")||status.equals("Open")))
           {
            if((assignee).equals("None"))
            {
              return "Please select an assignee!!!";
            }
            else if(comment == null || comment.length() == 0)
            {
              return "Please provide comments!!!";
            }            
           }
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            Ticket ticket = new Ticket();
            ticket.setCustomerName(customername);
            ticket.setContactTelAreaCode(areaCode);
            ticket.setContactNumber(telnum);
            ticket.setShallContact(shallContact);
            ticket.setContactSource(meansOfContact);
            ticket.setEmailId(emailid);
            ticket.setComment(comment);
            ticket.setDescription(description);
            ticket.setAssignedBy(createdBy);
            ticket.setAssignee(assignee);
            ticket.setStatus(status);   
            ticket.setAreaOfComplaint(areaOfComplaint);
            ticket.setAssignedDate(dateFormat.format(date));
            ticket.setLastUpdated("-");
            ticket.setClosedOn("-");
            TicketKb.getInstance().addTicket(ticket);
            return "Ticket No " + ticket.getTicketId() + " has been added successfully";
        }
        return "Mandatory fields missing!";
    }
    
    /**
     *This is to fetch the details of the tickets that has been created
     *The table content is framed and sent the requested page.
     * If open/New --> Ticket should be enabled for editing
     * If Closed --> ticket will not be editable
     * @return
     */
    @Path("/viewtickets")
    @GET
    @Produces("text/plain")
    public String getHtml()
    {
        StringBuilder htmlCode = new StringBuilder();
        HashMap<String, Ticket> tickets = TicketKb.getInstance().getTickets();
        htmlCode.append("<div class=\"table-responsive col-lg-12\" ><table border=\"1\" id=\"ticket_details_table\" class=\"table table-striped\" style=\"font-size: 11px;\">");
        htmlCode.append("<td>Ticket Id</td>");
        htmlCode.append("<td>Area Of Complaint</td>");
        htmlCode.append("<td>Description</td>");
        htmlCode.append("<td>Customer Name</td>");
        htmlCode.append("<td>Created By</td>");    
        htmlCode.append("<td>Assigned To</td>");    
        htmlCode.append("<td>Assigned On</td>");    
        htmlCode.append("<td>Last Updated</td>");         
        htmlCode.append("<td>Status</td>"); 
        htmlCode.append("<td>Comment</td>");
        htmlCode.append("<td>Closed on</td>");
        htmlCode.append("<td>Shall contact</td>");
        htmlCode.append("<td>Means of contact</td>"); 
        htmlCode.append("<td>Area Code</td>");
        htmlCode.append("<td>Contact</td>");
        htmlCode.append("<td>Email Id</td>");
       
        
        Iterator<String> keys = tickets.keySet().iterator();
        while(keys.hasNext())
        {
                String key = keys.next().toString();
                htmlCode.append("<tr>");
                if(!tickets.get(key).getStatus().equals("Closed"))
                {
                    htmlCode.append("<td><a style=\"cursor:pointer\" onClick=\"event.preventDefault();loadEditForm('"+tickets.get(key).getTicketId()+"');return false;\">" + tickets.get(key).getTicketId() + "</a></td>");
                }
                else
                {
                    htmlCode.append("<td><a href=\"#\" style=\"cursor:not-allowed\">" + tickets.get(key).getTicketId() + "</a></td>");
                }
                htmlCode.append("<td>" + tickets.get(key).getAreaOfComplaint() + "</td>");
                htmlCode.append("<td>" + tickets.get(key).getDescription() + "</td>");
                htmlCode.append("<td>" + tickets.get(key).getCustomerName() + "</td>");
                
                htmlCode.append("<td>" + tickets.get(key).getCreatorName() + "</td>");    
                htmlCode.append("<td>" + tickets.get(key).getAssignee() + "</td>");    
                htmlCode.append("<td>" + tickets.get(key).getAssignedDate() + "</td>");    
                htmlCode.append("<td>" + tickets.get(key).getLastUpdated() + "</td>");    
                htmlCode.append("<td>" + tickets.get(key).getStatus() + "</td>"); 
                htmlCode.append("<td>" + tickets.get(key).getComment() + "</td>");
                htmlCode.append("<td>" + tickets.get(key).getClosedOn() + "</td>");  
                
                htmlCode.append("<td>" + tickets.get(key).getShallContact() + "</td>");                
                htmlCode.append("<td>" + tickets.get(key).getContactSource() + "</td>");                
                htmlCode.append("<td>" + tickets.get(key).getContactTelAreaCode() + "</td>");
                htmlCode.append("<td>" + tickets.get(key).getContactNumber() + "</td>");
                htmlCode.append("<td>" + tickets.get(key).getEmailId() + "</td>");
                htmlCode.append("</tr>");
        }
        htmlCode.append("</table></div><div><a href=\"#\" "
        		+ "onclick=\"var elmnt = document.body;elmnt.scrollTop = 0;\">Top</a>"
        		+ "&nbsp;"
        		+ "<a href=\"#\" onclick=\"saveAsExcel();\">Save</a></div>");
        return htmlCode.toString();
    }
    
    /**
     *Returns the details of the ticket selected by the CR to edit
     *A string with details is sent as a key value pair that will be parsed and rendered in the HTML page
     * @return  string (key value format separated by &)
     */
    @Path("/editticket")
    @GET
    @Produces("text/plain")
    public String getEditTicket(@DefaultValue("") @QueryParam("tid") String ticketId)
    {
        Ticket ticket = TicketKb.getInstance().getTickets().get(ticketId);
        StringBuilder returnString = new StringBuilder();
        returnString.append("ticketid="+ticket.getTicketId());
        returnString.append("&customername="+ticket.getCustomerName());
        returnString.append("&areacode="+ticket.getContactTelAreaCode());
        returnString.append("&telnum="+ticket.getContactNumber());
        returnString.append("&emailid="+ticket.getEmailId());
        returnString.append("&comment="+ticket.getComment());
        returnString.append("&shallcontact="+ticket.getShallContact());
        returnString.append("&meansofcontact="+ticket.getContactSource());
        returnString.append("&complainttype="+ ticket.getAreaOfComplaint()+ "&createdby=" + ticket.getCreatorName());
        returnString.append("&assignee=" + ticket.getAssignee());
        returnString.append("&status="+ticket.getStatus());        
        returnString.append("&description="+ticket.getDescription());
        return returnString.toString();
    }
    
    /**
     *Received the value of the edited ticket and updates the corresponding Ticket
     * Status of update is returned. Successful or not.
     * @return  string (key value format separated by &)
     */
    @Path("/updateticket")
    @PUT
    @Produces("text/plain")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String updateTicket(@DefaultValue("") @FormParam("hidticketid") String ticketId,
        @DefaultValue("") @FormParam("customername") String customername,
        @DefaultValue("") @FormParam("areacode") String areaCode,
        @DefaultValue("") @FormParam("telnum") String telnum,
        @DefaultValue("") @FormParam("emailid") String emailid,           
        @DefaultValue("No") @FormParam("shallcontact") String shallContact,
        @DefaultValue("") @FormParam("meansofcontact") String meansOfContact,
        @DefaultValue("") @FormParam("complainttype") String areaOfComplaint,
        @DefaultValue("") @FormParam("comment") String comment,
        @DefaultValue("") @FormParam("description") String description,
        @DefaultValue("") @FormParam("createdby") String createdBy,
        @DefaultValue("") @FormParam("assignee") String assignee,
        @DefaultValue("New") @FormParam("status") String status)
    {
        
    	//Check for any null fields is done as another round of check before updating in the backend
        if(areaCode.length() > 0 && telnum.length() > 0 && description.length() >0 && customername.length() > 0 && meansOfContact.length() > 0
                && emailid.length() > 0 && createdBy.length() > 0 && assignee.length() > 0 && status.length() > 0)
        {
           
            if((status.equals("Closed")||status.equals("Open")))
            {
             if((assignee).equals("None"))
             {
               return "Please select an assignee!!!";
             }
             else if(comment == null || comment.length() == 0)
             {
               return "Please provide comments!!!";
             }            
            }
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            Ticket ticket = TicketKb.getInstance().getTickets().get(ticketId);
            if(ticket != null)
            {
                ticket.setCustomerName(customername);
                ticket.setContactTelAreaCode(areaCode);
                ticket.setContactNumber(telnum);
                ticket.setShallContact(shallContact);
                ticket.setContactSource(meansOfContact);
                ticket.setEmailId(emailid);
                ticket.setComment(comment);
                ticket.setAssignedBy(createdBy);
                ticket.setAssignee(assignee);
                ticket.setStatus(status);                        
                ticket.setLastUpdated(dateFormat.format(date));
                ticket.setAreaOfComplaint(areaOfComplaint);
                if(status.equals("Closed"))
                {
                    ticket.setClosedOn(dateFormat.format(date));
                }
                TicketKb.getInstance().addTicket(ticket);
                return "Ticket No " + ticket.getTicketId() + " has been updated successfully";
            }
            else
            {
                return "No such ticket found!!!";
            }
        }
        return "Fill in the mandatory fields!!!";
    }
    
    /**
     *Based on the Area of Complaint, set of engineers/staffs who are available in that area will have to be listed out.
     *Takes Area of Complaint as input and sends the list of Engineers/Staffs in that area in the HTML format that will be set as required 
     *by the requested HTML page
     */
    @Path("/getAssigneeList")
    @GET
    @Produces("text/plain")
    public String getAssigneeList(@DefaultValue("") @QueryParam("type") String areatype)
    {   
        String sendString = "";
        List<String> convertList = null;
        if(areatype.equals("Hardware"))
        {
            convertList = ServiceEngineersKb.getHardwareEngineers();  
        }
        else if(areatype.equals("Software"))
        {
            convertList = ServiceEngineersKb.getSoftwareEngineers();  
        }
        else if(areatype.equals("Warranty"))
        {
            convertList = ServiceEngineersKb.getWarrantyClaimStaffs();
        }
        else if(areatype.equals("New Products"))
        {
            convertList = ServiceEngineersKb.getNewProductEnquiryStaffs();
        }
        if(convertList != null)
        {
            for (int optionIterator = 0; optionIterator < convertList.size(); optionIterator++) {
                    sendString += "<option value=\""+convertList.get(optionIterator)+"\">" + convertList.get(optionIterator) + "</option>";
            }
        }
        return sendString;
    }
    
    /**
     *The access code entered at the UI is fetched through request and is validated against the actual code and the result is set back (valid/invalid)
     */
    @Path("/validate")
    @GET
    @Produces("text/plain")
    public String validateUser(@DefaultValue("") @QueryParam("accesscode") String accesscode)
    {   
        if(accesscode.equals("5Cats@OwlL8"))
        {
        	return "valid";
        }
        return "invalid";
    }
        
}
