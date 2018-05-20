package net.philsprojects.log;

import java.io.PrintStream;

public class Log 
{

	public static final int None 		= 1 >> 1;
	public static final int Error 		= 1 << 0;
	public static final int Warning 	= 1 << 1;
	public static final int Debug 		= 1 << 2;
	public static final int Info 		= 1 << 3;
	public static final int Suggestion 	= 1 << 4;
	
	public static final int All			= 0xffffffff;
	
	
	private static final String[] NAMES = {
		"None", "Error", "Warning", "Debug", "Info", "Suggestion", "", "",
		"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""
	};
	
	
	private static int level = None;
	
	private static PrintStream out = System.out;
	
	
	public static void enable(int x) {
		level |= x;
	}
	
	public static void disable(int x) {
		level &= ~x;
	}
	
	public static void set(int x) {
		level = x;
	}
	
	public static boolean isLog(int x) {
		return (x & level) != 0;
	}
	
	public static boolean isLogNone() {
		return isLog(None);
	}
	
	public static boolean isLogError() {
		return isLog(Error);
	}
	
	public static boolean isLogWarning() {
		return isLog(Warning);
	}
	
	public static boolean isLogDebug() {
		return isLog(Debug);
	}
	
	public static boolean isLogInfo() {
		return isLog(Info);
	}
	
	public static boolean isSuggestion() {
		return isLog(Suggestion);
	}
	
	public static void log(int x, String message) {
		out.print(NAMES[log2(x)]);
		out.println(message);
	}
	
	public static void log(int x, String format, Object ... args) {
		out.print(NAMES[log2(x)]);
		out.format(format, args);
	}
	
	public static void setPrintStream(PrintStream printStream) {
		out = printStream;
	}
	
	public static void register(int x, String name) {
		NAMES[x] = name;
	}
	
	private static int log2(int x) {
		return 31 - Integer.numberOfLeadingZeros(x);
	}
	                                
	                               
	
}
