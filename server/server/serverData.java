package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class serverData {

	private static final int BUFFER_SIZE = 4096;
	
	/**
	 * Saves file to SQL database
	 * 
	 * @param file
	 */
	public static void saveToDatabase(File file) {

		try {

			Connection conn = DriverManager.getConnection(ServerSetting.url, ServerSetting.user,
					ServerSetting.password);

			String sql = "INSERT INTO saveData (data, timestamp) values (?, ?)";

			PreparedStatement statement = conn.prepareStatement(sql);

			InputStream inputStream = new FileInputStream(file);

			statement.setBlob(1, inputStream);
			statement.setDate(2, new Date(0));

			conn.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public static File readFromDatabase() 
	{
		File tmp = new File("");

		try {   
			
			Connection conn = DriverManager.getConnection(ServerSetting.url, ServerSetting.user,
					ServerSetting.password);

        String sql = "SELECT TOP 1 data FROM saveData";
        
        PreparedStatement statement = conn.prepareStatement(sql);       

        ResultSet result = statement.executeQuery();
        if (result.next()) 
        {
            Blob blob = result.getBlob("data");
            InputStream inputStream = blob.getBinaryStream();
            
            OutputStream outputStream = new FileOutputStream(tmp);

            int bytesRead = -1;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            inputStream.close();
            outputStream.close();
            
        }        
        conn.close();  
      
        
    } catch (SQLException ex) {
        ex.printStackTrace();
    } catch (IOException ex) {
        ex.printStackTrace();
    }
		
		return tmp;}

}
