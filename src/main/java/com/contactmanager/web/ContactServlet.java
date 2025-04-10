package com.contactmanager.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.contactmanager.model.Contact;
import com.contactmanager.service.ContactService;
import com.contactmanager.service.ContactServiceImpl;

@WebServlet("/contacts/*")
public class ContactServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private ContactService contactService;
    
    public ContactServlet() {
        super();
        contactService = new ContactServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        
        if (action == null) {
            action = "/list";
        }
        
        switch (action) {
            case "/new":
                showNewForm(request, response);
                break;
            case "/edit":
                showEditForm(request, response);
                break;
            case "/delete":
                deleteContact(request, response);
                break;
            case "/view":
                viewContact(request, response);
                break;
            case "/search":
                searchContacts(request, response);
                break;
            case "/list":
            default:
                listContacts(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        
        if (action == null) {
            action = "/list";
        }
        
        switch (action) {
            case "/save":
                saveContact(request, response);
                break;
            case "/update":
                updateContact(request, response);
                break;
            default:
                listContacts(request, response);
                break;
        }
    }
    
    private void listContacts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Contact> contacts = contactService.getAllContacts();
        request.setAttribute("contacts", contacts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/list.jsp");
        dispatcher.forward(request, response);
    }
    
    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/form.jsp");
        dispatcher.forward(request, response);
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            Contact contact = contactService.getContactById(id);
            
            if (contact == null) {
                response.sendRedirect(request.getContextPath() + "/contacts/list");
                return;
            }
            
            request.setAttribute("contact", contact);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/form.jsp");
            dispatcher.forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/contacts/list");
        }
    }
    
    private void viewContact(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            Contact contact = contactService.getContactById(id);
            
            if (contact == null) {
                response.sendRedirect(request.getContextPath() + "/contacts/list");
                return;
            }
            
            request.setAttribute("contact", contact);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/details.jsp");
            dispatcher.forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/contacts/list");
        }
    }
    
    private void saveContact(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String email = request.getParameter("email");
            String telephone = request.getParameter("telephone");
            String adresse = request.getParameter("adresse");
            
            Contact contact = new Contact(nom, prenom, email, telephone, adresse);
            contactService.addContact(contact);
            
            response.sendRedirect(request.getContextPath() + "/contacts/list");
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/form.jsp");
            dispatcher.forward(request, response);
        }
    }
    
    private void updateContact(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String email = request.getParameter("email");
            String telephone = request.getParameter("telephone");
            String adresse = request.getParameter("adresse");
            
            Contact contact = new Contact(id, nom, prenom, email, telephone, adresse);
            contactService.updateContact(contact);
            
            response.sendRedirect(request.getContextPath() + "/contacts/list");
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/form.jsp");
            dispatcher.forward(request, response);
        }
    }
    
    private void deleteContact(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            contactService.deleteContact(id);
            
            response.sendRedirect(request.getContextPath() + "/contacts/list");
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            listContacts(request, response);
        }
    }
    
    private void searchContacts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<Contact> contacts = contactService.searchContacts(keyword);
        
        request.setAttribute("contacts", contacts);
        request.setAttribute("keyword", keyword);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/list.jsp");
        dispatcher.forward(request, response);
    }
}