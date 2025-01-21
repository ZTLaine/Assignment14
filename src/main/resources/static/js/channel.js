document.addEventListener('DOMContentLoaded', function() {
    const messagesContainer = document.getElementById('messages');
    const messageInput = document.getElementById('messageInput');
    const sendButton = document.getElementById('sendButton');
    const channelId = window.CHAT_CONFIG.channelId;
    const username = sessionStorage.getItem('username');
    let lastMessageId = -1;

    if (!username) {
        window.location.href = '/welcome';
        return;
    }

    function formatTimestamp(timestamp) {
        return new Date(timestamp).toLocaleTimeString([], { 
            hour: 'numeric', 
            minute: '2-digit',
            hour12: true 
        });
    }

    function addMessageToDisplay(message) {
        const messageDiv = document.createElement('div');
        const isOwnMessage = message.username === username;
        messageDiv.className = `message ${isOwnMessage ? 'own-message' : 'other-message'}`;
        messageDiv.innerHTML = `
            <div class="sender">${message.username}</div>
            <div class="content">${message.content}</div>
            <div class="timestamp">${formatTimestamp(message.createdAt)}</div>
        `;
        messagesContainer.appendChild(messageDiv);
        messagesContainer.scrollTop = messagesContainer.scrollHeight;
    }

    async function fetchMessages() {
        try {
            const response = await fetch(`/api/channel/${channelId}/messages?after=${lastMessageId}`);
            if (response.ok) {
                const data = await response.json();
                
                if (data.result === 'SUCCESS' && Array.isArray(data.data)) {
                    const messages = data.data;
                    messages.sort((a, b) => a.id - b.id);
                    
                    messages.forEach(message => {
                        if (message.id > lastMessageId) {
                            lastMessageId = message.id;
                            addMessageToDisplay(message);
                        }
                    });

                    if (lastMessageId === -1 && messages.length > 0) {
                        lastMessageId = Math.max(...messages.map(m => m.id));
                    }
                }
            } else {
                console.error('Error response:', await response.text());
            }
        } catch (error) {
            console.error('Error fetching messages:', error);
        }
    }

    async function sendMessage(content) {
        try {
            const messageDTO = {
                content: content,
                username: username,
                channelId: channelId,
                createdAt: Date.now()
            };

            const response = await fetch(`/api/channel/${channelId}/messages`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(messageDTO)
            });

            if (response.ok) {
                const data = await response.json();
                messageInput.value = '';
                await fetchMessages();
            } else {
                console.error('Error response:', await response.text());
            }
        } catch (error) {
            console.error('Error sending message:', error);
        }
    }

    sendButton.addEventListener('click', () => {
        const content = messageInput.value.trim();
        if (content) {
            sendMessage(content);
        }
    });

    messageInput.addEventListener('keypress', (e) => {
        if (e.key === 'Enter') {
            const content = messageInput.value.trim();
            if (content) {
                sendMessage(content);
            }
        }
    });

    messageInput.addEventListener('focus', function() {
        if (this.placeholder === 'Type your message...') {
            this.placeholder = '';
        }
    });

    messageInput.addEventListener('blur', function() {
        if (this.placeholder === '') {
            this.placeholder = 'Type your message...';
        }
    });

    fetchMessages();
    const pollInterval = setInterval(fetchMessages, 500);
    
    window.addEventListener('unload', () => {
        clearInterval(pollInterval);
    });
}); 