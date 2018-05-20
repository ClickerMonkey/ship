package ass2;

/**
 * A list of the standardized Cyclic Redundancy Checks. Each one has a unique
 * table used for calculating a CRC given byte data.
 * 
 * @source http://en.wikipedia.org/wiki/Cyclic_redundancy_check#Commonly_used_and_standardized_CRCs
 * 
 * @author Philip Diffenderfer
 *
 */
public class CRC extends AbstractChecksum 
{
	
	// x + 1 (most hardware; also known as parity bit)
	public static final CRC _1 = new CRC(0x1);
	
	// x4 + x + 1 (ITU-T G.704, p. 12)
	public static final CRC _4_ITU = new CRC(0xC);
	
	// x5 + x3 + 1 (Gen 2 RFID)
	public static final CRC _5_EPC = new CRC(0x12);
	
	// x5 + x4 + x2 + 1 (ITU-T G.704, p. 9)
	public static final CRC _5_ITU = new CRC(0x15);	
	
	// x5 + x2 + 1 (USB token packets)
	public static final CRC _5_USB = new CRC(0x14);
	
	// x6 + x + 1 (ITU-T G.704, p. 3)
	public static final CRC _6_ITU = new CRC(0x30);
	
	// x7 + x3 + 1 (telecom systems, ITU-T G.707, ITU-T G.832, MMC, SD)
	public static final CRC _7 = new CRC(0x48);	
	
	// x8 + x2 + x + 1 (ATM HEC), ISDN Header Error Control and Cell Delineation ITU-T I.432.1 (02/99)
	public static final CRC _8_CCIT = new CRC(0xE0);
	
	// x8 + x5 + x4 + 1 (1-Wire bus)
	public static final CRC _8_DALLAS = new CRC(0x8C);	
	
	// x8 + x7 + x6 + x4 + x2 + 1
	public static final CRC _8 = new CRC(0xAB);
	
	// x8 + x4 + x3 + x2 + 1
	public static final CRC _8_SAE = new CRC(0xB8);	
	
	// x8 + x7 + x4 + x3 + x + 1
	public static final CRC _8_WCDMA = new CRC(0xD9);
	
	// x10 + x9 + x5 + x4 + x + 1 (ATM; ITU-T I.610)
	public static final CRC _10 = new CRC(0x331);
	
	// x11 + x9 + x8 + x7 + x2 + 1 (FlexRay[17])
	public static final CRC _11 = new CRC(0x50E);
	
	// x12 + x11 + x3 + x2 + x + 1 (telecom systems[18][19])
	public static final CRC _12 = new CRC(0xF01);
	
	// x15 + x14 + x10 + x8 + x7 + x4 + x3 + 1
	public static final CRC _15_CAN = new CRC(0x4CD1);
	
	// x16 + x15 + x2 + 1 (Bisync, Modbus, USB, ANSI X3.28, many others; also known as CRC-16 and CRC-16-ANSI)
	public static final CRC _16_IBM = new CRC(0xA001);
	
	// x16 + x12 + x5 + 1 (X.25, V.41, HDLC, XMODEM, Bluetooth, SD, many others; known as CRC-CCITT)
	public static final CRC _16_CCIT = new CRC(0x8408);
	
	// x16 + x15 + x11 + x9 + x8 + x7 + x5 + x4 + x2 + x + 1 (SCSI DIF)
	public static final CRC _16_T10 = new CRC(0xEDD1);
	
	// x16 + x13 + x12 + x11 + x10 + x8 + x6 + x5 + x2 + 1 (DNP, IEC 870, M-Bus)
	public static final CRC _16_DNP = new CRC(0xA6BC);		
	
	// x16 + x10 + x8 + x7 + x3 + 1 (cordless telephones)[21]
	public static final CRC _16_DECT = new CRC(0x91A0);	
	
	// x24 + x22 + x20 + x19 + x18 + x16 + x14 + x13 + x11 + x10 + x8 + x7 + x6 + x3 + x + 1 (FlexRay)
	public static final CRC _24 = new CRC(0xD3B6BA);
	
	// x24 + x23 + x18 + x17 + x14 + x11 + x10 + x7 + x6 + x5 + x4 + x3 + x + 1 (OpenPGP)
	public static final CRC _24_RADIX = new CRC(0xDF3261);
	
	// x30 + x29 + x21 + x20 + x15 + x13 + x12 + x11 + x8 + x7 + x6 + x2 + x + 1 (CDMA)
	public static final CRC _30 = new CRC(0x38E74301);
	
	// x32 + x26 + x23 + x22 + x16 + x12 + x11 + x10 + x8 + x7 + x5 + x4 + x2 + x + 1 (V.42, Ethernet, MPEG-2, PNG, POSIX cksum)
	public static final CRC _32_IEEE = new CRC(0xEDB88320);
	
	// x32 + x28 + x27 + x26 + x25 + x23 + x22 + x20 + x19 + x18 + x14 + x13 + x11 + x10 + x9 + x8 + x6 + 1 (Castagnoli: iSCSI & SCTP, G.hn payload, SSE4.2)
	public static final CRC _32C = new CRC(0x82F63B78);
	
	// x32 + x30 + x29 + x28 + x26 + x20 + x19 + x17 + x16 + x15 + x11 + x10 + x7 + x6 + x4 + x2 + x + 1 (Koopman)
	public static final CRC _32K	= new CRC(0xEB31D82E);
	
	// x32 + x31 + x24 + x22 + x16 + x14 + x8 + x7 + x5 + x3 + x + 1 (aviation; AIXM)
	public static final CRC _32Q = new CRC(0xD5828281);

	
	// The value used for calculating a look-up table.
	private final int reversed;
	
	// The look up table for this CRC algorithm.
	private int[] table;

	
	/**
	 * Instantiates a new CRC algorithm given the reversed representation of
	 * the polynomial. 
	 *  
	 * @param reversed
	 * 		The reversed representation of the algorithm's polynomial.
	 */
	public CRC(int reversed) 
	{
		this.reversed = reversed;
	}

	/**
	 * Returns the reversed representation of this algorithm's polynomial.
	 */
	public long getReversed() 
	{
		return reversed;
	}

	/**
	 * Returns the look-up table for this algorithm. If it has not been created
	 * then it is calculated. 
	 * 
	 * @return
	 * 		An array of 256 longs.
	 */
	public int[] getTable() 
	{
		// If the table does not exist...
		if (table == null) 
		{
			table = new int[256];
			// Generate the table...
			for (int i = 0; i < 256; i++) 
			{
				// Could just process in loop, this is faster though
				int x = i;
				// If x is even x = x/2
				// If x is odd x = x/2 	XOR reversed
				for (int k = 0; k < 8; k++) {
					x = (x >>> 1) ^ (reversed & -(x & 1));	
				}
				
				table[i] = x;
			}
		}
		return table;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getCode32(byte[] data, int off, int len) 
	{
		int[] table = getTable();
		int crc = ~0;
		while (--len >= 0) {
			crc = (crc >>> 8) ^ table[(crc ^ data[off++]) & 0xFF];
		}
		return ~crc & 0xFFFFFFFFL;
	}

}
