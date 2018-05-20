package net.philsprojects.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;


public class Files 
{

	/**
	 * 
	 * @param src
	 * @param dst
	 * @throws IOException
	 */
	public static void copy(File src, File dst) throws IOException 
	{
		copy(src, dst, new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return true;
			}
		});
	}
	
	/**
	 * 
	 * @param src
	 * @param dst
	 * @param filter
	 * @throws IOException
	 */
	public static void copy(File src, File dst, FilenameFilter filter) throws IOException 
	{
		if (src.isFile()) {
			if (dst.isFile()) {
				copyFile(src, dst);
			}
			else {
				copyTo(src, dst);
			}
		}
		else {
			dst.mkdirs();
			
			for (File f : src.listFiles(filter)) {
				if (f.isDirectory()) {
					copy(f, new File(dst, f.getName()));
				}
				else {
					copyTo(f, dst);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param src
	 * @param dst
	 * @throws IOException
	 */
	private static void copyFile(File src, File dst) throws IOException 
	{
		if (!dst.isFile()) {
			dst.createNewFile();
		}
		
		FileChannel in = new RandomAccessFile(src, "r").getChannel();
		FileChannel out = new RandomAccessFile(dst, "rw").getChannel();
		in.transferTo(0, in.size(), out);
		in.close();
		out.close();
	}
	
	/**
	 * 
	 * @param src
	 * @param dstDir
	 * @throws IOException
	 */
	private static void copyTo(File src, File dstDir) throws IOException 
	{
		copyFile(src, new File(dstDir, src.getName()));
	}

}
