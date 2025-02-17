package edu.jsu.mcis.cs310.coursedb.dao;


import java.sql.*;
import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

public class DAOUtility {

    public static String getResultSetAsJson(ResultSet rs) throws SQLException {
        JsonArray jsonArray = new JsonArray();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (rs.next()) {
            JsonObject obj = new JsonObject();
            for (int i = 1; i <= columnCount; i++) {
                obj.put(metaData.getColumnName(i), rs.getObject(i)); // Fix: use .set()
            }
            jsonArray.add(obj);
        }

        return Jsoner.serialize(jsonArray); // Fix: use Jsoner.serialize()
    }
}


