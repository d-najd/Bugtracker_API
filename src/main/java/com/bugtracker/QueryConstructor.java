package com.bugtracker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class QueryConstructor {
	private static final String dbLocation = WebConfiguration.dbLocation;
	private static final String dbUname = WebConfiguration.dbUname;
	private static final String dbPass = WebConfiguration.dbPass;
	
	public static void sendQuery (String query) throws SQLException {
        ArrayList<String> queries = new ArrayList<String>(List.of(query));
        try {
        	sendQuery(queries);
        } catch (SQLException e){
        	throw e;
        }
	}
	
	public static void sendQuery (ArrayList<String> queries) throws SQLException{
    	for (String query : queries) {
    		try (Connection connection = DriverManager.getConnection(dbLocation, dbUname, dbPass);
    		PreparedStatement preparedStatement = connection.prepareStatement(query)) {
    			preparedStatement.executeUpdate();
		        preparedStatement.close();
		    } catch (SQLException e) {
		        System.out.print("\nERROR with sending the query to the database,"
		        		+ " query is:\n" + query + "\n");
		        throw e;
		   }
    	}
	}
	
	/**
	 * 
	 * @param query the input query
	 * @return integer size of the query size
	 * @apiNote use integers which are the values between 0-9 because bigger wont work, also doens't account if there is - before the integer 
	 * @throws SQLException
	 */

    
    /*
     *         final String INSERT_USERS_SQL = "INSERT INTO board_tasks" +
                "  (id, position, title, description, date_created) VALUES " +
                " (?, ?, ?, ?, ?);";
                
            preparedStatement.setInt(1, 5);
            preparedStatement.setString(2, "customquery");
     * 
     */
	
}
