<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestionnaire de Contacts</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <h1>Bienvenue dans le Gestionnaire de Contacts</h1>
        <div class="button-container">
            <a href="${pageContext.request.contextPath}/contacts/list" class="btn primary-btn">Accéder à mes contacts</a>
        </div>
    </div>
</body>
</html>