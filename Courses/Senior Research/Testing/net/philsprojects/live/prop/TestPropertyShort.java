package net.philsprojects.live.prop;

import static org.junit.Assert.*;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import net.philsprojects.BaseTest;
import net.philsprojects.live.Property;
import net.philsprojects.live.PropertyListener;
import net.philsprojects.live.PropertyValidator;
import net.philsprojects.live.prop.PropertyShort;

public class TestPropertyShort extends BaseTest 
{

	@Test
	public void testConstructors() 
	{
		PropertyShort p1 = new PropertyShort("p1", true);
		assertNull( p1.getValidator() );
		assertTrue( p1.isImmediate() );
		
		PropertyShort p2 = new PropertyShort("p2", false, new PropertyValidator<Short>() {
			public boolean isValidValue(Property<Short> property, Short oldValue, Short newValue) {
				return false;
			}
		});
		assertNotNull( p2.getValidator() );
		assertFalse( p2.isImmediate() );
	}
	
	@Test
	public void testAccessors() 
	{
		PropertyShort p = new PropertyShort("p", true);

		assertEquals( "p", p.getName() );
		
		p.set((short)4);
		assertEquals( 4, p.get() );

		assertNull( p.getValidator() );
		
		assertNotNull( p.getListeners() );
		assertEquals( 0, p.getListeners().size() );
	}
	
	@Test
	public void testImmediate() {
		PropertyShort p1 = new PropertyShort("p1", true);

		assertTrue( p1.isImmediate() );
		assertFalse( p1.hasChanged() );
		p1.set((short)5);
		assertFalse( p1.hasChanged() );
		assertEquals( 5, p1.get() );
		p1.reset();
		assertFalse( p1.hasChanged() );
		
		PropertyShort p2 = new PropertyShort("p2", false);
		
		assertFalse( p2.isImmediate() );
		assertFalse( p2.hasChanged() );
		p2.set((short)6);
		assertTrue( p2.hasChanged() );
		assertEquals( 6, p2.get() );
		p2.reset();
		assertFalse( p2.hasChanged() );
	}
	
	@Test
	public void testListeners() {
		final AtomicInteger invokes = new AtomicInteger();
		
		PropertyShort p = new PropertyShort("p", true);
		
		p.set((short)2);
		
		assertEquals( 0, invokes.get() );
		
		PropertyListener<Short> listener1 = new PropertyListener<Short>() {
			public void onPropertyChange(Property<Short> property, Short oldValue, Short newValue) {
				invokes.incrementAndGet();
				assertEquals( 2, oldValue.intValue() );
				assertEquals( 3, newValue.intValue() );
			}
		};
		p.getListeners().add(listener1);
		
		p.set((short)3);
		
		assertEquals( 1, invokes.get() );
		
		PropertyListener<Short> listener2 = new PropertyListener<Short>() {
			public void onPropertyChange(Property<Short> property, Short oldValue, Short newValue) {
				invokes.incrementAndGet();
				assertEquals( 3, oldValue.intValue() );
				assertEquals( 4, newValue.intValue() );
			}
		};
		p.getListeners().remove(listener1);
		p.getListeners().add(listener2);
		
		assertEquals( 1, p.getListeners().size() );
	
		p.set((short)4);
		
		assertEquals( 2, invokes.get() );
	}
	
	@Test
	public void testValidator() 
	{
		PropertyShort p = new PropertyShort("p", true);
		
		p.setValidator(new PropertyValidator<Short>() {
			public boolean isValidValue(Property<Short> property, Short oldValue, Short newValue) { 
				return (newValue != null && newValue > 5);
			}
		});

		p.set((short)6);
		assertEquals( 6, p.get() );
		
		p.setValue(null);
		assertEquals( 6, p.get() );
		
		p.set((short)4);
		assertEquals( 6, p.get() );
		
		p.set((short)45);
		assertEquals( 45, p.get() );
	}
	
}
