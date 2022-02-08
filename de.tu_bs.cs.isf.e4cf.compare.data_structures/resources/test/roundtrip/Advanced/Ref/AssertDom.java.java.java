class AssertDom {

	void f() {
		assert true : "If this fails we are all doomed.";
		assert false : s;
		assert 1 == 2 : errorMsg();
	}
}
