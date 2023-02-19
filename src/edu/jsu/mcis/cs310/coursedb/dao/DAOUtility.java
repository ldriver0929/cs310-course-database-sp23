package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.*;
import com.github.cliftonlabs.json_simple.*;
import java.util.ArrayList;

public class DAOUtility {

    public static final int TERMID_SP23 = 1;

    public static String getResultSetAsJson(ResultSet rs, boolean isSectionDAO) {

        JsonArray records = new JsonArray();

        try {

            if (rs != null) {

                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                while (rs.next()) {
                    JsonObject jsonObject = new JsonObject();

                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        Object columnValue = rs.getObject(i);

                        if (columnValue instanceof Time) {
                            Time timeValue = (Time) columnValue;
                            columnValue = timeValue.toString();

                        }

                        if (isSectionDAO) {
                            jsonObject.put("termid", rs.getObject("termid", Integer.class));
                            jsonObject.put("scheduletypeid", rs.getObject("scheduletypeid", String.class));
                            jsonObject.put("instructor", rs.getObject("instructor", String.class));
                            jsonObject.put("num", rs.getObject("num", Integer.class));
                            jsonObject.put("start", rs.getObject("start", String.class));
                            jsonObject.put("days", rs.getObject("days", String.class));
                            jsonObject.put("section", rs.getObject("section", String.class));
                            jsonObject.put("end", rs.getObject("end", String.class));
                            jsonObject.put("where", rs.getObject("where", String.class));
                            jsonObject.put("crn", rs.getObject("crn", Integer.class));
                            jsonObject.put("subjectid", rs.getObject("subjectid", String.class));

                        }
                        if (!isSectionDAO) {

                            jsonObject.put(columnName, columnValue);
                        }
                    }
                    records.add(jsonObject);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Jsoner.serialize(records);

    }

}
