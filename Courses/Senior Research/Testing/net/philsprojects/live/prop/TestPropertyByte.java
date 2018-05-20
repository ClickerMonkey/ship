package net.philsprojects.live.prop;

import static org.junit.Assert.*;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import net.philsprojects.BaseTest;
import net.philsprojects.live.Property;
import net.philsprojects.live.PropertyListener;
import net.philsprojects.live.PropertyValidator;
import net.philsprojects.live.prop.PropertyByte;

public class TestPropertyByte extends BaseTest 
{

	@Test
	public void testConstructors() 
	{
		PropertyByte p1 = new PropertyByte("p1", true);
		assertNull( p1.getValidator() );
		assertTrue( p1.isImmediate() );
		
		PropertyByte p2 = new PropertyByte("p2", false, new PropertyValidator<Byte>() {
			public boolean isValidValue(Property<Byte> property, Byte oldValue, Byte newValue) {
				return false;
			}
		});
		assertNotNull( p2.getValidator() );
		assertFalse( p2.isImmediate() );
	}
	
	@Test
	public void testAccessors() 
	{
		PropertyByte p = new PropertyByte("p", true);

		assertEquals( "p", p.getName() );
		
		p.set((byte)4);
		assertEquals( 4, p.get() );

		assertNull( p.getValidator() );
		
		assertNotNull( p.getListeners() );
		assertEquals( 0, p.getListeners().size() );
	}
	
	@Test
	public void testImmediate() {
		PropertyByte p1 = new PropertyByte("p1", true);

		assertTrue( p1.isImmediate() );
		assertFalse( p1.hasChanged() );
		p1.set((byte)5);
		assertFalse( p1.hasChanged() );
		assertEquals( 5, p1.get() );
		p1.reset();
		assertFalse( p1.hasChanged() );
		
		PropertyByte p2 = new PropertyByte("p2", false);
		
		assertFalse( p2.isImmediate() );
		assertFalse( p2.hasChanged() );
		p2.set((byte)6);
		assertTrue( p2.hasChanged() );
		assertEquals( 6, p2.get() );
		p2.reset();
		assertFalse( p2.hasChanged() );
	}
	
	@Test
	public void testListeners() {
		final AtomicInteger invokes = new AtomicInteger();
		
		PropertyByte p = new PropertyByte("p", true);
		
		p.set((byte)2);
		
		assertEquals( 0, invokes.get() );
		
		PropertyListener<Byte> listener1 = new PropertyListener<Byte>() {
			public void onPropertyChange(Property<Byte> property, Byte oldValue, Byte newValue) {
				invokes.incrementAndGet();
				assertEquals( 2, oldValue.intValue() );
				assertEquals( 3, newValue.intValue() );
			}
		};
		p.getListeners().add(listener1);
		
		p.set((byte)3);
		
		assertEquals( 1, invokes.get() );
		
		PropertyListener<Byte> listener2 = new PropertyListener<Byte>() {
			public void onPropertyChange(Property<Byte> property, Byte oldValue, Byte newValue) {
				invokes.incrementAndGet();
				assertEquals( 3, oldValue.intValue() );
				assertEquals( 4, newValue.intValue() );
			}
		};
		p.getListeners().remove(listener1);
		p.getListeners().add(listener2);
		
		assertEquals( 1, p.getListeners().size() );
	
		p.set((byte)4);
		
		assertEquals( 2, invokes.get() );
	}
	
	@Test
	public void testValidator() 
	{
		PropertyByte p = new PropertyByte("p", true);
		
		p.setValidator(new PropertyValidator<Byte>() {
			public boolean isValidValue(Property<Byte> property, Byte oldValue, Byte newValue) { 
				return (newValue != null && newValue > 5);
			}
		});

		p.set((byte)6);
		assertEquals( 6, p.get() );
		
		p.setValue(null);
		assertEquals( 6, p.get() );
		
		p.set((byte)4);
		assertEquals( 6, p.get() );
		
		p.set((byte)45);
		assertEquals( 45, p.get() );
	}
	
}
