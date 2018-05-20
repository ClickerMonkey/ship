package dbms;

public class TestDatabase 
{

	public static void main(String[] args) {
		Database db = Database.get();
		
		if (db.getConnection() == null) {
			System.out.println("Error connecting");
		}
		else {
			System.out.println("Successful connection");
		}
		
		db.disconnect();
	}
	
}
