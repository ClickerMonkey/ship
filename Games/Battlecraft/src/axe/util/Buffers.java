package axe.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Buffers 
{
	
	public static IntBuffer ints(int count) 
	{
		return ByteBuffer.allocateDirect(count << 2).order(ByteOrder.nativeOrder()).asIntBuffer();
	}
	
	public static IntBuffer ints(int ... values) 
	{
		return (IntBuffer)ints(values.length).put(values).flip();
	}
	
	public static FloatBuffer floats(int count) 
	{
		return ByteBuffer.allocateDirect(count << 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
	}
	
	public static FloatBuffer floats(float ... values) 
	{
		return (FloatBuffer)floats(values.length).put(values).flip();
	}
	
	public static DoubleBuffer doubles(int count) 
	{
		return ByteBuffer.allocateDirect(count << 3).order(ByteOrder.nativeOrder()).asDoubleBuffer();
	}
	
	public static DoubleBuffer doubles(double ... values) 
	{
		return (DoubleBuffer)doubles(values.length).put(values).flip();
	}
	
}