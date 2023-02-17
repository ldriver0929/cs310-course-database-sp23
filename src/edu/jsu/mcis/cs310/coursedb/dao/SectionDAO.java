package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

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
                    JsonObject jsonObject = new JsonObject();
                    
                    
 
    

                    for (int i = 1; i <= numColumns; i++) {
                        String columnName = rsmd.getColumnName(i);
                        Object columnValue = rs.getObject(i);
                        
                             if (columnValue instanceof Time) {
                            // Convert Time to String
                            Time timeValue = (Time) columnValue;
                            columnValue = timeValue.toString();
                             }
                          /* 
                     if (columnName.equals("id")) {
                        jsonObject.put("termid", columnValue);
                 
                    } else if (columnName.equals("num")) {
                        jsonObject.put("num", columnValue);
                    } else if (columnName.equals("scheduletypeid")) {
                        jsonObject.put("scheduletypeid", columnValue);
                    } else if (columnName.equals("section")) {
                        jsonObject.put("section", columnValue);
                    } else if (columnName.equals("days")) {
                        jsonObject.put("days", columnValue);
                    } else if (columnName.equals("start")) {
                        jsonObject.put("start", columnValue);
                    } else if (columnName.equals("end")) {
                        jsonObject.put("end", columnValue);
                    } else if (columnName.equals("where")) {
                        jsonObject.put("where", columnValue);
                    } else if (columnName.equals("instructor")) {
                        jsonObject.put("instructor", columnValue);
                    } 
                      else if (columnName.equals("crn")) {
                          
                        jsonObject.put("crn", columnValue);
                       
                    }
                       else if (columnName.equals("subjectid")) {
                        jsonObject.put("subjectid", columnValue);
                    } */
                                                }
                    
                         jsonObject.put("termid", rs.getObject("termid", Integer.class));
                         jsonObject.put("scheduletypeid", rs.getObject("scheduletypeid", String.class));
                         jsonObject.put("instructor", rs.getObject("instructor",String.class));
                         jsonObject.put("num", rs.getObject("num",Integer.class));
                             jsonObject.put("start", rs.getObject("start", String.class));
    jsonObject.put("days", rs.getObject("days", String.class));
    jsonObject.put("section", rs.getObject("section", String.class));
    jsonObject.put("end", rs.getObject("end", String.class));
    jsonObject.put("where", rs.getObject("where", String.class));
    jsonObject.put("crn", rs.getObject("crn", Integer.class));
   jsonObject.put("subjectid", rs.getObject("subjectid", String.class));                   
                    resultList.add(jsonObject);
                }
                
                JsonArray jsonArray = new JsonArray(resultList);
               
                result = Jsoner.serialize(jsonArray);
             
            }
            
        }
        
        catch (SQLException e) { e.printStackTrace(); }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (SQLException e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (SQLException e) { e.printStackTrace(); } }
            
        }
        
        return result.trim();
        
    }
    
}
