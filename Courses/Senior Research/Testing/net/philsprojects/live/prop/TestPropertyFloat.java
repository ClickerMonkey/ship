package net.philsprojects.live.prop;

import static org.junit.Assert.*;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import net.philsprojects.BaseTest;
import net.philsprojects.live.Property;
import net.philsprojects.live.PropertyListener;
import net.philsprojects.live.PropertyValidator;
import net.philsprojects.live.prop.PropertyFloat;

public class TestPropertyFloat extends BaseTest 
{

	@Test
	public void testConstructors() 
	{
		PropertyFloat p1 = new PropertyFloat("p1", true);
		assertNull( p1.getValidator() );
		assertTrue( p1.isImmediate() );
		
		PropertyFloat p2 = new PropertyFloat("p2", false, new PropertyValidator<Float>() {
			public boolean isValidValue(Property<Float> property, Float oldValue, Float newValue) {
				return false;
			}
		});
		assertNotNull( p2.getValidator() );
		assertFalse( p2.isImmediate() );
	}
	
	@Test
	public void testAccessors() 
	{
		PropertyFloat p = new PropertyFloat("p", true);

		assertEquals( "p", p.getName() );
		
		p.set(4);
		assertEquals( 4, p.get(), 0.00001 );

		assertNull( p.getValidator() );
		
		assertNotNull( p.getListeners() );
		assertEquals( 0, p.getListeners().size() );
	}
	
	@Test
	public void testImmediate() {
		PropertyFloat p1 = new PropertyFloat("p1", true);

		assertTrue( p1.isImmediate() );
		assertFalse( p1.hasChanged() );
		p1.set(5);
		assertFalse( p1.hasChanged() );
		assertEquals( 5, p1.get(), 0.00001 );
		p1.reset();
		assertFalse( p1.hasChanged() );
		
		PropertyFloat p2 = new PropertyFloat("p2", false);
		
		assertFalse( p2.isImmediate() );
		assertFalse( p2.hasChanged() );
		p2.set(6);
		assertTrue( p2.hasChanged() );
		assertEquals( 6, p2.get(), 0.00001 );
		p2.reset();
		assertFalse( p2.hasChanged() );
	}
	
	@Test
	public void testListeners() {
		final AtomicInteger invokes = new AtomicInteger();
		
		PropertyFloat p = new PropertyFloat("p", true);
		
		p.set(2);
		
		assertEquals( 0, invokes.get() );
		
		PropertyListener<Float> listener1 = new PropertyListener<Float>() {
			public void onPropertyChange(Property<Float> property, Float oldValue, Float newValue) {
				invokes.incrementAndGet();
				assertEquals( 2, oldValue.intValue() );
				assertEquals( 3, newValue.intValue() );
			}
		};
		p.getListeners().add(listener1);
		
		p.set(3);
		
		assertEquals( 1, invokes.get() );
		
		PropertyListener<Float> listener2 = new PropertyListener<Float>() {
			public void onPropertyChange(Property<Float> property, Float oldValue, Float newValue) {
				invokes.incrementAndGet();
				assertEquals( 3, oldValue.intValue() );
				assertEquals( 4, newValue.intValue() );
			}
		};
		p.getListeners().remove(listener1);
		p.getListeners().add(listener2);
		
		assertEquals( 1, p.getListeners().size() );
	
		p.set(4);
		
		assertEquals( 2, invokes.get() );
	}
	
	@Test
	public void testValidator() 
	{
		PropertyFloat p = new PropertyFloat("p", true);
		
		p.setValidator(new PropertyValidator<Float>() {
			public boolean isValidValue(Property<Float> property, Float oldValue, Float newValue) { 
				return (newValue != null && newValue > 5);
			}
		});

		p.set(6);
		assertEquals( 6, p.get(), 0.00001 );
		
		p.setValue(null);
		assertEquals( 6, p.get(), 0.00001 );
		
		p.set(4);
		assertEquals( 6, p.get(), 0.00001 );
		
		p.set(45);
		assertEquals( 45, p.get(), 0.00001 );
	}
	
}
