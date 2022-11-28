package main.java.ethos.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminDal {

    private List<String> columnNamesFromQuery;

    public AdminDal() {
        this.columnNamesFromQuery = new ArrayList<String>();
    }
    
    public Map<String, Object> submitQuery(String queryString) throws SQLException {
        Map<String, Object> executionResult = new HashMap<String, Object>();       
        executionResult.put("result set", null);
        executionResult.put("rows affected", Integer.MIN_VALUE);       
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
                PreparedStatement stmt = connection.prepareStatement(queryString)) {
            boolean querySuccess = stmt.execute();
            if (querySuccess) {
                executionResult.put("result set", this.prepareResults(stmt.getResultSet()));
                executionResult.put("columns", this.columnNamesFromQuery);
            } 
            executionResult.put("rows affected", stmt.getUpdateCount());
            
        }
        return executionResult;
    }

    private List<Map<String, String>> prepareResults(ResultSet rs) throws SQLException {   
        ArrayList<String> columns = new ArrayList<String>();
        for (int index = 1; index <= rs.getMetaData().getColumnCount(); index++) {
            columns.add(rs.getMetaData().getColumnName(index));
        }
        List<Map<String, String>> rowData = new ArrayList<Map<String, String>>();
        while (rs.next()) {
            Map<String, String> data = new HashMap<String, String>();
            for (int colNum = 0; colNum < columns.size(); colNum++) {
                data.put(columns.get(colNum), String.valueOf(rs.getObject(colNum + 1)));
            }
            rowData.add(data);
        }
        this.columnNamesFromQuery = columns;
        return rowData;
    }

    public List<String> getColumnNames() {
        return this.columnNamesFromQuery;
    }
}
