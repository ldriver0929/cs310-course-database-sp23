package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;

public class SectionDAO {
    
    private final DAOFactory daoFactory;
    
    public SectionDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    public String find(int termid, String subjectid, String num) {
        
        String result = null;
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                //term id comes from term table, subject id and num ocmes from course table,
                //String query = "SELECT * FROM course WHERE id=? AND subjectid=? AND num=? ORDER BY crn";
                 //String query = "SELECT term.id, course.*, section.* FROM term, course, section WHERE term.id=? AND course.subjectid=? AND course.num=? ORDER BY section.crn";
              String query = "SELECT term.id, course.*, section.* FROM term, course, section WHERE term.id=? AND course.subjectid=? AND course.num=? AND course.num = section.num AND course.subjectid = section.subjectid ORDER BY section.crn";

                ps = conn.prepareStatement(query);
                ps.setInt(1, termid);
                ps.setString(2, subjectid);
                ps.setString(3, num);
                rs = ps.executeQuery();
                
                rsmd = rs.getMetaData();
                int numColumns = rsmd.getColumnCount();
                List<JsonObject> resultList = new ArrayList<>();
                
                while (rs.next()) {
                    JsonObject sectionObject = new JsonObject();
                    
                    
                    
                    for (int i = 1; i <= numColumns; i++) {
                        String columnName = rsmd.getColumnName(i);
                        Object columnValue = rs.getObject(i);
                        
                             if (columnValue instanceof Time) {
                            // Convert Time to String
                            Time timeValue = (Time) columnValue;
                            columnValue = timeValue.toString();
                             }
                        sectionObject.put(columnName, columnValue);
                    }
                    resultList.add(sectionObject);
                }
                
                JsonArray jsonArray = new JsonArray(resultList);
                result = jsonArray.toJson();
            }
            
        }
        
        catch (SQLException e) { e.printStackTrace(); }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (SQLException e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (SQLException e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }
    
}
