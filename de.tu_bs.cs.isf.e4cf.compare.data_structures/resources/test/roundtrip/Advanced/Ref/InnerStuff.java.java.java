class InnerStuff {

	void f() {
		class SuperSecretClass {
		}
	}

	void anon() {
		Object object = new Object();
		Object listener = new Listener();
		object.addListener(listener -> {
			doSomething();
		});
	}

	private class CoolInnerClass {

		int coolInt = 1;
	}
}
