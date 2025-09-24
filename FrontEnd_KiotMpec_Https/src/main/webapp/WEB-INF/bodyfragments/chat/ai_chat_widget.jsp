<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!-- AI Chat Widget -->
<div id="ai-chat-widget">
    <!-- Chat Toggle Button -->
    <div id="chat-toggle-btn" class="chat-toggle-btn">
        <i class="fas fa-comments"></i>
        <span class="chat-badge" id="chat-badge" style="display: none;">1</span>
    </div>
    
    <!-- Chat Window -->
    <div id="chat-window" class="chat-window" style="display: none;">
        <!-- Chat Header -->
        <div class="chat-header">
            <div class="chat-header-info">
                <i class="fas fa-robot chat-avatar"></i>
                <div>
                    <h4>AI Assistant</h4>
                    <span class="chat-status">Online</span>
                </div>
            </div>
            <div class="chat-actions">
                <button id="chat-minimize-btn" class="chat-action-btn">
                    <i class="fas fa-minus"></i>
                </button>
                <button id="chat-close-btn" class="chat-action-btn">
                    <i class="fas fa-times"></i>
                </button>
            </div>
        </div>
        
        <!-- Chat Messages Wrapper -->
        <div class="chat-messages-wrapper">
            <!-- Chat Messages -->
            <div class="chat-messages" id="chat-messages">
                <div class="ai-message">
                    <div class="message-avatar">
                        <i class="fas fa-robot"></i>
                    </div>
                    <div class="message-content">
                        <div class="message-text">
                            Xin chào! Tôi có thể giúp gì cho bạn?
                        </div>
                        <div class="message-time"></div>
                    </div>
                </div>
            </div>
            
            <!-- Scroll to Bottom Button -->
            <button id="scroll-to-bottom-btn" class="scroll-to-bottom-btn">
                <i class="fas fa-chevron-down"></i>
            </button>
        </div>
        
        <!-- Chat Input -->
        <div class="chat-input">
            <form id="chat-form">
                <div class="chat-input-group">
                    <input type="text" id="chat-message-input" placeholder="Nhập tin nhắn..." autocomplete="off"/>
                    <button type="submit" id="chat-send-btn">
                        <i class="fas fa-paper-plane"></i>
                    </button>
                </div>
            </form>
        </div>
        
        <!-- Typing Indicator -->
        <div class="chat-typing" id="chat-typing" style="display: none;">
            <div class="typing-dots">
                <span></span>
                <span></span>
                <span></span>
            </div>
            <span>AI đang trả lời...</span>
        </div>
    </div>
</div>

<link rel="stylesheet" href="resources/dist/css/ai-chat-widget.css">
<script src="resources/dist/js/ai-chat-widget.js"></script> 