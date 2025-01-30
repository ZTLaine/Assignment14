document.addEventListener('DOMContentLoaded', function() {
    const channelNameInput = document.getElementById('channelName');
    const modal = document.getElementById('newChannelModal');
    const btn = document.getElementById('newChannelBtn');
    const span = document.getElementsByClassName('close-modal')[0];
    const createBtn = document.getElementById('createChannelBtn');

    const username = sessionStorage.getItem('username');
    if (!username) {
        window.location.href = '/welcome';
    }

    btn.onclick = function() {
        modal.style.display = "block";
        channelNameInput.focus();
    }

    span.onclick = function() {
        modal.style.display = "none";
    }

    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }

    channelNameInput.addEventListener('focus', function() {
        if (this.placeholder === 'Enter channel name') {
            this.placeholder = '';
        }
    });

    channelNameInput.addEventListener('blur', function() {
        if (this.placeholder === '') {
            this.placeholder = 'Enter channel name';
        }
    });

    function createChannel() {
        const channelName = channelNameInput.value.trim();
        const username = sessionStorage.getItem('username');
        
        if (!username) {
            window.location.href = '/welcome';
            return;
        }
        
        if (channelName) {
            fetch('/channels/addChannel', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ 
                    name: channelName,
                    username: username 
                })
            })
            .then(response => response.json())
            .then(data => {
                if (data.result === 'SUCCESS') {
                    window.location.href = `/channel/${data.data.id}`;
                } else {
                    alert(data.message || 'Channel name already exists');
                    channelNameInput.value = '';
                    channelNameInput.focus();
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Error creating channel');
            });
        }
    }

    createBtn.onclick = createChannel;
    channelNameInput.addEventListener('keypress', (e) => {
        if (e.key === 'Enter') {
            createChannel();
        }
    });
}); 