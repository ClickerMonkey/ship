package net.philsprojects;

import java.lang.annotation.*;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD})
public @interface Ordered
{
	public int index() default -1;
}
