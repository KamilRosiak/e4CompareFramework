class Loops {

	void f() {
		while (i < 0) {
		}
		do {
			i++;
		} while (i < 100);
		for (int a = 3, b = 5; a < 99; a++, b++) hello();
		for (a = 3, b = 5; a < 99; a++) {
			hello();
		}
		for (a(), b(); true; ) hello();
		for (; true; ) hello();
	}
}
