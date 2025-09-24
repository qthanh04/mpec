<%--
  Created by IntelliJ IDEA.
  User: QUY
  Date: 02/06/2021
  Time: 5:03 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    [v-cloak] { display: none; }

    .header-highlight {
        color: #ff4954;
    }

    .info {
        padding: 1.5rem;
    }

    .container {
        padding-top: 1rem;
    }

    #videos {
        display: flex;
        flex-wrap: wrap;
        margin-top: 20px;
    }

    #videos video {
        flex: 1 1 50%;
        padding: 0;
        min-width: 0;
    }
</style>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />

    <title>Clone Zoom với Stringee hihi</title>

    <!-- import the webpage's stylesheet -->
    <link
            rel="stylesheet"
            href="https://cdn.jsdelivr.net/npm/bulma@0.9.1/css/bulma.min.css"
    />
    <link rel="stylesheet" href="/style.css" />

    <!-- import the webpage's javascript file -->

    <script src="https://cdn.jsdelivr.net/npm/vue@2.6.12/dist/vue.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/axios@0.20.0/dist/axios.min.js"></script>
    <script src="https://cdn.stringee.com/sdk/web/2.2.1/stringee-web-sdk.min.js"></script>
</head>
<body>
<div class="container has-text-centered" v-cloak id="app">
    <h1 class="title">
        Phòng zom của phần mềm quản lý bán hàng
        <span class="header-highlight">KiotMpec</span>
    </h1>

    <div>
        <button class="button is-primary" v-if="!room" @click="createRoom">
            Tạo Meeting
        </button>

        <button class="button is-info" v-if="!room" @click="joinWithId">
            Join Meeting
        </button>

        <button class="button is-info" v-if="room" @click="publish(true)">
            Share Desktop
        </button>
    </div>

    <div v-if="roomId" class="info">
        <p>Bạn đang ở trong room <strong>{{roomId}}</strong>.</p>
        <p>
            Gửi link này cho bạn bè cùng join room nhé
            <a v-bind:href="roomUrl" target="_blank">{{roomUrl}}</a>.
        </p>
        <p>Hoặc bạn cũng có thể copy <code>{{roomId}}</code> để share.</p>
    </div>
</div>

<div class="container">
    <div id="videos"></div>
</div>
</body>

<script src="resources/pages/chat/api.js"></script>
<script src="resources/pages/chat/video_call.js"></script>
</html>

