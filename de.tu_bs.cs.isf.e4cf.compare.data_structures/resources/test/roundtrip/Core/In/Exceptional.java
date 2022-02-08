class Exceptional {
	
void f() throws NullPointerException {
	try {
		hello();
	} catch (RuntimeException e) {
		ohNo();
	} catch (Exception ee) {
		e.printStackTrace();
	} finally {

	}
	
	throw new NullPointerException();
}
	
}