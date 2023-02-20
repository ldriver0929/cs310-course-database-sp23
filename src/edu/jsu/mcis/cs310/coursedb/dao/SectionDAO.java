package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.*;

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

                String query = "SELECT term.id, course.*, section.* FROM term, course, section WHERE term.id=? AND course.subjectid=? AND course.num=? AND course.num = section.num AND course.subjectid = section.subjectid ORDER BY section.crn";

                ps = conn.prepareStatement(query);
                ps.setInt(1, termid);
                ps.setString(2, subjectid);
                ps.setString(3, num);
                rs = ps.executeQuery();

                result = DAOUtility.getResultSetAsJson(rs, true);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }

        return result.trim();

    }

}
