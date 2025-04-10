package com.contactmanager.service;

import java.util.List;
import com.contactmanager.model.Contact;

public interface ContactService {
    /**
     * Ajoute un nouveau contact
     * @param contact Le contact à ajouter
     * @return L'ID du contact ajouté
     */
    Long addContact(Contact contact);
    
    /**
     * Met à jour un contact existant
     * @param contact Le contact à mettre à jour
     * @return true si la mise à jour a réussi
     */
    boolean updateContact(Contact contact);
    
    /**
     * Supprime un contact par son ID
     * @param id L'ID du contact à supprimer
     * @return true si la suppression a réussi
     */
    boolean deleteContact(Long id);
    
    /**
     * Trouve un contact par son ID
     * @param id L'ID du contact à trouver
     * @return Le contact trouvé ou null
     */
    Contact getContactById(Long id);
    
    /**
     * Trouve tous les contacts
     * @return La liste de tous les contacts
     */
    List<Contact> getAllContacts();
    
    /**
     * Recherche des contacts par nom ou prénom
     * @param keyword Le mot-clé de recherche
     * @return La liste des contacts correspondants
     */
    List<Contact> searchContacts(String keyword);
}