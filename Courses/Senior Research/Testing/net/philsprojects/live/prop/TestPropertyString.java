package net.philsprojects.live.prop;

import static org.junit.Assert.*;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import net.philsprojects.BaseTest;
import net.philsprojects.live.Property;
import net.philsprojects.live.PropertyListener;
import net.philsprojects.live.PropertyValidator;
import net.philsprojects.live.prop.PropertyString;

public class TestPropertyString extends BaseTest 
{

	@Test
	public void testConstructors() 
	{
		PropertyString p1 = new PropertyString("p1", true);
		assertNull( p1.getValidator() );
		assertTrue( p1.isImmediate() );
		
		PropertyString p2 = new PropertyString("p2", false, new PropertyValidator<String>() {
			public boolean isValidValue(Property<String> property, String oldValue, String newValue) {
				return false;
			}
		});
		assertNotNull( p2.getValidator() );
		assertFalse( p2.isImmediate() );
	}
	
	@Test
	public void testAccessors() 
	{
		PropertyString p = new PropertyString("p", true);

		assertEquals( "p", p.getName() );
		
		p.set("4");
		assertEquals( "4", p.get() );

		assertNull( p.getValidator() );
		
		assertNotNull( p.getListeners() );
		assertEquals( 0, p.getListeners().size() );
	}
	
	@Test
	public void testImmediate() {
		PropertyString p1 = new PropertyString("p1", true);

		assertTrue( p1.isImmediate() );
		assertFalse( p1.hasChanged() );
		p1.set("5");
		assertFalse( p1.hasChanged() );
		assertEquals( "5", p1.get() );
		p1.reset();
		assertFalse( p1.hasChanged() );
		
		PropertyString p2 = new PropertyString("p2", false);
		
		assertFalse( p2.isImmediate() );
		assertFalse( p2.hasChanged() );
		p2.set("6");
		assertTrue( p2.hasChanged() );
		assertEquals( "6", p2.get() );
		p2.reset();
		assertFalse( p2.hasChanged() );
	}
	
	@Test
	public void testListeners() {
		final AtomicInteger invokes = new AtomicInteger();
		
		PropertyString p = new PropertyString("p", true);
		
		p.set("2");
		
		assertEquals( 0, invokes.get() );
		
		PropertyListener<String> listener1 = new PropertyListener<String>() {
			public void onPropertyChange(Property<String> property, String oldValue, String newValue) {
				invokes.incrementAndGet();
				assertEquals( "2", oldValue );
				assertEquals( "3", newValue );
			}
		};
		p.getListeners().add(listener1);
		
		p.set("3");
		
		assertEquals( 1, invokes.get() );
		
		PropertyListener<String> listener2 = new PropertyListener<String>() {
			public void onPropertyChange(Property<String> property, String oldValue, String newValue) {
				invokes.incrementAndGet();
				assertEquals( "3", oldValue );
				assertEquals( "4", newValue );
			}
		};
		p.getListeners().remove(listener1);
		p.getListeners().add(listener2);
		
		assertEquals( 1, p.getListeners().size() );
	
		p.set("4");
		
		assertEquals( 2, invokes.get() );
	}
	
	@Test
	public void testValidator() 
	{
		PropertyString p = new PropertyString("p", true);
		
		p.setValidator(new PropertyValidator<String>() {
			public boolean isValidValue(Property<String> property, String oldValue, String newValue) { 
				return (newValue != null && !newValue.contains("5"));
			}
		});

		p.set("6");
		assertEquals( "6", p.get() );
		
		p.setValue(null);
		assertEquals( "6", p.get() );

		p.set("5");
		assertEquals( "6", p.get() );
		
		p.set("152");
		assertEquals( "6", p.get() );
		
		p.set("42");
		assertEquals( "42", p.get() );
	}
	
}
