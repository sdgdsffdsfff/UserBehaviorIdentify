import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class testsqlite {
	public static void main(String[] args) throws ClassNotFoundException{
		//load the sqlite-JDBC driver using the current class loader
		Class.forName("org.sqlite.JDBC");
		
		Connection connection=null;
		try{
			//create a database connection
			connection=DriverManager.getConnection("jdbc:sqlite:c:/temp/1.db");
			Statement stmt=connection.createStatement();
			stmt.execute("create table person (name varchar(100),age int)");
			stmt.execute("insert into person values('steve',25)");
			ResultSet result = stmt.executeQuery("select * from person");
			while(result.next()){
			     System.out.println(result.getString(1));
			     System.out.println(result.getInt(2));
			}
		}catch(SQLException e){
			//if the error message is "out of memory"
			//it probably means no database file is found
			System.err.println(e.getMessage());
		}finally{
			try{
				if(connection !=null){
					connection.close();
				}
			}catch(SQLException e){
				//connection close failed
				System.err.println(e);
			}
		}
		
	}
}