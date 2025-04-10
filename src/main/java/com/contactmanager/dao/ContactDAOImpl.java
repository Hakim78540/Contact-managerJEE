package com.contactmanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.contactmanager.model.Contact;
import com.contactmanager.util.DatabaseUtil;

public class ContactDAOImpl implements ContactDAO {

    @Override
    public Long save(Contact contact) {
        String sql = "INSERT INTO contacts (nom, prenom, email, telephone, adresse) VALUES (?, ?, ?, ?, ?)";
        Long id = null;
        
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseUtil.getConnection();
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            statement.setString(1, contact.getNom());
            statement.setString(2, contact.getPrenom());
            statement.setString(3, contact.getEmail());
            statement.setString(4, contact.getTelephone());
            statement.setString(5, contact.getAdresse());
            
            int affectedRows = statement.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("La création du contact a échoué, aucune ligne affectée.");
            }
            
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getLong(1);
                contact.setId(id);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la sauvegarde du contact : " + e.getMessage());
        } finally {
            closeResources(resultSet, statement, connection);
        }
        
        return id;
    }

    @Override
    public boolean update(Contact contact) {
        String sql = "UPDATE contacts SET nom = ?, prenom = ?, email = ?, telephone = ?, adresse = ? WHERE id = ?";
        boolean updated = false;
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = DatabaseUtil.getConnection();
            statement = connection.prepareStatement(sql);
            
            statement.setString(1, contact.getNom());
            statement.setString(2, contact.getPrenom());
            statement.setString(3, contact.getEmail());
            statement.setString(4, contact.getTelephone());
            statement.setString(5, contact.getAdresse());
            statement.setLong(6, contact.getId());
            
            int affectedRows = statement.executeUpdate();
            updated = affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du contact : " + e.getMessage());
        } finally {
            closeResources(null, statement, connection);
        }
        
        return updated;
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM contacts WHERE id = ?";
        boolean deleted = false;
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = DatabaseUtil.getConnection();
            statement = connection.prepareStatement(sql);
            
            statement.setLong(1, id);
            
            int affectedRows = statement.executeUpdate();
            deleted = affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du contact : " + e.getMessage());
        } finally {
            closeResources(null, statement, connection);
        }
        
        return deleted;
    }

    @Override
    public Contact findById(Long id) {
        String sql = "SELECT * FROM contacts WHERE id = ?";
        Contact contact = null;
        
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseUtil.getConnection();
            statement = connection.prepareStatement(sql);
            
            statement.setLong(1, id);
            
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                contact = extractContactFromResultSet(resultSet);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche du contact : " + e.getMessage());
        } finally {
            closeResources(resultSet, statement, connection);
        }
        
        return contact;
    }

    @Override
    public List<Contact> findAll() {
        String sql = "SELECT * FROM contacts ORDER BY nom, prenom";
        List<Contact> contacts = new ArrayList<>();
        
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseUtil.getConnection();
            statement = connection.prepareStatement(sql);
            
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                Contact contact = extractContactFromResultSet(resultSet);
                contacts.add(contact);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des contacts : " + e.getMessage());
        } finally {
            closeResources(resultSet, statement, connection);
        }
        
        return contacts;
    }

    @Override
    public List<Contact> search(String keyword) {
        String sql = "SELECT * FROM contacts WHERE nom LIKE ? OR prenom LIKE ? ORDER BY nom, prenom";
        List<Contact> contacts = new ArrayList<>();
        
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseUtil.getConnection();
            statement = connection.prepareStatement(sql);
            
            String searchKeyword = "%" + keyword + "%";
            statement.setString(1, searchKeyword);
            statement.setString(2, searchKeyword);
            
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                Contact contact = extractContactFromResultSet(resultSet);
                contacts.add(contact);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche des contacts : " + e.getMessage());
        } finally {
            closeResources(resultSet, statement, connection);
        }
        
        return contacts;
    }
    
    /**
     * Extrait un objet Contact à partir d'un ResultSet
     */
    private Contact extractContactFromResultSet(ResultSet resultSet) throws SQLException {
        Contact contact = new Contact();
        contact.setId(resultSet.getLong("id"));
        contact.setNom(resultSet.getString("nom"));
        contact.setPrenom(resultSet.getString("prenom"));
        contact.setEmail(resultSet.getString("email"));
        contact.setTelephone(resultSet.getString("telephone"));
        contact.setAdresse(resultSet.getString("adresse"));
        return contact;
    }
    
    /**
     * Ferme proprement les ressources JDBC
     */
    private void closeResources(ResultSet resultSet, PreparedStatement statement, Connection connection) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture du ResultSet : " + e.getMessage());
            }
        }
        
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture du Statement : " + e.getMessage());
            }
        }
        
        DatabaseUtil.closeConnection(connection);
    }
}