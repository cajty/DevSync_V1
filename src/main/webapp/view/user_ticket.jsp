<%--
  Created by IntelliJ IDEA.
  User: YouCode
  Date: 10/11/2024
  Time: 5:42 PM
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Ticket Board</title>
  <script src="https://cdn.tailwindcss.com"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
  <style>
    .ticket-card:hover .details {
      display: block;
    }
    .ticket-card .details {
      display: none;
    }
  </style>
</head>
<body class="bg-gray-100">
<%@ include file="../component/header.jsp" %>
<%@ include file="../component/add_tiket_ajax.jsp" %>

<h1 class="text-2xl font-semibold text-center mt-8">BOSHABA</h1>
<h1 class="text-2xl font-semibold text-center mt-8">LAHSANZE</h1>

<div class="container mx-auto py-8">
 <div class="grid grid-cols-4 gap-4">
  <!-- IN PROGRESS Column -->
  <div class="max-h-70 overflow-y-auto">
    <h2 class="text-lg font-semibold mb-4">IN PROGRESS</h2>
    <div id="inProgressColumn" class="space-y-4"></div>
  </div>

  <!-- RESOLVED Column -->
  <div class="max-h-70 overflow-y-auto">
    <h2 class="text-lg font-semibold mb-4">RESOLVED</h2>
    <div id="resolvedColumn" class="space-y-4"></div>
  </div>

  <!-- REPLACED Column -->
  <div class="max-h-70 overflow-y-auto">
    <h2 class="text-lg font-semibold mb-4">REPLACED</h2>
    <div id="replacedColumn" class="space-y-4"></div>
  </div>

  <!-- CLOSED Column -->
  <div class="max-h-70 overflow-y-auto">
    <h2 class="text-lg font-semibold mb-4">CLOSED</h2>
    <div id="closedColumn" class="space-y-4"></div>
  </div>
</div>
  </div>
</div>

<script>
  function loadTickets() {
    $.ajax({
      url: '/DevSync/ticketJsoin',
      method: 'GET',
      dataType: 'json',
      success: function(data) {
        console.log(data);
        updateColumn('inProgressColumn', data.inProgressTickets);
        updateColumn('resolvedColumn', data.resolvedTickets);
        updateColumn('replacedColumn', data.replacedTickets);
        updateColumn('closedColumn', data.closedTickets);
        getTags(data.tags);

      },
      error: function(xhr, status, error) {
        console.error('Error fetching tickets:', error);
      }
    });
  }



  function getTags(tags) {
    console.log("test1");
    console.log(tags);
    console.log("test");
    const listOfTagsDiv = document.getElementById('listOfTags');
    listOfTagsDiv.innerHTML = '';  // Clear existing content

    tags.forEach(tag => {
      const tagDiv = document.createElement('div');
      tagDiv.className = 'flex items-center';

      tagDiv.innerHTML = `
            <input type="checkbox" id="tag_${tag.id}" name="tags" value="${tag.id}" class="h-4 w-4 text-blue-600 border-gray-300 rounded focus:ring-blue-500 mr-2">
            <label for="tag_${tag.id}" class="text-gray-800">${tag.name}</label>
        `;

      listOfTagsDiv.appendChild(tagDiv);
    });
  }


  document.getElementById('ticketForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent form from submitting the traditional way
    addTicket(); // Call the addTicket function
  });

  function addTicket() {
    const title = document.getElementById('title').value;
    const description = document.getElementById('description').value;
    const deadline = document.getElementById('deadline').value;
    const tags = Array.from(document.getElementsByName('tags'))
            .filter(tag => tag.checked)
            .map(tag => tag.value);

    $.ajax({
      url: '/DevSync/ticketJsoin/add',
      method: 'POST',
      data: {
        id_user: 61,
        title: title,
        description: description,
        deadline: deadline,
        tags: tags
      },
      success: function() {
        loadTickets();
        // Clear the form after submission
        document.getElementById('title').value = '';
        document.getElementById('description').value = '';
        document.getElementById('deadline').value = '';
        document.getElementsByName('tags').forEach(tag => tag.checked = false);
        // Hide the modal
        document.getElementById('ticketModal').classList.add('hidden');
      },
      error: function(xhr, status, error) {
        console.error('Error adding ticket:', error);
      }
    });
  }






  function updateColumn(columnId, tickets) {
    const column = document.getElementById(columnId);
    column.innerHTML = '';

    tickets.forEach(ticket => {
      const ticketCard = createTicketCard(ticket);
      column.appendChild(ticketCard);
    });
  }

  function createTicketCard(ticket) {
    const cardDiv = document.createElement('div');
    cardDiv.id = `ticket_${ticket.id}`;
    cardDiv.className = 'ticket-card bg-white p-4 shadow-lg rounded-lg';
    console.log(ticket.canDeleteTicket);

   cardDiv.innerHTML = `
    <div class="mb-2" >
        <p class="font-bold">${ticket.title}</p>
        <p class="text-sm text-gray-500">Due: ${ticket.deadline}</p>
    </div>
    <div class="details bg-gray-100 p-2 mt-2 rounded-md">
        <p><strong>Description:</strong> ${ticket.description}</p>
        <p><strong>User:</strong> ${ticket.user}</p>
        <div class="flex mt-2 space-x-2">
        ${ticket.status === 'IN_PROGRESS' && ticket.canReplaceTicket  ? `
        <button onclick="replaceToken(${ticket.id})" class="bg-yellow-500 hover:bg-yellow-600 text-white font-bold py-1 px-3 rounded-lg">
            Update
        </button>
    ` : ''}
    ${(ticket.status === 'IN_PROGRESS' && ticket.canDeleteTicket) ||ticket.status === 'REPLACED'   ? `
        <button onclick="deleteToken(${ticket.id})" class="bg-red-500 hover:bg-red-600 text-white font-bold py-1 px-3 rounded-lg">
            Delete
        </button>
    ` : ''}
        </div>
        <div class="flex flex-wrap gap-1 mt-2">
            ${ticket.tags.map(tag => `
                <span class="bg-blue-200 text-blue-800 text-xs font-semibold mr-2 px-2.5 py-0.5 rounded">
                    ${tag.name}
                </span>
            `).join('')}
        </div>
    </div>
`;

    return cardDiv;
  }

  function replaceToken(ticketId) {
    // Implement update functionality
    $.ajax({
      url: `/DevSync/ticketJsoin/replace_token`,
      method: 'POST',
      data: { id_ticket: ticketId, id_user: 61},
      success: function() {
          loadTickets();
      },
      error: function(xhr, status, error) {
        console.error('Error updating ticket:', error);
      }
    });
  }

  function deleteToken(ticketId) {
    if (confirm('Are you sure you want to delete this ticket?')) {
      $.ajax({
        url: `/DevSync/ticketJsoin/delete_token`,
        method: 'POST',
        data: {
          id_ticket: ticketId,
          id_user: 61
        },
        success: function() {
          removeTicket(ticketId);
        },
        error: function(xhr, status, error) {
         alert('Error deleting ticket:', error);
        }
      });
    }
  }
  function removeTicket(ticketId) {
    const ticket = document.getElementById(`ticket_`+ticketId);
    if (ticket) {
      ticket.remove();
    }
  }


  $(document).ready(function() {
    loadTickets();


    setInterval(loadTickets, 350000);
  });

  const modal = document.getElementById("ticketModal");
  const openModalButton = document.getElementById("openModalButton");
  const closeModalButton = document.getElementById("closeModalButton");

  openModalButton.addEventListener("click", () => {
    modal.classList.remove("hidden");
  });

  closeModalButton.addEventListener("click", () => {
    modal.classList.add("hidden");
  });

  window.addEventListener("click", (event) => {
    if (event.target === modal) {
      modal.classList.add("hidden");
    }
  });
</script>


</body>
</html>