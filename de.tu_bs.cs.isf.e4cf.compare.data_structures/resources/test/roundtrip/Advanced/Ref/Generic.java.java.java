class Generic {

	var a = new ArrayList<String>();

	private void genericFunction() {
		class GenericInnerClass<T extends Object & Serializable> {
		}
	}

	void disjunct() {
		try {
			int i = 0;
		} catch (IndexOutOfBoundsException | NullPointerException e) {
			int i = 1;
		}
	}
}
