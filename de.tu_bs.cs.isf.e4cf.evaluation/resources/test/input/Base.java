package de.tu_bs.cs.isf.e4c.clonerepo;

public class Base {

	public void sumTimes(int n) {
		float s = 0;
		float p = 1;
		for (int i; i <= n; i++) {
			s = s + i;
			p = p * i;
			fun(s, p);
		}
	}
	
	private void fun(float a, float b) {}

}
