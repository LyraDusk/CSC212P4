package edu.smith.cs.csc212.p4;


/*
 * Subclass of Exit, represents an exit that cannot be seen unless
 * it is first searched for. 
 */
public class SecretExit extends Exit{
	
	/*
	 * Constructor for the SecretExit subclass, that sets the secret boolean to true
	 */
	public SecretExit(String target, String description) {
		super(target,description);
		this.isSecret = true;
	}
	
	/*
	 * Unhides the secret door
	 */
	public void search() {
		this.isSecret = false;
	}
	
	
}
