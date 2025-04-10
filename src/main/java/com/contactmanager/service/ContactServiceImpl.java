package com.contactmanager.service;

import java.util.List;

import com.contactmanager.dao.ContactDAO;
import com.contactmanager.dao.ContactDAOImpl;
import com.contactmanager.model.Contact;

public class ContactServiceImpl implements ContactService {
    
    private ContactDAO contactDAO;
    
    public ContactServiceImpl() {
        this.contactDAO = new ContactDAOImpl();
    }

    @Override
    public Long addContact(Contact contact) {
       
        if (contact.getNom() == null || contact.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom du contact ne peut pas être vide");
        }
        if (contact.getPrenom() == null || contact.getPrenom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le prénom du contact ne peut pas être vide");
        }
        return contactDAO.save(contact);
    }

    @Override
    public boolean updateContact(Contact contact) {
        
        if (contact.getId() == null || contactDAO.findById(contact.getId()) == null) {
            throw new IllegalArgumentException("Le contact à mettre à jour n'existe pas");
        }
        return contactDAO.update(contact);
    }

    @Override
    public boolean deleteContact(Long id) {
        
        if (id == null || contactDAO.findById(id) == null) {
            throw new IllegalArgumentException("Le contact à supprimer n'existe pas");
        }
        return contactDAO.delete(id);
    }

    @Override
    public Contact getContactById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID du contact ne peut pas être null");
        }
        return contactDAO.findById(id);
    }

    @Override
    public List<Contact> getAllContacts() {
        return contactDAO.findAll();
    }

    @Override
    public List<Contact> searchContacts(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllContacts();
        }
        return contactDAO.search(keyword);
    }
}