package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

public class DAOUtility {

    // Define the Fall 2024 term ID constant (as an int).
    public static final int TERMID_FA24 = 1;

    /**
     * Converts the provided ResultSet into a JSON array string.
     * All non-null values are converted to strings.
     */
    public static String getResultSetAsJson(ResultSet rs) throws Exception {
        JsonArray jsonArray = new JsonArray();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (rs.next()) {
            JsonObject obj = new JsonObject();
            // Iterate over each column in the current row
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                Object value = rs.getObject(i);
                // Convert value to String (even java.sql.Time will become a string)
                String valueStr = (value == null) ? "" : value.toString();
                obj.put(columnName, valueStr);
            }
            jsonArray.add(obj);
        }
        // Return the JSON array as a serialized string.
        return Jsoner.serialize(jsonArray);
    }
}
