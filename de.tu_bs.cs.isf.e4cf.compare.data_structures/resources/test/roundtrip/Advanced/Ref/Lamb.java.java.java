class Lamb {

	private int lambda() {
		Sayable a = (String parameter) -> {
			return "Hello " + parameter;
		};
		a.say("World");
		return 0;
	}

	interface Sayable {

		public String say(String name);
	}
}
