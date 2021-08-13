class References {

	/*	
	// Method References
	public static String referencedMethod(String s){  
        return "Hello, this is static method.";  
    }  
	
    public static void invokeMethodReference() {  
        // Referring static method  
        Sayable sayable = References::referencedMethod;  
        // Calling interface method  
        sayable.say("Pain");  
    }
	
	interface Sayable {
		public String say(String name);
	}
	
*/
	void JPDoc() {
		i = Bar<String>::<Integer>new;
		j = Integer::new;
		a = (test ? stream.map(String::trim) : stream)::toArray;
	}
}
