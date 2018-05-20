package net.philsprojects.live.prop;

import static org.junit.Assert.*;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import net.philsprojects.BaseTest;
import net.philsprojects.live.Property;
import net.philsprojects.live.PropertyListener;
import net.philsprojects.live.PropertyValidator;
import net.philsprojects.live.prop.PropertyBoolean;

public class TestPropertyBoolean extends BaseTest 
{

	@Test
	public void testConstructors() 
	{
		PropertyBoolean p1 = new PropertyBoolean("p1", true);
		assertNull( p1.getValidator() );
		assertTrue( p1.isImmediate() );
		
		PropertyBoolean p2 = new PropertyBoolean("p2", false, new PropertyValidator<Boolean>() {
			public boolean isValidValue(Property<Boolean> property, Boolean oldValue, Boolean newValue) {
				return false;
			}
		});
		assertNotNull( p2.getValidator() );
		assertFalse( p2.isImmediate() );
	}
	
	@Test
	public void testAccessors() 
	{
		PropertyBoolean p = new PropertyBoolean("p", true);

		assertEquals( "p", p.getName() );
		
		p.set(true);
		assertEquals( true, p.get() );

		assertNull( p.getValidator() );
		
		assertNotNull( p.getListeners() );
		assertEquals( 0, p.getListeners().size() );
	}
	
	@Test
	public void testImmediate() {
		PropertyBoolean p1 = new PropertyBoolean("p1", true);

		assertTrue( p1.isImmediate() );
		assertFalse( p1.hasChanged() );
		p1.set(true);
		assertFalse( p1.hasChanged() );
		assertEquals( true, p1.get() );
		p1.reset();
		assertFalse( p1.hasChanged() );
		
		PropertyBoolean p2 = new PropertyBoolean("p2", false);
		
		assertFalse( p2.isImmediate() );
		assertFalse( p2.hasChanged() );
		p2.set(true);
		assertTrue( p2.hasChanged() );
		assertEquals( true, p2.get() );
		p2.reset();
		assertFalse( p2.hasChanged() );
	}
	
	@Test
	public void testListeners() {
		final AtomicInteger invokes = new AtomicInteger();
		
		PropertyBoolean p = new PropertyBoolean("p", true);
		
		p.set(true);
		
		assertEquals( 0, invokes.get() );
		
		PropertyListener<Boolean> listener1 = new PropertyListener<Boolean>() {
			public void onPropertyChange(Property<Boolean> property, Boolean oldValue, Boolean newValue) {
				invokes.incrementAndGet();
				assertEquals( true, oldValue );
				assertEquals( false, newValue );
			}
		};
		p.getListeners().add(listener1);
		
		p.set(false);
		
		assertEquals( 1, invokes.get() );
		
		PropertyListener<Boolean> listener2 = new PropertyListener<Boolean>() {
			public void onPropertyChange(Property<Boolean> property, Boolean oldValue, Boolean newValue) {
				invokes.incrementAndGet();
				assertEquals( false, oldValue );
				assertEquals( true, newValue );
			}
		};
		p.getListeners().remove(listener1);
		p.getListeners().add(listener2);
		
		assertEquals( 1, p.getListeners().size() );
	
		p.set(true);
		
		assertEquals( 2, invokes.get() );
	}
	
	@Test
	public void testValidator() 
	{
		PropertyBoolean p = new PropertyBoolean("p", true);
		
		p.setValidator(new PropertyValidator<Boolean>() {
			public boolean isValidValue(Property<Boolean> property, Boolean oldValue, Boolean newValue) { 
				return (newValue != null && newValue != false);
			}
		});

		p.set(true);
		assertEquals( true, p.get() );
		
		p.setValue(null);
		assertEquals( true, p.get() );
		
		p.set(false);
		assertEquals( true, p.get() );
	}
	
}
