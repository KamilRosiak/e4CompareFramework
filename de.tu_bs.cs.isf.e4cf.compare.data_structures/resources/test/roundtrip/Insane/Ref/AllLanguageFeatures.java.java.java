package de.tu_bs.cs.isf.e4c.clonerepo;

import java.io.Serial;
import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;

/*
 * This is a block comment typically used for documentation.
 */
@SuppressWarnings("all")
public class AllLanguageFeatures extends Object implements Serializable {

	/* Class Field Declaration*/
	private static final long serialVersionUID = 1L;

	private int nonInitializedField;

	public static int staticInt;

	public int z, x, y = 419;

	/* Initializer Block*/
	{
		nonInitializedField = 0;
	}

	/* Static Initializer Block*/
	{
		staticInt = Integer.MAX_VALUE;
	}

	/* Constructor*/
	public AllLanguageFeatures(int n) {
		super();
		this.nonInitializedField = n;
	}

	/* Method*/
	public float foo(int argument0, int argument1) {
		bar(argument1, argument0);
		var a = new ArrayList<String>();
		int[] myNum = { 10, 20, 30, 40 };
		int c = myNum[(123 * 456)];
		return 0f;
	}

	public void bar() {
		foo(123, 456);
	}

	/* Choice*/
	private void ifElseIf(int i) {
		int j;
		if (i == 0) {
			j = 0;
		} else if (i == 1) {
			j = 1;
		} else if (i == 2) {
			j = 2;
		} else {
			j = -1;
		}
	}

	private void switches() {
		int i = 0;
		switch(j) {
			case 5:
				i = 5;
				break;
			default:
				break;
		}
	}

	private String ternary(int i) {
		return i < 0 ? "Negative" : "Positive";
	}

	/* Loops*/
	private void loopWhile(int i) {
		while (i < 100) i++;
	}

	private void loopDoWhile(int i) {
		do {
			i++;
		} while (i < 100);
	}

	private void loopFor() {
		for (int i = 10; i > 100; i--) {
			--i;
		}
	}

	private void loopForEach(ArrayList<String> list) {
		for (String s : list) {
			s += "a";
		}
	}

	private void typeCheckAndCast(Object o) {
		if (o instanceof AllLanguageFeatures) {
			AllLanguageFeatures a = (AllLanguageFeatures) o;
		}
	}

	private void errors() throws Exception {
		try {
			int i = 0;
		} catch (IndexOutOfBoundsException | NullPointerException e) {
			int i = 1;
		} finally {
			throw new Exception();
		}
	}

	private void assertion() {
		assert true;
	}

	private void innerClassMember() {
		class SuperSecretClass {
		}
	}

	private void genericFunction() {
		class GenericInnerClass<T extends Object & Serializable> {
		}
	}

	private void labeledStatements(boolean grumpy) {
		morningRoutine: {
			if (grumpy)
				break morningRoutine;
		}
	}

	private void lambda() {
		Sayable a = (String parameter) -> {
			return "Hello " + parameter;
		};
		a.say("World");
	}

	public synchronized void threadSafeMethod() {
	}

	/* Method References*/
	public static String referencedMethod(String s) {
		return "Hello, this is static method.";
	}

	public static void invokeMethodReference() /* Referring static method  */
	{
		Sayable sayable = AllLanguageFeatures::referencedMethod;
		sayable.say("Pain");
	}

	/* Annotated Method*/
	@Greeter(greet = "Good morning")
	public void bar() {
	}

	/* enum*/
	private enum Enumaration {

		A_CONSTANT("A"), ANOTHER_CONSTANT("B");

		public final String value;

		private Enumaration(String value) {
			this.value = value;
		}
	}

	/* interface*/
	interface Sayable {

		public String say(String name);
	}

	/* inner class*/
	private class CoolInnerClass {

		int coolInt = 1;
	}
}

@Retention(RetentionPolicy.RUNTIME)
public @interface Greeter {

	public String greet() default "";
}
