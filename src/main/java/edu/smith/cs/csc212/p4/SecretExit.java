package edu.smith.cs.csc212.p4;

public class SecretExit extends Exit{
	
	private boolean isSecret;
	
	
	public SecretExit(String target, String description) {
		super(target,description);
		this.isSecret = true;
	}
	
	public void search() {
		this.isSecret = false;
	}
	
	
}
