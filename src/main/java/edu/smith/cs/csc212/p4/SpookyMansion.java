package edu.smith.cs.csc212.p4;

import java.util.HashMap;
import java.util.Map;

/**
 * SpookyMansion, the game.
 * @author jfoley
 *
 */
public class SpookyMansion implements GameWorld {
	private Map<String, Place> places = new HashMap<>();
	
	/**
	 * Where should the player start?
	 */
	@Override
	public String getStart() {
		return "entranceHall";
	}

	/**
	 * This constructor builds our SpookyMansion game.
	 */
	public SpookyMansion() {
		Place entranceHall = insert(
				Place.create("entranceHall", "You are in the grand entrance hall of a large building.\n"
						+ "The front door is locked. How did you get here?"));
		entranceHall.addExit(new Exit("basement", "There are stairs leading down."));
		entranceHall.addExit(new Exit("attic", "There are stairs leading up."));
		entranceHall.addExit(new Exit("kitchen", "There is a red door."));
		Exit frontdoortemp = new Exit("frontdoor","The front door seems to have opened on its own.");
		frontdoortemp.setTime(9);
		entranceHall.addExit(frontdoortemp);
		
		Place basement = insert(
				Place.create("basement", "You have found the basement of the mansion.\n" + 
		                           "It is darker down here.\n" +
						"You get the sense a secret is nearby, but you only see the stairs you came from."
						));
		basement.addExit(new Exit("entranceHall", "There are stairs leading up."));
		basement.addExit(new Exit("cellar","There is an old wooden door."));
		basement.addSecretExit(new SecretExit("secretRoom","You find a secret door in the wall."));
		
		Place cellar = insert(Place.create("cellar", "The cellar is full of crates. You feel like you're being watched, \n"
				+ "but it's probably just the rats."));
		cellar.addExit(new Exit("basement","The old door leads back to the basement."));
		//Setting a timed exit requires a bit more variable juggling, but it works
		Exit laddertemp = new Exit("ladder","A trapdoor appears in the ceiling.");
		laddertemp.setTime(0);
		cellar.addExit(laddertemp);
		
		
		Place attic = insert(Place.create("attic",
				"Something rustles in the rafters as you enter the attic. Creepy.\n" + "It's big up here."));
		attic.addExit(new Exit("entranceHall", "There are stairs leading down."));
		attic.addExit(new Exit("attic2", "There is more through an archway."));
		attic.addExit(new Exit("window","There's a window in the wall."));

		Place attic2 = insert(Place.create("attic2", "There's definitely a bat in here somewhere.\n"
				+ "This part of the attic is brighter, so maybe you're safe here."));
		attic2.addExit(new Exit("attic", "There is more back through the archway"));
		Exit attictemp = new Exit("porch","There is a door in the wall where a moment ago there was nothing.");
		attictemp.setTime(17);
		attic2.addExit(attictemp);
		
		Place atticwindow = insert(Place.create("window",
				"The window looks out over the yard. The sun peeks over the trees."));
		atticwindow.addExit(new Exit("attic","Go back."));
		atticwindow.addAltDesc("The window looks out over the yard. It is dark outside.");
		
		Place kitchen = insert(Place.create("kitchen", "You've found the kitchen. You smell old food and some kind of animal."));
		kitchen.addExit(new Exit("entranceHall", "There is a red door."));
		kitchen.addExit(new Exit("dumbwaiter", "There is a dumbwaiter."));
		
		Place dumbwaiter = insert(Place.create("dumbwaiter", "You crawl into the dumbwaiter. What are you doing?"));
		dumbwaiter.addExit(new Exit("secretRoom", "Take it to the bottom."));
		dumbwaiter.addExit(new Exit("kitchen", "Take it to the middle-level."));
		dumbwaiter.addExit(new Exit("attic2", "Take it up to the top."));
		
		Place secretRoom = insert(Place.create("secretRoom", "You have found the secret room."));
		secretRoom.addExit(new Exit("hallway0", "There is a long hallway."));
		secretRoom.addExit(new Exit("basement", "There is an opening in the wall."));
		
		int hallwayDepth = 5;
		int lastHallwayPart = hallwayDepth - 1;
		for (int i=0; i<hallwayDepth; i++) {
			Place hallwayPart = insert(Place.create("hallway"+i, "This is a very long hallway. You see a " + i + " scratched on the wall."));
			if (i == 0) {
				hallwayPart.addExit(new Exit("secretRoom", "Go back."));
			} else {
				hallwayPart.addExit(new Exit("hallway"+(i-1), "Go back."));
			}
			if (i != lastHallwayPart) {
				hallwayPart.addExit(new Exit("hallway"+(i+1), "Go forward."));
			} else {
				hallwayPart.addExit(new Exit("crypt", "There is darkness ahead."));
			}
		}
		
		Place crypt = insert(Place.terminal("crypt", "You have found the crypt.\n"
				+"It is scary here, but light is streaming down from the way out.\n"+
				"Maybe you'll be safe out there."));
		crypt.addAltDesc("You have found the crypt.\n"
				+"It is scary here, a door leads out into the night.\n"+
				"Maybe you'll be safe out there.");
		
		Place ladder = insert(Place.terminal("ladder", "A ladder leads up through the trapdoor. \nYou see stars above.\n" + 
		"Maybe a way out?"));
		
		
		Place frontDoor = insert(Place.terminal("frontdoor", "The front door swings open, as if releasing you.\n + "
				+ "A ray of sunlight bathes the front yard as you walk away."));
				
		Place porch = insert(Place.terminal("porch", "The door leads to a porch outside.\n"+
		"As you look behind you, the door vanishes into the wall as the house releases you."));
				
		// Make sure your graph makes sense!
		checkAllExitsGoSomewhere();
	}

	/**
	 * This helper method saves us a lot of typing. We always want to map from p.id
	 * to p.
	 * 
	 * @param p - the place.
	 * @return the place you gave us, so that you can store it in a variable.
	 */
	private Place insert(Place p) {
		places.put(p.getId(), p);
		return p;
	}


	/**
	 * I like this method for checking to make sure that my graph makes sense!
	 */
	private void checkAllExitsGoSomewhere() {
		boolean missing = false;
		// For every place:
		for (Place p : places.values()) {
			// For every exit from that place:
			for (Exit x : p.getVisibleExits()) {
				// That exit goes to somewhere that exists!
				if (!places.containsKey(x.getTarget())) {
					// Don't leave immediately, but check everything all at once.
					missing = true;
					// Print every exit with a missing place:
					System.err.println("Found exit pointing at " + x.getTarget() + " which does not exist as a place.");
				}
			}
		}
		
		// Now that we've checked every exit for every place, crash if we printed any errors.
		if (missing) {
			throw new RuntimeException("You have some exits to nowhere!");
		}
	}

	/**
	 * Get a Place object by name.
	 */
	public Place getPlace(String id) {
		return this.places.get(id);		
	}
}
