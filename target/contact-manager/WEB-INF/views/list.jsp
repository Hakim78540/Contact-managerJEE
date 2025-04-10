<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des Contacts</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>Liste des Contacts</h1>
            <div class="header-actions">
                <a href="${pageContext.request.contextPath}/contacts/new" class="btn primary-btn">Ajouter un contact</a>
                <a href="${pageContext.request.contextPath}/" class="btn secondary-btn">Accueil</a>
            </div>
        </header>
        
        <div class="search-box">
            <form action="${pageContext.request.contextPath}/contacts/search" method="get">
                <input type="text" name="keyword" placeholder="Rechercher un contact..." value="${keyword}">
                <button type="submit" class="btn primary-btn">Rechercher</button>
            </form>
        </div>
        
        <c:if test="${not empty error}">
            <div class="error-message">
                <p>${error}</p>
            </div>
        </c:if>
        
        <c:if test="${empty contacts}">
            <div class="info-message">
                <p>Aucun contact trouvé.</p>
            </div>
        </c:if>
        
        <c:if test="${not empty contacts}">
            <table class="contact-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nom</th>
                        <th>Prénom</th>
                        <th>Email</th>
                        <th>Téléphone</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="contact" items="${contacts}">
                        <tr>
                            <td>${contact.id}</td>
                            <td>${contact.nom}</td>
                            <td>${contact.prenom}</td>
                            <td>${contact.email}</td>
                            <td>${contact.telephone}</td>
                            <td class="actions">
                                <a href="${pageContext.request.contextPath}/contacts/view?id=${contact.id}" class="btn info-btn">Voir</a>
                                <a href="${pageContext.request.contextPath}/contacts/edit?id=${contact.id}" class="btn warning-btn">Modifier</a>
                                <a href="${pageContext.request.contextPath}/contacts/delete?id=${contact.id}" class="btn danger-btn" onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce contact?')">Supprimer</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</body>
</html>