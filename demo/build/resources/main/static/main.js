const purchaseTab = document.getElementById("bookList");
const searchTab = document.getElementById("searchContainer");


function handleLogin(event) {
    event.preventDefault();

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    fetch('/api/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            username: username,
            password: password,
        }),
    })
    .then(response => response.json())
    .then(data => {
        if (data.username !== "Null") {
            alert('Logged in successfully as ' + data.name);

            localStorage.setItem('user', JSON.stringify(data));
            window.location.href = 'homepage.html';
        } else {
            alert('Login failed');
        }
    })
    .catch((error) => {
        console.error('Error:', error);
    });
}

function handleRegistration(event) {
    event.preventDefault();

    const fullName = document.getElementById('name').value;
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    fetch('/api/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            fullName: fullName,
            username: username,
            password: password,
        }),
    })
    .then(response => response.text())
    .then(message => {
        alert(message);
    })
    .catch((error) => {
        console.error('Error:', error);
    });
}

var user = JSON.parse(localStorage.getItem('user'));
console.log(user);
document.getElementById("greet").innerHTML = "Welcome " + user.name;

function purchaseBook() {
    hideUserDetails();
    searchTab.style.display = "none";
    purchaseTab.style.display = "flex";
    console.log(searchTab.style.display);
    fetch('/api/books')
        .then(response => response.json())
        .then(data => {
            const bookListDiv = document.getElementById('bookList');
            bookListDiv.innerHTML = ''; // Clear previous book list

            if (data.length === 0) {
                bookListDiv.innerText = 'No books available.';
            } else {
                data.forEach(book => {
                    const bookDiv = document.createElement('div');
                    bookDiv.classList.add('book-item');

                    const title = document.createElement('h3');
                    title.innerText = book.title;

                    const author = document.createElement('p');
                    author.innerText = `Author: ${book.author}`;

                    const price = document.createElement('p');
                    price.innerText = `Price: $${book.price}`;

                    const isbn = document.createElement('p');
                    isbn.innerText = `ISBN: ${book.isbn}`;

                    bookDiv.appendChild(title);
                    bookDiv.appendChild(author);
                    bookDiv.appendChild(price);
                    bookDiv.appendChild(isbn);

                    const selectButton = document.createElement('button');
                    selectButton.innerText = 'Select';
                    selectButton.addEventListener('click', () => {
                        purchaseSelectedBook(book);
                    });

                    bookDiv.appendChild(selectButton);

                    bookListDiv.appendChild(bookDiv);
                });
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
}
function logout() {
    localStorage.removeItem('user');
    window.location.href = 'index.html';
}
function showSearchContainer() {
  purchaseTab.style.display = "none";
  hideUserDetails();
  const searchContainer = document.getElementById('searchContainer');
  searchContainer.style.display = 'block';
}
function searchBook() {
  hideUserDetails();
  const searchInput = document.getElementById('searchInput');
  const searchTerm = searchInput.value.trim(); // Get the search term from the input field

  // Make the search request to the server
  fetch('/api/search', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ searchTerm }),
  })
    .then(response => response.json())
    .then(data => {
      const searchResultsDiv = document.getElementById('searchResults');
      searchResultsDiv.innerHTML = ''; // Clear previous search results

      if (data.length === 0) {
              searchResultsDiv.innerText = 'No results found.';
        } else {
          const resultList = document.createElement('ul');
          resultList.classList.add('search-result-list');

          data.forEach(book => {
            const listItem = document.createElement('li');
            listItem.classList.add('search-result');
            listItem.addEventListener('click', () => {
              purchaseSelectedBook(book);
            });

            const contentDiv = document.createElement('div');
            contentDiv.classList.add('search-result-content');

            const title = document.createElement('h3');
            title.classList.add('search-result-title');
            title.innerText = book.title;

            const author = document.createElement('p');
            author.classList.add('search-result-author');
            author.innerText = `Author: ${book.author}`;

            const price = document.createElement('p');
            price.classList.add('search-result-price');
            price.innerText = `Price: $${book.price}`;

            const isbn = document.createElement('p');
            isbn.classList.add('search-result-isbn');
            isbn.innerText = `ISBN: ${book.isbn}`;

            contentDiv.appendChild(title);
            contentDiv.appendChild(author);
            contentDiv.appendChild(price);
            contentDiv.appendChild(isbn);

            listItem.appendChild(contentDiv);
            resultList.appendChild(listItem);
          });

          searchResultsDiv.appendChild(resultList);
        }
    });
    function handleBookSelection(book) {
      // Handle book selection here
      console.log('Selected book:', book);
      // You can implement further actions like purchasing the book
    }
}

/* pop up */
function purchaseSelectedBook(book) {
  const purchaseModal = document.getElementById("purchaseModal");
  const purchaseBookDetails = document.getElementById("purchaseBookDetails");
  const closeBtn = document.getElementsByClassName("close")[0];

  // Populate the book details in the pop-up window
  purchaseBookDetails.innerHTML = `
    <h4>Title: ${book.title}</h4>
    <p>Author: ${book.author}</p>
    <p>Price: $${book.price}</p>
    <p>ISBN: ${book.isbn}</p>
  `;

  // Open the pop-up window
  purchaseModal.style.display = "block";

  // Close the pop-up window when the user clicks on the close button
  closeBtn.onclick = function () {
    purchaseModal.style.display = "none";
  };

  // Close the pop-up window when the user clicks outside of it
  window.onclick = function (event) {
    if (event.target == purchaseModal) {
      purchaseModal.style.display = "none";
    }
  };
  const pButton = document.getElementById("purchaseButton");
  pButton.addEventListener('click', () => {purchase(book)});
}

// Assuming you have the list of books available as an array
const bookList = document.getElementById('bookList');
bookList.innerHTML = ''; // Clear previous book list

books.forEach(book => {
  const listItem = document.createElement('li');
  listItem.innerText = book.title;
  bookList.appendChild(listItem);
});
function toggleUserDetails() {
  searchTab.style.display = 'none';
  purchaseTab.style.display = 'none';
  const userDetails = document.querySelector('.user-details');
  userDetails.classList.toggle('show');
  document.getElementById('fullName').textContent = user.name;
  document.getElementById('username').textContent = user.username;
  document.getElementById('password').textContent = user.password;
  getUserBooks(user);
}
function getUserBooks(user) {
  const userBookList = document.getElementById('userBookList');

  fetch(`/api/user-books?username=${encodeURIComponent(user.username)}`)
    .then(response => response.json())
    .then(data => {
      userBookList.innerHTML = '';

      if (data.length === 0) {
        userBookList.innerText = 'No books found for this user.';
      } else {
        data.forEach(book => {
          const bookDiv = document.createElement('div');
          bookDiv.classList.add('book-item');

          const title = document.createElement('h3');
          title.innerText = book.title;

          const author = document.createElement('p');
          author.innerText = `Author: ${book.author}`;

          const price = document.createElement('p');
          price.innerText = `Price: $${book.price}`;

          const isbn = document.createElement('p');
          isbn.innerText = `ISBN: ${book.isbn}`;

          bookDiv.appendChild(title);
          bookDiv.appendChild(author);
          bookDiv.appendChild(price);
          bookDiv.appendChild(isbn);

          userBookList.appendChild(bookDiv);
        });
      }
    })
    .catch(error => {
      console.error('Error:', error);
    });
}



// Add this function to hide the user details initially
function hideUserDetails() {
  const userDetails = document.querySelector('.user-details');
  userDetails.classList.remove('show');
}

// Call the hideUserDetails function when the page loads
window.addEventListener('load', hideUserDetails);

function purchase(book) {
  const isbn = book.isbn;
  const username = user.username;
  console.log(username);
  fetch('/api/purchase', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
                username: username,
                isbn: isbn,
            }),
  })
    .then(response => {
      if (response.ok) {
        // Perform any actions on successful purchase
        console.log('Book purchased successfully');
      } else {
        // Handle purchase error
        console.error('Failed to purchase book');
      }
    })
    .catch(error => {
      console.error('Error:', error);
    });
}
