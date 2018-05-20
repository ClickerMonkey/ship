package net.philsprojects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

public class OrderedRunner extends BlockJUnit4ClassRunner 
{

	public OrderedRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}

	@Override
	protected List<FrameworkMethod> computeTestMethods() {
		List<FrameworkMethod> list = super.computeTestMethods();
		List<FrameworkMethod> copy = new ArrayList<FrameworkMethod>(list);
		Collections.sort(copy, new Comparator<FrameworkMethod>() {
			public int compare(FrameworkMethod o1, FrameworkMethod o2) {
				Ordered ot1 = o1.getAnnotation(Ordered.class);
				Ordered ot2 = o2.getAnnotation(Ordered.class);
				if (ot1 != null && ot2 != null) {
					return ot1.index() - ot2.index();
				}
				return 0;
			}
		});
		return copy;
	}

}