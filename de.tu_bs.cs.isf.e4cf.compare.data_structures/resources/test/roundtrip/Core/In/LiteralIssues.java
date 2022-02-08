class LiteralIssues {
	float f = 123 + 32f;
	float f2 = 123;
	double d = 2.0;
	int i = Integer.parseInt("123");
	
	{
		f = 123;
	}
}