package JDBC_Demo;
import java.sql.*;

import javax.swing.JOptionPane;
public class JDBC_Connection {
	static Connection con;
	public static Connection make_con() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","Kashad","Kashad");
			JOptionPane.showMessageDialog(null, "DataBase Connected");
		}
		catch(Exception e1){
			JOptionPane.showMessageDialog(null, "Error in DataBase Connection");

		}
		return con;
		}
 public static void main(String args[])
 {
	 JDBC_Connection.make_con();
	 
 }
}