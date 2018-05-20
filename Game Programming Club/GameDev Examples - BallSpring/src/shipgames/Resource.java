package shipgames;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Resource 
{
	String name();
	
	public enum ResourceType {
		BOOLEAN,
		BYTE,
		SHORT,
		INTEGER,
		LONG,
		FLOAT,
		DOUBLE,
		IMAGE,
		FILE_FONT,
		SYSTEM_FONT,
		AUDIO_CLIP,
		COLOR,
	}
	
	ResourceType type();
}
