package simulations;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Set;

import net.philsprojects.stat.StatArchive;
import net.philsprojects.stat.StatDatabase;
import net.philsprojects.stat.StatFormat;
import net.philsprojects.stat.StatGroup;
import net.philsprojects.stat.StatPoint;
import net.philsprojects.stat.StatTarget;

/**
 * Handles statistics for simulations.
 * 
 * @author Philip Diffenderfer
 *
 */
public class Stat 
{
	
	// The base directory (stats)
	public static final StatGroup base;
	
	// The server stats directory (stats/server)
	public static final StatGroup server;
	
	// The client stats directory (stats/client)
	public static final StatGroup client;
	
	// The format of the stat databases.
	public static final StatFormat format;
	
	
	/**
	 * Initializes statistics directories.
	 */
	static 
	{
		base = new StatGroup("stats");
		server = base.getChild("server");
		client = base.getChild("client");
		
		format = new StatFormat(5);
		format.set(0, 100, 20);		//  2s of stats every 100ms
		format.set(1, 500, 30);		// 15s of stats every 500ms
		format.set(2, 1000, 30);	// 30s of stats every 1s
		format.set(3, 5000, 24);	//  2m of stats every 5s
		format.set(4, 15000, 20);	//  5m of stats every 15s
		format.compile();
		
		base.setTargetDefault(StatTarget.All);
		base.setFormatDefault(format);
		base.setEnableDefault(true);
	}
	
	/**
	 * Exports all databases that exist in the base group.
	 */
	public static void exportAll() throws IOException
	{
		Set<StatDatabase> dbs = base.getDatabases();
		for (StatDatabase db : dbs) {
			export(db, new File(db.getName() + ".csv"));
		}
	}
	
	/**
	 * Exports database data to a spreadsheet readable format (CSV).
	 * 
	 * @param db
	 * 		The database to export.
	 * @param file
	 * 		The file to write the exported data to.
	 * @throws IOException
	 */
	public static void export(StatDatabase db, File file) throws IOException 
	{
		PrintStream out = new PrintStream(file);
		
		// archive index,,,,,
		// index, total, avg, sum, min, max
		// ,,,,,
		
		for (StatArchive sa : db) {
			out.format("Archive #%d,,,,,\n", sa.getIndex());
			out.println("index,total,avg,sum,min,max");
			int index = 0;
			for (StatPoint sp : sa) {
				out.format("%d,%d,%f,%f,%f,%f\n", index++, sp.getTotal(), sp.getAverage(), sp.getSum(), sp.getMin(), sp.getMax());
			}
			out.println(",,,,,");
		}
		
		out.close();
	}
	
	
}
