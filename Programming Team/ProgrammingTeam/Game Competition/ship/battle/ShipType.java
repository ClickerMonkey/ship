package ship.battle;

/**
 * A type for a ship. Each type has a length of the ship in cells.
 * 
 * @author Philip Diffenderfer
 *
 */
public enum ShipType 
{
	/**
	 * An aircraft carrier is a warship designed with a primary mission of 
	 * deploying and recovering aircraft, acting as a seagoing airbase. Aircraft 
	 * carriers thus allow a naval  force to project air power great distances 
	 * without having to depend on local bases for staging aircraft operations. 
	 * They have evolved from wooden vessels, used to deploy balloons, into 
	 * nuclear powered warships that carry dozens of fixed and rotary wing aircraft.
	 */
	AircraftCarrier(5),
	/**
	 * A battleship is a large armored warship  with a main battery consisting of
	 * heavy caliber guns. Battleships were larger, better armed and armored 
	 * than cruisers  and destroyers. As the largest armed ships in a fleet, 
	 * battleships were used to attain command of the sea and represented the 
	 * apex of a nation's naval power from the nineteenth century up until World
	 * War II.
	 */
	Battleship(4),
	/**
	 * In naval terminology, a destroyer is a fast and maneuverable yet 
	 * long-endurance warship intended to escort larger vessels in a fleet, 
	 * convoy  or battle group and defend them against smaller, short-range but 
	 * powerful attackers (originally torpedo boats, later submarines and aircraft).
	 */
	Destroyer(3),
	/**
	 * A submarine is a watercraft  capable of independent operation below the 
	 * surface of the water. It differs from a submersible, which has only 
	 * limited underwater capability.
	 */
	Submarine(3),
	/**
	 * A patrol boat is a small naval vessel generally designed for coastal 
	 * defense duties.
	 */
	PatrolBoat(2);
	
	// The length of the ship in cells.
	public final int length;
	
	/**
	 * Instantiates a ShipType enum.
	 * 
	 * @param length The length of the ship.
	 */
	private ShipType(int length) 
	{
		this.length = length;
	}
	
}
