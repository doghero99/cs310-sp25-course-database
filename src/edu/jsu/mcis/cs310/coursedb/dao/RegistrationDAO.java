package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegistrationDAO {
    
    private final DAOFactory daoFactory;
    
    RegistrationDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    // Registers a single course for a student.
    public boolean create(int studentid, int termid, int crn) {
        boolean result = false;
        PreparedStatement ps = null;
        try {
            Connection conn = daoFactory.getConnection();
            if (conn != null && conn.isValid(0)) {
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
            if (ps != null) { 
                try { ps.close(); } catch (Exception ex) { ex.printStackTrace(); }
            }
        }
        return result;
    }
    
    // Deletes a registration for a single course.
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
            if (ps != null) { 
                try { ps.close(); } catch (Exception ex) { ex.printStackTrace(); }
            }
        }
        return result;
    }
    
    // Deletes all registrations for a student for a given term.
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
            if (ps != null) {
                try { ps.close(); } catch (Exception ex) { ex.printStackTrace(); }
            }
        }
        return result;
    }
    
    // Lists all registered courses for a student in a given term.
    public String list(int studentid, int termid) {
        String result = "[]";
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
            e.printStackTrace();
        } finally {
            if (rs != null) { 
                try { rs.close(); } catch (Exception ex) { ex.printStackTrace(); }
            }
            if (ps != null) {
                try { ps.close(); } catch (Exception ex) { ex.printStackTrace(); }
            }
        }
        return result;
    }
}
