package com.wmd.sandbox;

public class ForeignKey extends Column 
{

	private final Table<?> reference;
	private final int keyIndex;

	public ForeignKey(int index, String name, Table<?> reference, String keyName) {
		super(index, name, true);
		this.reference = reference;
		this.keyIndex = reference.getColumn(keyName).getIndex();
	}
	
	public ForeignKey(int index, String name, Table<?> reference, int keyIndex) {
		super(index, name, true);
		this.reference = reference;
		this.keyIndex = keyIndex;
	}
	
	public Table<?> getReference() {
		return reference;
	}
	
	public int getKeyIndex() {
		return keyIndex;
	}
	
}
