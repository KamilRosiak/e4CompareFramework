class Superman extends Batman {
	
	int health;
	
	public Superman() {
		this(4);
	}
	
	public Superman(int hp) {
		super();
		this.health = hp;
		int damage = this.health;
		this.fly();
	}
	
	void fly() {	
	}
}
