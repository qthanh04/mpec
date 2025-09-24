'use strict';

class AIChatWidget {
    constructor() {
        this.isOpen = false;
        this.isMinimized = false;
        this.isScrolledToBottom = true;
        this.scrollThreshold = 50;
        this.initElements();
        this.bindEvents();
        this.updateTime();
    }

    initElements() {
        this.widget = document.getElementById('ai-chat-widget');
        this.toggleBtn = document.getElementById('chat-toggle-btn');
        this.chatWindow = document.getElementById('chat-window');
        this.minimizeBtn = document.getElementById('chat-minimize-btn');
        this.closeBtn = document.getElementById('chat-close-btn');
        this.chatForm = document.getElementById('chat-form');
        this.messageInput = document.getElementById('chat-message-input');
        this.sendBtn = document.getElementById('chat-send-btn');
        this.messagesContainer = document.getElementById('chat-messages');
        this.typingIndicator = document.getElementById('chat-typing');
        this.badge = document.getElementById('chat-badge');
        this.scrollToBottomBtn = document.getElementById('scroll-to-bottom-btn');
    }

    bindEvents() {
        this.toggleBtn.addEventListener('click', () => this.toggleChat());
        this.minimizeBtn.addEventListener('click', () => this.minimizeChat());
        this.closeBtn.addEventListener('click', () => this.closeChat());
        this.chatForm.addEventListener('submit', (e) => this.handleSubmit(e));
        this.scrollToBottomBtn.addEventListener('click', () => this.scrollToBottom(true));
        
        this.messageInput.addEventListener('keypress', (e) => {
            if (e.key === 'Enter' && !e.shiftKey) {
                e.preventDefault();
                this.handleSubmit(e);
            }
        });

        // Simple scroll handler
        this.messagesContainer.addEventListener('scroll', () => this.handleScroll());

        // Click outside to close
        document.addEventListener('click', (e) => {
            if (this.isOpen && !this.widget.contains(e.target)) {
                this.closeChat();
            }
        });
    }

    handleScroll() {
        const container = this.messagesContainer;
        const distanceFromBottom = container.scrollHeight - container.scrollTop - container.clientHeight;
        
        this.isScrolledToBottom = distanceFromBottom <= this.scrollThreshold;
        
        // Show/hide scroll to bottom button
        if (this.isScrolledToBottom) {
            this.scrollToBottomBtn.classList.remove('show');
        } else {
            this.scrollToBottomBtn.classList.add('show');
        }
    }

    toggleChat() {
        if (this.isOpen) {
            this.closeChat();
        } else {
            this.openChat();
        }
    }

    openChat() {
        this.isOpen = true;
        this.chatWindow.style.display = 'flex';
        setTimeout(() => {
            this.chatWindow.classList.add('show');
            this.messageInput.focus();
        }, 10);
        this.hideBadge();
    }

    closeChat() {
        this.isOpen = false;
        this.chatWindow.classList.remove('show');
        setTimeout(() => {
            this.chatWindow.style.display = 'none';
        }, 300);
    }

    minimizeChat() {
        this.closeChat();
    }

    async handleSubmit(e) {
        e.preventDefault();
        
        const message = this.messageInput.value.trim();
        if (!message) return;

        // Add user message
        this.addUserMessage(message);
        
        // Clear input and show typing
        this.messageInput.value = '';
        this.setLoading(true);
        
        try {
            await this.sendToAI(message);
        } catch (error) {
            this.addErrorMessage('Không thể gửi tin nhắn. Vui lòng thử lại!');
        } finally {
            this.setLoading(false);
        }
    }

    addUserMessage(message) {
        const messageDiv = document.createElement('div');
        messageDiv.className = 'user-message';
        messageDiv.innerHTML = `
            <div class="message-avatar">
                <i class="fas fa-user"></i>
            </div>
            <div class="message-content">
                <div class="message-text">${this.escapeHtml(message)}</div>
                <div class="message-time">${this.getCurrentTime()}</div>
            </div>
        `;
        
        this.messagesContainer.appendChild(messageDiv);
        
        // Always scroll to bottom for user's own messages
        setTimeout(() => this.scrollToBottom(true), 100);
    }

    addAIMessage(message) {
        const messageDiv = document.createElement('div');
        messageDiv.className = 'ai-message';
        messageDiv.innerHTML = `
            <div class="message-avatar">
                <i class="fas fa-robot"></i>
            </div>
            <div class="message-content">
                <div class="message-text">${this.escapeHtml(message)}</div>
                <div class="message-time">${this.getCurrentTime()}</div>
            </div>
        `;
        
        this.messagesContainer.appendChild(messageDiv);
        
        // NO AUTO-SCROLL for AI messages - let user control
        // Only scroll if user is already at the very bottom
        if (this.isScrolledToBottom) {
            setTimeout(() => this.scrollToBottom(false), 100);
        }
        
        // Show notification if chat is closed
        if (!this.isOpen) {
            this.showBadge();
        }
    }

    addErrorMessage(error) {
        const messageDiv = document.createElement('div');
        messageDiv.className = 'ai-message';
        messageDiv.innerHTML = `
            <div class="message-avatar">
                <i class="fas fa-exclamation-triangle" style="color: #ff6b6b;"></i>
            </div>
            <div class="message-content">
                <div class="message-text" style="color: #ff6b6b;">
                    <i class="fas fa-exclamation-circle"></i> ${this.escapeHtml(error)}
                </div>
                <div class="message-time">${this.getCurrentTime()}</div>
            </div>
        `;
        
        this.messagesContainer.appendChild(messageDiv);
        
        // No auto-scroll for error messages either
    }

    async sendToAI(message) {
        try {
            const response = await fetch('/ai-chat/send', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ message: message })
            });

            if (!response.ok) {
                throw new Error(`HTTP ${response.status}: ${response.statusText}`);
            }

            const data = await response.json();

            if (data.status === 'success') {
                this.addAIMessage(data.response);
            } else {
                this.addErrorMessage(data.response || 'Có lỗi xảy ra khi xử lý tin nhắn');
            }

        } catch (error) {
            console.error('Error sending message to AI:', error);
            this.addErrorMessage('Không thể kết nối đến server. Vui lòng thử lại sau.');
        }
    }

    setLoading(loading) {
        if (loading) {
            this.sendBtn.disabled = true;
            this.messageInput.disabled = true;
            this.typingIndicator.style.display = 'flex';
        } else {
            this.sendBtn.disabled = false;
            this.messageInput.disabled = false;
            this.typingIndicator.style.display = 'none';
            this.messageInput.focus();
        }
    }

    scrollToBottom(smooth = false) {
        const container = this.messagesContainer;
        
        if (smooth) {
            container.scrollTo({
                top: container.scrollHeight,
                behavior: 'smooth'
            });
        } else {
            container.scrollTop = container.scrollHeight;
        }
        
        // Update scroll state
        setTimeout(() => {
            this.handleScroll();
        }, 100);
    }

    getCurrentTime() {
        const now = new Date();
        return now.toLocaleTimeString('vi-VN', { 
            hour: '2-digit', 
            minute: '2-digit' 
        });
    }

    updateTime() {
        const timeElement = this.messagesContainer.querySelector('.ai-message .message-time');
        if (timeElement) {
            timeElement.textContent = this.getCurrentTime();
        }
    }

    escapeHtml(text) {
        const div = document.createElement('div');
        div.textContent = text;
        return div.innerHTML;
    }

    showBadge() {
        this.badge.style.display = 'flex';
        this.badge.style.animation = 'pulse 1s infinite';
    }

    hideBadge() {
        this.badge.style.display = 'none';
        this.badge.style.animation = 'none';
    }

    // Method to clear all messages
    clearMessages() {
        this.messagesContainer.innerHTML = '';
        this.addWelcomeMessage();
    }

    addWelcomeMessage() {
        const welcomeDiv = document.createElement('div');
        welcomeDiv.className = 'ai-message';
        welcomeDiv.innerHTML = `
            <div class="message-avatar">
                <i class="fas fa-robot"></i>
            </div>
            <div class="message-content">
                <div class="message-text">
                    Xin chào! Tôi có thể giúp gì cho bạn?
                </div>
                <div class="message-time">${this.getCurrentTime()}</div>
            </div>
        `;
        
        this.messagesContainer.appendChild(welcomeDiv);
    }
}

// Initialize widget when DOM is loaded
document.addEventListener('DOMContentLoaded', function() {
    window.aiChatWidget = new AIChatWidget();
});

// Quick message functions for easy integration
window.sendQuickChatMessage = function(message) {
    if (window.aiChatWidget) {
        if (!window.aiChatWidget.isOpen) {
            window.aiChatWidget.openChat();
        }
        window.aiChatWidget.messageInput.value = message;
        window.aiChatWidget.messageInput.focus();
    }
};

window.openAIChat = function() {
    if (window.aiChatWidget) {
        window.aiChatWidget.openChat();
    }
};

// Add CSS for pulse animation
const style = document.createElement('style');
style.textContent = `
    @keyframes pulse {
        0% { transform: scale(1); }
        50% { transform: scale(1.1); }
        100% { transform: scale(1); }
    }
`;
document.head.appendChild(style); 