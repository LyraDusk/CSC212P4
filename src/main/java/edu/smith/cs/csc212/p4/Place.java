package edu.smith.cs.csc212.p4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This represents a place in our text adventure.
 * @author jfoley
 *
 */
public class Place {
	/**
	 * This is a list of places we can get to from this place.
	 */
	private List<Exit> exits;
	
	/*
	 * This is a list of visible exits
	 */
	
	private List<Exit> visibleExits;
	
	/**
	 * This is the identifier of the place.
	 */
	private String id;
	/**
	 * What to tell the user about this place.
	 */
	public String description;
	
	/*
	 * Alternate description for places that change 
	 */
	public String altdesc;
	
	
	/**
	 * Whether reaching this place ends the game.
	 */
	private boolean terminal;
	
	
	
	/**
	 * Internal only constructor for Place. Use {@link #create(String, String)} or {@link #terminal(String, String)} instead.
	 * @param id - the internal id of this place.
	 * @param description - the user-facing description of the place.
	 * @param terminal - whether this place ends the game.
	 */
	public Place(String id, String description, boolean terminal) {
		this.id = id;
		this.description = description;
		this.exits = new ArrayList<>();
		this.visibleExits = new ArrayList<>();
		this.terminal = terminal;
		
	}
	
	/*
	 * Adds an alternate (nighttime) description to a place
	 */
	public void addAltDesc(String altdesc) {
		this.altdesc = altdesc;
	}
	
	
	
	/**
	 * Create an exit for the user to navigate to another Place.
	 * @param exit - the description and target of the other Place.
	 */
	public void addExit(Exit exit) {
		this.exits.add(exit);
		this.visibleExits.add(exit);
		
	}
	
	/*
	 * Create a secret exit
	 */
	
	public void addSecretExit(Exit exit) {
		this.exits.add(exit);
	}
	
	
	/**
	 * For gameplay, whether this place ends the game.
	 * @return true if this is the end.
	 */
	public boolean isTerminalState() {
		return this.terminal;
	}
	
	/**
	 * The internal id of this place, for referring to it in {@link Exit} objects.
	 * @return the id.
	 */
	public String getId() {
		return this.id;
	}
	
	/**
	 * The narrative description of this place.
	 * @return what we show to a player about this place.
	 */
	public String getDescription(boolean day) {
		if(!day && this.altdesc != null) {
			return this.altdesc;
		} else {
			return this.description;
		}
	}

	/**
	 * Get a view of the visible exits from this Place, for navigation.
	 * @return all the exits from this place.
	 */
	public List<Exit> getVisibleExits() {
		return Collections.unmodifiableList(visibleExits);
	}
	
	/*
	 * Returns all exits, not just the visible ones
	 */
	public List<Exit> getAllExits() {
		return Collections.unmodifiableList(exits);
	}
	
	/**
	 * This is a terminal location (good or bad).
	 * @param id - this is the id of the place (for creating {@link Exit} objects that go here).
	 * @param description - this is the description of the place.
	 * @return the Place object.
	 */
	public static Place terminal(String id, String description) {
		return new Place(id, description, true);
	}
	
	/**
	 * Create a place with an id and description.
	 * @param id - this is the id of the place (for creating {@link Exit} objects that go here).
	 * @param description - this is what we show to the user.
	 * @return the new Place object (add exits to it).
	 */
	public static Place create(String id, String description) {
		return new Place(id, description, false);
	}
	
	/**
	 * Implements what we need to put Place in a HashSet or HashMap.
	 */
	public int hashCode() {
		return this.id.hashCode();
	}
	
	/*
	 * Reveals all hidden exits
	 */
	public void search() {
		for(Exit e: exits) {
			if(!visibleExits.contains(e)) {
			e.search();
			visibleExits.add(e);
			}
			
		}
	}
	
	/**
	 * Give a string for debugging what place is what.
	 */
	public String toString() {
		return "Place("+this.id+" with "+this.exits.size()+" exits.)";
	}
	
	/**
	 * Whether this is the same place as another.
	 */
	public boolean equals(Object other) {
		if (other instanceof Place) {
			return this.id.equals(((Place) other).id);
		}
		return false;
	}
	
}
