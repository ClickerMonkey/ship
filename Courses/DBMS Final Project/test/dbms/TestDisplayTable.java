package dbms;

import java.util.GregorianCalendar;


public class TestDisplayTable 
{

	public static void main(String[] args) {
		DisplayTable table = new DisplayTable(3);
		table.setHeader(0, "id");
		table.setHeader(1, "name");
		table.setHeader(2, "date");

		table.addRow();
		table.set(0, 1);
		table.set(1, "tom");
		table.set(2, new GregorianCalendar(2010, 3, 21));
		
		table.addRow();
		table.set(0, 4);
		table.set(1, "phil");
		table.set(2, new GregorianCalendar(2010, 3, 21));
		
		table.addRow();
		table.set(0, 8);
		table.set(1, "matt");
		table.set(2, new GregorianCalendar(2010, 4, 5));
		
		table.display();
	}
	
}
