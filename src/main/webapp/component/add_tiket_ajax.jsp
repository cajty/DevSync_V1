
<div class="flex justify-center mt-10">
    <button id="openModalButton" class="bg-blue-600 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-md shadow-md transition duration-300 ease-in-out">
        Create New Ticket
    </button>
</div>
<!-- Modal Background -->
<div id="ticketModal" class="fixed inset-0 hidden bg-gray-900 bg-opacity-70 flex items-center justify-center modal">
    <div class="bg-white rounded-lg shadow-lg max-w-lg w-full">
        <div class="flex justify-between items-center p-4 border-b">
            <h3 class="text-lg font-bold text-gray-800">Create a New Ticket</h3>
            <button id="closeModalButton" class="text-gray-500 hover:text-gray-800 focus:outline-none">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M6 18L18 6M6 6l12 12"/>
                </svg>
            </button>
        </div>

        <!-- Modal Body (Form) -->
        <div class="p-6 bg-gray-100 rounded-lg shadow-lg max-h-[80vh] overflow-y-auto">
            <form id="ticketForm">
                <!-- Title Input -->
                <div class="mb-6">
                    <label for="title" class="block text-gray-600 text-sm font-semibold mb-2">Title</label>
                    <input type="text" id="title" name="title" required class="shadow-md border border-gray-300 rounded-lg w-full py-3 px-4 text-gray-800 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition duration-200 ease-in-out" placeholder="Enter ticket title">
                </div>

                <!-- Description Input -->
                <div class="mb-6">
                    <label for="description" class="block text-gray-600 text-sm font-semibold mb-2">Description</label>
                    <textarea id="description" name="description" required class="shadow-md border border-gray-300 rounded-lg w-full py-3 px-4 text-gray-800 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition duration-200 ease-in-out" placeholder="Enter ticket description" rows="4"></textarea>
                </div>

                <!-- Deadline Input -->
                <div class="mb-6">
                    <label for="deadline" class="block text-gray-600 text-sm font-semibold mb-2">Deadline</label>
                    <input type="date" id="deadline" name="deadline" required class="shadow-md border border-gray-300 rounded-lg w-full py-3 px-4 text-gray-800 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition duration-200 ease-in-out">
                </div>

                <!-- Tags Input (2 tags per line, scrollable) -->
                <div class="mb-6">
                    <label class="block text-gray-600 text-sm font-semibold mb-2">Tags</label>
                    <div id="listOfTags" class="grid grid-cols-2 gap-4 max-h-32 overflow-y-auto border border-gray-300 rounded-lg p-4 shadow-inner">

                    </div>
                </div>

                <!-- Submit Button -->
                <div class="flex justify-end">
                    <button type="submit" class="bg-blue-600 hover:bg-blue-700 text-white font-bold py-3 px-6 rounded-lg shadow-md transition duration-300 ease-in-out focus:ring-4 focus:ring-blue-500 focus:outline-none">
                        Create Ticket
                    </button>
                </div>
            </form>
        </div>

    </div>
</div>
</div>