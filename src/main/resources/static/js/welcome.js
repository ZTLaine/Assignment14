// Check if username exists in sessionStorage
function checkUsername() {
    const username = sessionStorage.getItem('username');
    if (!username) {
        // Show username prompt if no username is stored
        const newUsername = prompt('Please enter your username:');
        if (newUsername && newUsername.trim()) {
            // Store username and redirect
            sessionStorage.setItem('username', newUsername.trim());
            // Post the username to the server
            submitUsername(newUsername.trim());
        } else {
            // If user cancels or enters empty string, ask again
            checkUsername();
        }
    } else {
        // If username exists, redirect to channels
        window.location.href = '/channels';
    }
}

// Function to submit username to server
function submitUsername(username) {
    fetch('/welcome', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username: username })
    })
    .then(response => {
        if (response.ok) {
            window.location.href = '/channels';
        } else {
            console.error('Error submitting username');
            sessionStorage.removeItem('username');
            checkUsername();
        }
    })
    .catch(error => {
        console.error('Error:', error);
        sessionStorage.removeItem('username');
        checkUsername();
    });
}

// Run check when page loads
document.addEventListener('DOMContentLoaded', checkUsername); 