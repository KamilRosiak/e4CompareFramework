package de.tu_bs.cs.isf.e4c.clonerepo;

public class Methods {

	public static int exponentiation(int b, int n) {
		int result = b;
		for (int i = 0; i < n; i++) {
			result *= b;
		}
		return result;
	}
	
	private static int add(int a, int b) {
		return a + b;
	}

    private static int sum(int a, int b) {
		return a * b;
	}

}
