package edu.smith.cs.csc212.p4;

import java.util.List;

/**
 * This is our main class for P4. It interacts with a GameWorld and handles user-input.
 * @author jfoley
 *
 */
public class InteractiveFiction {

	/**
	 * This is where we play the game.
	 * @param args
	 */
	public static void main(String[] args) {
		// This is a text input source (provides getUserWords() and confirm()).
		TextInput input = TextInput.fromArgs(args);

		// This is the game we're playing.
		GameWorld game = new SpookyMansion();
		
		//This is the time keeper
		GameTime timer = new GameTime();
		
		
		// This is the current location of the player (initialize as start).
		// Maybe we'll expand this to a Player object.
		String place = game.getStart();

		// Play the game until quitting.
		// This is too hard to express here, so we just use an infinite loop with breaks.
		while (true) {
			// Print the description of where you are.
			Place here = game.getPlace(place);
			System.out.println(here.getDescription(timer.getDay()));
			System.out.println(timer.printHour());

			// Game over after print!
			if (here.isTerminalState()) {
				break;
			}

			// Show a user the ways out of this place.
			List<Exit> exits = here.getVisibleExits();
			
			for (int i=0; i<exits.size(); i++) {
			    Exit e = exits.get(i);
			    if(e.getOpen(timer.hour)) {
				System.out.println(" ["+i+"] " + e.getDescription());
			    }
			}

			// Figure out what the user wants to do, for now, only "quit" is special.
			List<String> words = input.getUserWords(">");
			if (words.size() == 0) {
				System.out.println("Must type something!");
				continue;
			} else if (words.size() > 1) {
				System.out.println("Only give me 1 word at a time!");
				continue;
			}
			
			// Get the word they typed as lowercase, and no spaces.
			String action = words.get(0).toLowerCase().trim();
			
			if (action.equals("quit") || action.equals("q") || action.equals("escape")) {
				if (input.confirm("Are you sure you want to quit?")) {
					break;
				} else {
					continue;
				}
			}
			
			/*
			 * Check the words against the possible actions Search and Rest
			 */
			
			// Reveals hidden doors
			if (action.equals("search")) {
				here.search();
				continue;
				}
			
			// Skips time forward one hour (Decreased from two to make time-sensitive exits easier)
			if (action.contentEquals("rest")) {
				timer.increaseHour();
				continue;
			}
			
			
			// From here on out, what they typed better be a number!
			Integer exitNum = null;
			try {
				exitNum = Integer.parseInt(action);
			} catch (NumberFormatException nfe) {
				System.out.println("That's not something I understand! Try a number!");
				continue;
			}
			
			if (exitNum < 0 || exitNum > exits.size()) {
				System.out.println("I don't know what to do with that number!");
				continue;
			}

			// Move to the room they indicated.
			Exit destination = exits.get(exitNum);
			place = destination.getTarget();
			timer.increaseHour();
		}

		// You get here by "quit" or by reaching a Terminal Place.
		System.out.println(">>> GAME OVER <<<");
		System.out.println("You spent "+ timer.totalTime + " hours in the mansion!");
	}

}
