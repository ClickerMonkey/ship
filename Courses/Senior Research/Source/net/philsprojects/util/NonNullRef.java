package net.philsprojects.util;

public class NonNullRef<E> implements Ref<E> 
{

	private volatile E value;
	
	@Override
	public E get() {
		if (value == null) {
			synchronized (this) {
				if (value == null) {
					try {
						this.wait();
					} catch (InterruptedException e) {
					}
				}
			}
		}
		return value;
	}

	@Override
	public void set(E newValue) 
	{
		if (newValue != null) {
			if (value == null) {
				synchronized (this) {
					if (value == null) {
						this.notifyAll();
						this.value = newValue;
					}
				}
			}
		}
		this.value = newValue;	
	}
	
	public boolean has() 
	{
		return (value != null);
	}
	
	public void wakeup()
	{
		synchronized (this) {
			this.notifyAll();
		}
	}

}
