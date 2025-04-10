<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${empty contact ? 'Ajouter' : 'Modifier'} un Contact</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>${empty contact ? 'Ajouter' : 'Modifier'} un Contact</h1>
            <div class="header-actions">
                <a href="${pageContext.request.contextPath}/contacts/list" class="btn secondary-btn">Retour à la liste</a>
            </div>
        </header>
        
        <c:if test="${not empty error}">
            <div class="error-message">
                <p>${error}</p>
            </div>
        </c:if>
        
        <div class="form-container">
            <form action="${pageContext.request.contextPath}/contacts/${empty contact ? 'save' : 'update'}" method="post">
                <c:if test="${not empty contact}">
                    <input type="hidden" name="id" value="${contact.id}">
                </c:if>
                
                <div class="form-group">
                    <label for="nom">Nom *</label>
                    <input type="text" id="nom" name="nom" value="${contact.nom}" required>
                </div>
                
                <div class="form-group">
                    <label for="prenom">Prénom *</label>
                    <input type="text" id="prenom" name="prenom" value="${contact.prenom}" required>
                </div>
                
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" value="${contact.email}">
                </div>
                
                <div class="form-group">
                    <label for="telephone">Téléphone</label>
                    <input type="tel" id="telephone" name="telephone" value="${contact.telephone}">
                </div>
                
                <div class="form-group">
                    <label for="adresse">Adresse</label>
                    <textarea id="adresse" name="adresse" rows="3">${contact.adresse}</textarea>
                </div>
                
                <div class="form-actions">
                    <button type="submit" class="btn primary-btn">Enregistrer</button>
                    <a href="${pageContext.request.contextPath}/contacts/list" class="btn secondary-btn">Annuler</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>