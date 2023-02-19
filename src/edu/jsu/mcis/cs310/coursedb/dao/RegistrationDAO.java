package edu.jsu.mcis.cs310.coursedb.dao;

import com.github.cliftonlabs.json_simple.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class RegistrationDAO {

    private final DAOFactory daoFactory;

    public RegistrationDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public boolean create(int studentid, int termid, int crn) {
        boolean result = false;
        PreparedStatement ps = null;

        try {
            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                String query = "INSERT INTO registration (studentid, termid, crn) SELECT s.id, t.id, sec.crn FROM student s, term t, section sec WHERE s.id = ? AND t.id = ? AND sec.crn = ?";

                ps = conn.prepareStatement(query);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                ps.setInt(3, crn);
                int rows = ps.executeUpdate();

                if (rows > 0) {
                    result = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public boolean delete(int studentid, int termid, int crn) {

        boolean result = false;

        PreparedStatement ps = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                String query = "DELETE FROM registration WHERE studentid = ? AND termid = ? AND crn = ?";
                ps = conn.prepareStatement(query);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                ps.setInt(3, crn);
                int rows = ps.executeUpdate();
                if (rows > 0) {
                    result = true;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        return result;
    }

    public boolean delete(int studentid, int termid) {
        boolean result = false;
        PreparedStatement ps = null;

        try {
            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {
                String query = "DELETE FROM registration WHERE studentid = ? AND termid = ?";
                ps = conn.prepareStatement(query);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                int rows = ps.executeUpdate();

                if (rows > 0) {
                    result = true;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    public String list(int studentid, int termid) {

        JsonArray jsonArray = new JsonArray();
        String result = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;

        try {
            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {
                String query = "SELECT studentid, termid, crn FROM registration WHERE studentid = ? AND termid = ? ORDER BY registration.crn";
                ps = conn.prepareStatement(query);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                rs = ps.executeQuery();

                rsmd = rs.getMetaData();
                result = DAOUtility.getResultSetAsJson(rs, false);
                /*
                while (rs.next()) {
                    JsonObject obj = new JsonObject();
                    int numColumns = rsmd.getColumnCount();
                    for (int i = 1; i <= numColumns; i++) {
                        String column_name = rsmd.getColumnName(i);
                        obj.put(column_name, rs.getObject(column_name));
                    }
                    jsonArray.add(obj);
                    result = jsonArray.toJson();
                }*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return result.trim();
    }
}
