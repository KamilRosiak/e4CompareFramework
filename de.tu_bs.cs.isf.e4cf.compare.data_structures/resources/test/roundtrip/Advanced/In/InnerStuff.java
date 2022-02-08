class InnerStuff {
	
	void f() {
		class SuperSecretClass{}
	}
	
	void anon() {
		
		Object object = new Object();
		
		// Seems like JavaParser doesn't support this
		/*object.setListener(Object() {
			
			@Override
			public String toString() {
				return "uff";
			};
		});*/ 
		
		Object listener = new Listener();
		object.addListener(listener -> {
			doSomething();
		});
	}
	
	private class CoolInnerClass {
		int coolInt = 1;
	}
}