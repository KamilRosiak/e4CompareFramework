@Greeter(greet = "class")
@SuppressWarnings("all")
class Annotations {

	@Greeter(greet = "Field")
	int f = 0;

	@Greeter
	int def;

	@Deprecated
	public void foo(int x) {
	}

	@Greeter(greet = "Method")
	public void bar(@Greeter(greet = "Param") int i) {
		@Greeter
		float f = 0;
	}
}

@Retention(RetentionPolicy.RUNTIME)
public @interface Greeter {

	public String greet() default "";
}
