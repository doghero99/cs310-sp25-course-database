package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class RegistrationDAO {
    
    private final DAOFactory daoFactory;
    
    RegistrationDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public boolean create(int studentid, int termid, int crn) {
        boolean result = false;
        PreparedStatement ps = null;
        
        try {
            Connection conn = daoFactory.getConnection();
            if (conn != null && conn.isValid(0)) { // Ensure connection is valid
                String query = "INSERT INTO registration (studentid, termid, crn) VALUES (?, ?, ?)";
                ps = conn.prepareStatement(query);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                ps.setInt(3, crn);
                result = (ps.executeUpdate() > 0);
            }
        } catch (Exception e) {
            System.err.println("Error in RegistrationDAO.create(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
        }
        return result;
    }

    public boolean delete(int studentid, int termid, int crn) {
        boolean result = false;
        PreparedStatement ps = null;
        
        try {
            Connection conn = daoFactory.getConnection();
            if (conn != null && conn.isValid(0)) {
                String query = "DELETE FROM registration WHERE studentid = ? AND termid = ? AND crn = ?";
                ps = conn.prepareStatement(query);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                ps.setInt(3, crn);
                result = (ps.executeUpdate() > 0);
            }
        } catch (Exception e) {
            System.err.println("Error in RegistrationDAO.delete(single course): " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
        }
        return result;
    }
    
    public boolean delete(int studentid, int termid) {
        boolean result = false;
        PreparedStatement ps = null;
        
        try {
            Connection conn = daoFactory.getConnection();
            if (conn != null && conn.isValid(0)) {
                String query = "DELETE FROM registration WHERE studentid = ? AND termid = ?";
                ps = conn.prepareStatement(query);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                result = (ps.executeUpdate() > 0);
            }
        } catch (Exception e) {
            System.err.println("Error in RegistrationDAO.delete(all courses): " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
        }
        return result;
    }

    public String list(int studentid, int termid) {
        String result = "[]"; // Default to empty JSON array
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            Connection conn = daoFactory.getConnection();
            if (conn != null && conn.isValid(0)) {
                String query = "SELECT * FROM registration WHERE studentid = ? AND termid = ? ORDER BY crn";
                ps = conn.prepareStatement(query);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                rs = ps.executeQuery();
                result = DAOUtility.getResultSetAsJson(rs);
            }
        } catch (Exception e) {
            System.err.println("Error in RegistrationDAO.list(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
        }
        return result;
    }
}
