// This function invokes various other functions to validate all the mandatory field, range validation and pattern validation. 
// This is used by both Creation and Updation methods
function validateMandatoryFields() {
	var nameValid = validateCustomerName();
	var areaCode = validateareacode();
	var telphValid = validateTelephoneNumber();
	var emailValid = checkEmail();
	var othersValid = validateOtherMandatoryfield();
	if (nameValid && areaCode && telphValid && emailValid && othersValid) {
		return true;
	} else {
		return false;
	}
}
function createTicket(e) {
	e.preventDefault();
	if (validateMandatoryFields()) {
		$.ajax({
			url : 'rest/portal/createticket',
			type : 'POST',
			data : $('#ticketing_form').serializeArray(),
			success : function(data) {
				$("#creationstatus_message").html(data);
				if (data.substring(0, 6) == 'Ticket') {
					$('#ticketing_form')[0].reset();
				}
				var elmnt = document.body;
				elmnt.scrollTop = 0;
			}
		});
	}
}

function validateAccessCode(e) {
	e.preventDefault();
	if ($('#accesscode').val().length > 0) {
		$.ajax({
			url : 'rest/portal/validate',
			type : 'GET',
			data : 'accesscode=' + $('#accesscode').val(),
			success : function(data) {
				if (data == 'valid') {
					$('#myModal').modal('hide');
					var elmnt = document.body;
					elmnt.scrollTop = 0;
				}
			}
		});
	}
}

// This function is used to clear all the mandatory fields associated warning
// from the page during form reset
function clearWarnings() {
	$('#desc-alert').html('');
	$('#assignee-alert').html('');
	$('#comment-alert').html('');
	$('#name-alert').html('');
	$('#email-alert').html('');
	$('#tel-alert').html('');
	$('#ticketing_form')[0].reset();
}
// This function is used to validate Description, Assignee and Comment field
// Comment and Assignee is mandatory only when Status of the Ticket is either
// Closed or Open
function validateOtherMandatoryfield() {
	var valid = true;
	if ($("#description").val().length <= 0) {
		valid = false;
		$("#desc-alert").html('Description is mandatory!');
	}
	if ($("#status").val() == "Open" || $("#status").val() == "Closed") {

		if ($("#assignee").val() == "None") {
			$("#assignee-alert").html("Assignee is mandatory!");
			valid = false
		}
		if ($('#comment').val().length <= 0) {
			$("#comment-alert").html("Comment is mandatory!");
			valid = false;
		}

	}
	if (valid == true) {
		$("#desc-alert").html('');
		$("#assignee-alert").html("");
		$("#comment-alert").html("");
		return true;
	}
	return false;
}
// This is used to update the edited ticket
function updateTicket(e) {
	e.preventDefault();
	if (validateMandatoryFields()) {
		$.ajax({
			url : 'rest/portal/updateticket',
			type : 'PUT',
			data : $('#ticketing_form').serializeArray(),
			success : function(data) {

				$("#creationstatus_message").html(data);
				if (data.substring(0, 6) == 'Ticket') {
					$('#ticketid').html("");
					$('#ticketing_form')[0].reset();
				}
				var elmnt = document.body;
				elmnt.scrollTop = 0;
			}
		});
	}
}
// This function is used to fetch the details of the created tickets
// Input is fetched in the table format
function CreateTicketList() {

	// Clear result div
	$("#form_holder_create_ticket").fadeOut(0);

	$("#button_holder")
			.html(
					"<button class=\"btn btn-primary\" onClick=\"CreateTicketList()\">View Tickets</button>&nbsp;<button class=\"btn btn-primary\" onClick=\"selfToggle()\">Create Ticket</button>");
	$("#creationstatus_message").html("");
	$("#ticketid").html("");
	$.ajax({
		url : 'rest/portal/viewtickets',
		type : 'GET',
		success : function(data) {
			$("#form_holder_view_tickets").html(data);
			$("#form_holder_view_tickets").fadeIn(0);
		}
	});
}
// This is a utility method used to hide/unhide specific forms within the page
function selfToggle() {
	$("#form_holder_create_ticket").fadeIn(0);
	$("#ticketing_form")[0].reset();
	$("#form_holder_view_tickets").fadeOut(0);
	$("#action_buttons_edit").fadeOut(0);
	$("#action_buttons_new").fadeIn(0);
}
// This function is used to fetch the details of the ticket which the user has
// opted to edit from the List page
function loadEditForm(ticketId) {
	$.ajax({
		url : 'rest/portal/editticket',
		type : 'GET',
		data : "tid=" + ticketId,
		success : function(data) {
			var splittedData = data.split('&');
			var finalValue = 0;
			var vale = 'Software';
			for (var loop = 0; loop < splittedData.length; loop++) {
				var parameter = splittedData[loop].split("=");
				var key = parameter[0];
				var value = parameter[1];
				if (key == 'assignee') {
					/*
					 * if(value == 'Jame' || value == 'Chris' || value ==
					 * 'Christy') finalValue = 1; else if(value == 'Don' ||
					 * value == 'Jack') finalValue = 2; else finalValue = 0;
					 */
					finalValue = value;
				} else {
					if (key == "complainttype") {
						vale = value;
					}
					$('#' + key).val(value);
				}

			}
			$("#ticketid").html(ticketId);
			$("#hidticketid").val(ticketId);
			$("#form_holder_view_tickets").fadeOut(0);
			$("#form_holder_create_ticket").fadeIn(0);
			$("#action_buttons_edit").fadeIn(0);
			$("#action_buttons_new").fadeOut(0);
			loadSubList(vale, finalValue);
		}
	});
}

// List of tickets can be saved to the local machine if required
function saveAsExcel() {
	$('#ticket_details_table').tableExport({
		type : 'excel',
		escape : 'false'
	});
}

function checkEmail() {
	var email = document.getElementById('emailid');
	var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;

	if (!filter.test(email.value)) {
		email.focus;
		$('#email-alert').html('Enter a valid email id!');
		var elmnt = document.body;
		elmnt.scrollTop = 0;
		return false;
	} else {
		$('#email-alert').html('');
		return true;
	}
}

function validateCustomerName() {
	var nameRegex = /^[a-zA-Z]+$/;
	var validUsername = document.getElementById('customername');
	var valid = true;
	if (validUsername == null) {
		valid = false
	} else {
		if (nameRegex.test(validUsername.value)) {
			valid = true;
			$('#name-alert').html('');
			return true;
		} else {
			valid = false;
		}
	}
	if (!valid) {
		$('#name-alert').html('Enter a valid customer name! [A-Z or a-z]');
		var elmnt = document.body;
		elmnt.scrollTop = 0;
		return false;
	}
}

function validateTelephoneNumber() {
	var noRegex = /^[0-9]{6,10}$/;
	var validNumber = document.getElementById('telnum');
	var valid = true;
	if (validNumber.value == null) {
		valid = false;
	} else {
		if (noRegex.test(validNumber.value)) {
			$('#tel-alert').html('');
			return true;
		} else {
			valid = false;
		}
	}
	if (!valid) {
		$('#tel-alert')
				.html('Telephone number should be between [6-10] digits');
		var elmnt = document.body;
		elmnt.scrollTop = 0;
		return false;
	}
}

function validateareacode() {
	var noRegex = /^[0-9]{1,5}$/;
	var validNumber = document.getElementById('areacode');
	var valid = true;
	if (validNumber.value == null) {
		valid = false;
	} else {
		if (noRegex.test(validNumber.value)) {
			$('#ac-alert').html('');
			return true;
		} else {
			valid = false;
		}
	}
	if (!valid) {
		$('#ac-alert').html('area code should be digits (1-5)');
		var elmnt = document.body;
		elmnt.scrollTop = 0;
		return false;
	}
}
