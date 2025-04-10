<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Détails du Contact</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>Détails du Contact</h1>
            <div class="header-actions">
                <a href="${pageContext.request.contextPath}/contacts/list" class="btn secondary-btn">Retour à la liste</a>
            </div>
        </header>
        
        <div class="contact-details">
            <div class="detail-row">
                <div class="detail-label">ID:</div>
                <div class="detail-value">${contact.id}</div>
            </div>
            <div class="detail-row">
                <div class="detail-label">Nom:</div>
                <div class="detail-value">${contact.nom}</div>
            </div>
            <div class="detail-row">
                <div class="detail-label">Prénom:</div>
                <div class="detail-value">${contact.prenom}</div>
            </div>
            <div class="detail-row">
                <div class="detail-label">Email:</div>
                <div class="detail-value">${contact.email}</div>
            </div>
            <div class="detail-row">
                <div class="detail-label">Téléphone:</div>
                <div class="detail-value">${contact.telephone}</div>
            </div>
            <div class="detail-row">
                <div class="detail-label">Adresse:</div>
                <div class="detail-value">${contact.adresse}</div>
            </div>
        </div>
        
        <div class="action-buttons">
            <a href="${pageContext.request.contextPath}/contacts/edit?id=${contact.id}" class="btn warning-btn">Modifier</a>
            <a href="${pageContext.request.contextPath}/contacts/delete?id=${contact.id}" class="btn danger-btn" onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce contact?')">Supprimer</a>
        </div>
    </div>
</body>
</html>