package net.philsprojects.live;

import java.util.List;

import org.junit.Test;

import net.philsprojects.BaseTest;
import net.philsprojects.live.prop.PropertyBoolean;
import net.philsprojects.live.prop.PropertyString;

public class TestModule extends BaseTest 
{

	interface StringSearcher {
		public boolean isSensitive();
		public void setSensitive(boolean sensitive);
		public String getBase();
		public void setBase(String base);
		public int search(String text);
	}
	
	abstract class AbstractStringSearcher implements StringSearcher {
		private boolean sensitive = true;
		private String base = "";
		public AbstractStringSearcher() {
		}
		protected abstract int doSearch(char[] base, char[] text);
		public String getBase() {
			return base;
		}
		public boolean isSensitive() {
			return sensitive;
		}
		
		public void setBase(String base) {
			this.base = base;
		}
		public void setSensitive(boolean sensitive) {
			this.sensitive = sensitive;
		}
		public int search(String text) {
			String base = getBase();
			if (base.length() > text.length()) {
				return -1;
			}
			if (isSensitive()) {
				base = base.toLowerCase();
				text = text.toLowerCase();
			}
			return doSearch(base.toCharArray(), text.toCharArray());
		}
	}
	
	class StringSearcherForward extends AbstractStringSearcher {
		protected int doSearch(char[] base, char[] text) {
			int max = base.length - text.length + 1;
			int i, j;
			for (i = 0; i < max; i++) {
				for (j = 0; j < text.length; j++) {
					if (base[i] != text[j]) {
						break;
					}
				}
				if (j == text.length) {
					return i;
				}
			}
			return -1;
		}
	}
	
	class StringSearcherBackward extends AbstractStringSearcher {
		protected int doSearch(char[] base, char[] text) {
			int max = base.length - text.length;
			int i, j;
			for (i = max; i >= 0; i--) {
				for (j = 0; j < text.length; j++) {
					if (base[i] != text[j]) {
						break;
					}
				}
				if (j == text.length) {
					return i;
				}
			}
			return -1;
		}
	}
	
	class StringSearcherForwardStrategy extends AbstractStrategy<StringSearcherForward> {
		public final PropertyBoolean sensitive;
		public final PropertyString base;
		public StringSearcherForwardStrategy() {
			super("StringSearcherForwardStrategy");
			sensitive = new PropertyBoolean("sensitive", true);
			base = new PropertyString("base", true);
			super.load();
		}
		protected void onPropertyLoad(List<Property<?>> props) {
			props.add(sensitive);
			props.add(base);
			
			sensitive.getListeners().add(new PropertyListener<Boolean>() {
				public void onPropertyChange(Property<Boolean> property, Boolean oldValue, Boolean newValue) {
					get().setSensitive(newValue);
				}
			});
			sensitive.setValidator(new PropertyValidator<Boolean>() {
				public boolean isValidValue(Property<Boolean> property, Boolean oldValue, Boolean newValue) {
					return (newValue != null);
				}
			});
			
			base.getListeners().add(new PropertyListener<String>() {
				public void onPropertyChange(Property<String> property, String oldValue, String newValue) {
					get().setBase(newValue);
				}
			});
			base.setValidator(new PropertyValidator<String>() {
				public boolean isValidValue(Property<String> property, String oldValue, String newValue) {
					return (newValue != null);
				}
			});
		}
		protected StringSearcherForward onUpdate(StringSearcherForward current) {
			return current;
		}
		@Override
		public void set(StringSearcherForward value) {
			
		}
	}
	
	@Test
	public void test1() {
		
	}
	
}
