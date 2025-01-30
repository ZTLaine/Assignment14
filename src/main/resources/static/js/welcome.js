document.addEventListener('DOMContentLoaded', function() {
    const modal = document.getElementById('usernameModal');
    const joinBtn = document.getElementById('joinChatBtn');
    const closeBtn = document.getElementsByClassName('close-modal')[0];
    const submitBtn = document.getElementById('submitUsername');
    const usernameInput = document.getElementById('usernameInput');

    const username = sessionStorage.getItem('username');
    if (username) {
        window.location.href = '/channels';
        return;
    }

    joinBtn.onclick = function() {
        modal.style.display = "block";
    }

    closeBtn.onclick = function() {
        modal.style.display = "none";
    }

    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }

    usernameInput.addEventListener('focus', function() {
        if (this.placeholder === 'Enter your username') {
            this.placeholder = '';
        }
    });

    usernameInput.addEventListener('blur', function() {
        if (this.placeholder === '') {
            this.placeholder = 'Enter your username';
        }
    });

    function submitUsername() {
        const username = usernameInput.value.trim();
        if (username) {
            fetch('/welcome', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ username: username })
            })
            .then(response => response.json().catch(() => response))
            .then(data => {
                if (data.ok) {
                    sessionStorage.setItem('username', username);
                    fetch('/welcome/session', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                        },
                        body: JSON.stringify({ username: username })
                    }).then(() => {
                        window.location.href = '/channels';
                    });
                } else if (data.result === 'ERROR') {
                    alert(data.message || 'Username already exists');
                    usernameInput.value = '';
                    usernameInput.focus();
                } else {
                    console.error('Error submitting username');
                }
            })
            .catch(error => {
                console.error('Error:', error);
            });
        }
    }

    submitBtn.onclick = submitUsername;
    usernameInput.addEventListener('keypress', (e) => {
        if (e.key === 'Enter') {
            submitUsername();
        }
    });
}); 