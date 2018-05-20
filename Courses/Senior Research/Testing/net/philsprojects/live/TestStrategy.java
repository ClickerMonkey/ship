package net.philsprojects.live;

import static org.junit.Assert.*;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import net.philsprojects.BaseTest;
import net.philsprojects.io.buffer.BufferFactoryBinary;
import net.philsprojects.live.prop.PropertyInt;

public class TestStrategy extends BaseTest 
{

	private class BufferFactoryBinaryStrategy extends AbstractStrategy<BufferFactoryBinary> 
	{
		public final PropertyInt minPower;
		public final PropertyInt maxPower;
		
		public BufferFactoryBinaryStrategy(String name) 
		{
			super(name);
			minPower = new PropertyInt("minPower", false);
			maxPower = new PropertyInt("maxPower", false);
			super.load();
		}
		
		@Override
		public void onPropertyLoad(List<Property<?>> props) 
		{
			props.add(minPower);
			props.add(maxPower);
			
			maxPower.setValidator(new PropertyValidator<Integer>() {
				public boolean isValidValue(Property<Integer> property, Integer oldValue, Integer newValue) {
					return (newValue >= 5 && newValue <= 16 && newValue > minPower.get());
				}
			});
			minPower.setValidator(new PropertyValidator<Integer>() {
				public boolean isValidValue(Property<Integer> property, Integer oldValue, Integer newValue) {
					return (newValue >= 5 && newValue <= 16 && newValue < maxPower.get());
				}
			});
		}
		
		@Override
		protected BufferFactoryBinary onUpdate(BufferFactoryBinary current) 
		{
			BufferFactoryBinary next = new BufferFactoryBinary(minPower.get(), maxPower.get());
			if (current != null) {
				List<ByteBuffer> buffers = current.release();
				List<ByteBuffer> extra = next.transfer(buffers);
				for (ByteBuffer buffer : extra) {
					next.free(buffer);
				}	
			}
			return next;
		}

		@Override
		public void set(BufferFactoryBinary value) {
			// TODO Auto-generated method stub
			
		}
	}
	
	@Test
	public void testChange() 
	{
		BufferFactoryBinaryStrategy strategy = new BufferFactoryBinaryStrategy("test");
		
		assertNull( strategy.get() );
		assertFalse( strategy.hasChanged() );
		assertFalse( strategy.maxPower.hasChanged() );
		assertFalse( strategy.minPower.hasChanged() );
		
		strategy.maxPower.set(14);
		strategy.minPower.set(8);

		assertTrue( strategy.maxPower.hasChanged() );
		assertTrue( strategy.minPower.hasChanged() );
		assertTrue( strategy.hasChanged() );
	}
	
	@Test
	public void testUpdate() 
	{
		BufferFactoryBinaryStrategy strategy = new BufferFactoryBinaryStrategy("test");
		
		assertNull( strategy.get() );
		
		strategy.maxPower.set(14);
		strategy.minPower.set(8);
		strategy.update();
		
		assertNotNull( strategy.get() );
	}
	
	@Test
	public void testListeners()
	{
		final AtomicInteger updates = new AtomicInteger();
		
		BufferFactoryBinaryStrategy strategy = new BufferFactoryBinaryStrategy("test");
		
		strategy.getListeners().add(new StrategyListener<BufferFactoryBinary>() {
			public void onStrategyUpdate(Strategy<BufferFactoryBinary> strategy) {
				updates.incrementAndGet();
			}
			public void onStrategyError(Strategy<BufferFactoryBinary> strategy,Exception e) {
			}
		});

		strategy.maxPower.set(14);
		strategy.minPower.set(8);
		strategy.update();
		
		assertEquals( 1, updates.get() );
	}
	
}
