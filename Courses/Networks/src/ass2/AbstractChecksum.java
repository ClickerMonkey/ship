package ass2;

/**
 * An abstract checksum
 * 
 * @author Philip Diffenderfer
 *
 */
public abstract class AbstractChecksum implements Checksum 
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public short getCode8(byte[] data) 
	{
		return (short)getCode32(data, 0, data.length);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public short getCode8(byte[] data, int off, int len) 
	{
		return (short)getCode32(data, off, len);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getCode16(byte[] data) 
	{
		return (int)getCode32(data, 0, data.length);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getCode16(byte[] data, int off, int len) 
	{
		return (int)getCode32(data, off, len);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getCode32(byte[] data) 
	{
		return getCode32(data, 0, data.length);
	}

}
