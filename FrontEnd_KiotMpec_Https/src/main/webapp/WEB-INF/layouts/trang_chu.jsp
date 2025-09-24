<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>MpecLab</title>
    <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
    <link rel="shortcut icon" type="image/png" href="resources/dist/img/LogoBK.png"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title><tiles:insertAttribute name="title"/></title>
    <%@include file="../library/library_css.jsp" %>
    <script src="resources/dist/js/ajax/ajax_main.js"></script>
    <script src="resources/pages/thong_tin_header/ajax_header.js"></script>
</head>

<body class="skin-purple">
<!-- Main Js -->
<%@include file="../library/library_js.jsp" %>

<div class="wrapper">
    <tiles:insertAttribute name="header"/>
    <tiles:insertAttribute name="body"/>
    <tiles:insertAttribute name="footer"/>
</div>

<!-- AI Chat Widget -->
<%@include file="../bodyfragments/chat/ai_chat_widget.jsp" %>

</body>
</html>