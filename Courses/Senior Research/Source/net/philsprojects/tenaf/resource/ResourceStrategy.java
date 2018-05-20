package net.philsprojects.tenaf.resource;

import java.util.List;

import net.philsprojects.live.AbstractStrategy;
import net.philsprojects.live.Property;
import net.philsprojects.live.PropertyListener;
import net.philsprojects.live.PropertyValidator;
import net.philsprojects.live.prop.PropertyInt;
import net.philsprojects.live.prop.PropertyLong;
import net.philsprojects.resource.Resource;
import net.philsprojects.resource.ResourcePool;

public class ResourceStrategy<R extends Resource> extends AbstractStrategy<ResourcePool<R>> 
{
	
	private class IntValidator implements PropertyValidator<Integer> 
	{
		private final int min, max;
		public IntValidator(int min, int max) {
			this.min = min;
			this.max = max;
		}
		public boolean isValidValue(Property<Integer> property, Integer oldValue, Integer newValue) {
			return (newValue != null && newValue >= min && newValue <= max);
		}
	}
	
	private PropertyInt allocateSize;
	private PropertyInt deallocateSize;
	private PropertyLong allocateThreshold;
	private PropertyInt capacity;
	private PropertyInt maxCapacity;
	private PropertyInt minCapacity;

	/**
	 * Instantiates a new ResourceStrategy.
	 * 
	 * @param name
	 * @param pool
	 */
	public ResourceStrategy(String name, ResourcePool<R> pool) 
	{
		super(name);
	
		ref.set(pool);
		
		allocateSize = new PropertyInt("allocateSize", true);
		deallocateSize = new PropertyInt("deallocateSize", true);
		allocateThreshold = new PropertyLong("deallocateSize", true);
		capacity = new PropertyInt("capacity", true);
		maxCapacity = new PropertyInt("maxCapacity", true);
		minCapacity = new PropertyInt("minCapacity", true);
		
		this.load();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onPropertyLoad(List<Property<?>> props) 
	{
		props.add(allocateSize);
		props.add(deallocateSize);
		props.add(allocateThreshold);
		props.add(capacity);
		props.add(minCapacity);
		props.add(maxCapacity);

		allocateSize.setValidator(new IntValidator(0, Integer.MAX_VALUE));
		deallocateSize.setValidator(new IntValidator(0, Integer.MAX_VALUE));
		capacity.setValidator(new IntValidator(0, Integer.MAX_VALUE));
		maxCapacity.setValidator(new IntValidator(0, Integer.MAX_VALUE));
		minCapacity.setValidator(new IntValidator(0, Integer.MAX_VALUE));
		
		allocateSize.getListeners().add(new PropertyListener<Integer>() {
			public void onPropertyChange(Property<Integer> property, Integer oldValue, Integer newValue) {
				ref.get().setAllocateSize(newValue);
			}
		});

		deallocateSize.getListeners().add(new PropertyListener<Integer>() {
			public void onPropertyChange(Property<Integer> property, Integer oldValue, Integer newValue) {
				ref.get().setDeallocateSize(newValue);
			}
		});

		allocateThreshold.getListeners().add(new PropertyListener<Long>() {
			public void onPropertyChange(Property<Long> property, Long oldValue, Long newValue) {
				ref.get().setAllocateThreshold(newValue);
			}
		});
		
		capacity.getListeners().add(new PropertyListener<Integer>() {
			public void onPropertyChange(Property<Integer> property, Integer oldValue, Integer newValue) {
				ref.get().setCapacity(newValue);
			}
		});

		maxCapacity.getListeners().add(new PropertyListener<Integer>() {
			public void onPropertyChange(Property<Integer> property, Integer oldValue, Integer newValue) {
				ref.get().setMaxCapacity(newValue);
			}
		});

		minCapacity.getListeners().add(new PropertyListener<Integer>() {
			public void onPropertyChange(Property<Integer> property, Integer oldValue, Integer newValue) {
				ref.get().setMinCapacity(newValue);
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ResourcePool<R> onUpdate(ResourcePool<R> current) 
	{
		return current;
	}

}
