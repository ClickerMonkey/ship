package net.philsprojects.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A dynamic proxy class for notifying a list of listeners. Listeners can be 
 * added and removed from the notifier. Once methods are invoked on the proxy()
 * object all listeners added to this notifier have the exact same method 
 * invoked with the same arguments. Proxies add approximately 500ns in overhead 
 * compared to plain invokation. The ideal environment for a Notifier consists 
 * of few modification to the listeners relative to the number of times methods
 * on the proxy object are invoked. Another ideal aspect is infrequent use of
 * the proxy object, a.k.a. it should not be a heavily invoked object, the
 * overhead for using Proxies can build up when its being invoked thousands of
 * times in a very short time. A similar aspect is the number of listeners,
 * if there are thousands of listeners which are invoked frequently it will
 * have the same effect as described above, excessive overhead may build up.
 * 
 * <h1>Example</h1>
 * <pre>
 * interface EventListener {
 * 	public void onEvent(String event); 
 * }
 * 
 * // instantiate
 * Notifier&lt;EventListener&gt; listeners = Notifier.create(EventListener.class);
 * 
 * // add listeners
 * listeners.add(new EventListener() {
 * 	public void onEvent(String event) {
 * 		// event has been received.
 * 	}
 * });
 * 
 * EventListener impl = ...
 * listeners.add(impl);
 * 
 * // notify listeners
 * listeners.proxy().onEvent("Hello World");
 * </pre>
 * 
 * @author Philip Diffenderfer
 *
 * @see CopyOnWriteArrayList
 * @param <T>
 * 		The element type to be proxied.
 */
public class Notifier<T> extends CopyOnWriteArrayList<T> implements InvocationHandler 
{

	// The proxy which notifies the elements on the list.
	private final T proxy;
	
	private final List<Object> results;
	
	/**
	 * Instantiates a new Notifier given the proxy type.
	 * 
	 * @param type
	 * 		The proxy class.
	 */
	@SuppressWarnings("unchecked")
	private Notifier(Class<T> type) 
	{
		proxy = (T)Proxy.newProxyInstance(
				getClass().getClassLoader(), 
				new Class<?>[] {type}, 
				this);
		results = new ArrayList<Object>();
	}
	
	/**
	 * {@inheritDoc} 
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable 
	{
		Object result = null;
		
		// Clear the results from the previous invocation.
		results.clear();
		
		// For each listener currently in this notifier...
		for (T listener : this) 
		{
			// Catch errors thrown by listeners
			try 
			{
				// Invoke listener
				result = method.invoke(listener, args);	
				
				// If an actual result was returned, append to results.
				if (result != null) {
					results.add(result);	
				}
			}
			catch (Throwable t) 
			{
				// Print out all exceptions to stdout.
				t.printStackTrace();
			}
		}
		// Return the last result.
		return result;
	}
	
	/**
	 * Returns the proxy object. Methods invoked on this object will in turn
	 * invoke the same method with the same arguments on the listeners and
	 * return the result of the last invokation (if any).
	 * 
	 * @return
	 * 		The reference to the proxy object.
	 */
	public T proxy() 
	{
		return proxy;
	}
	
	/**
	 * Returns the list of results from the last invokation to the proxy object.
	 * This list can only be safely accessed by the same thread that invoked
	 * the method on the proxy object.
	 * 
	 * @return
	 * 		The reference to the list of results.
	 */
	public List<Object> results()
	{
		return results;
	}
	
	
	/**
	 * Creates a new Notifier for the given type.
	 * 
	 * @param <T>
	 * 		The notifier type.
	 * @param type
	 * 		The proxy class.
	 * @return
	 * 		The reference to a newly instantiated Notifier.
	 */
	@SuppressWarnings({ "unchecked" })
	public static <T> T create(Class<?> type) 
	{
		return (T)new Notifier(type);
	}

}
