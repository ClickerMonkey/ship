package net.philsprojects.game;

import net.philsprojects.game.util.BoundingBox;

/**
 * An interface to an entity that lives on a TiledLayer. Has all the information required
 *      for the TiledLayer to make all of its calculations specifically for certain entities.
 *      
 * @author Philip Diffenderfer
 */
public interface ITiledEntity extends IUpdate, IDraw
{

	/**
	 * Used to make sure that no entity is checked for collision with another
	 *      entity in its group. Only applies for when you allow certain
	 *      entities to intersect each other but not certain entities.
	 * 
	 * @return The group identification which is some predefined value.
	 */
	public String getGroupID();

	/**
	 * Tiled Layer is trying to remove it because its disabled or its not in the world 
	 *      boundaries, to resist this should return false, else return true.
	 */
	public boolean removingEntity();

	/**
	 * Used to tell the TiledLayer class whether to draw this entity or let it draw it self.
	 * 
	 * @return True if this entity draws it self, false if the TiledLayer draws it for them.
	 */
	public boolean isUserDrawn();

	/**
	 * Used to determine whether this entity will be checked for collisions with
	 *      a tiled element;
	 * 
	 * @return True if this entity collides with tiles, false if not.
	 */
	public boolean acceptsTileHit();

	/**
	 * Used to determine whether this entity will be checked for collisions with 
	 *      another entity.
	 *      
	 * @return True if this entity collides with other entities, false if not.
	 */
	public boolean acceptsEntityHit();

	/**
	 * Used to determine whether this entity will correct the other entities position
	 *      if they intersect.
	 *      
	 * @return True if this entity corrects another entities bounds at collision.
	 */
	public boolean correctsIntersection();

	/**
	 * Should return a calculated bounding box that fits correctly around this entity.
	 * 
	 * @return The bounding box around this entity.
	 */
	public BoundingBox getBounds();

	/**
	 * Returns the last frames calculated bounds, used to properly handle collisions with tiles.
	 * 
	 * @return The last frames bounding box that was around the entity.
	 */
	public BoundingBox getLastBounds();

	/**
	 *  Is set by the TiledLayer class when this entity intersects with a tile or
	 *      another entity where this entities right overlaps a left.
	 *      
	 * @param right => The new right of this entity.
	 */
	public void setRight(float right);

	/**
	 * Is set by the TiledLayer class when this entity intersects with a tile or
	 *      another entity where this entities left overlaps a right.
	 *       
	 * @param left => The new left of this entity.
	 */
	public void setLeft(float left);

	/**
	 * Is set by the TiledLayer class when this entity intersects with a tile or
	 *      another entity where this entities top overlaps a bottom. 
	 * 
	 * @param top => The new top of this entity.
	 */
	public void setTop(float top);

	/**
	 * Is set by the TiledLayer class when this entity intersects with a tile or
	 *      another entity where this entities bottom overlaps a top.
	 * 
	 * @param bottom => The new bottom of this entity.
	 */
	public void setBottom(float bottom);

	/**
	 * Is triggered by the TiledLayer class and occurs when this entity hits a tile
	 *      that accepts intersections and it has hit a side of the tile thats not
	 *      blocked.
	 * 
	 * @param element => The tiled element that this entity hit.
	 * @param x => The X index of the element on the TiledLayer.
	 * @param y => The Y index of the element on the TiledLayer.
	 * @param hitType => The side that was hit on this entity, either HIT_TOP, HIT_LEFT, HIT_RIGHT, or HIT_BOTTOM.
	 */
	public void hitTile(TiledElement element, int x, int y, int hitType);

	/**
	 * Is triggered by the TiledLayer class and occurs when this entity hits another
	 *      entity that accepts intersections.
	 *      
	 * @param entity => The entity that this entity has hit.
	 * @param hitType => The side that was hit on this entity, either HIT_TOP, HIT_LEFT, HIT_RIGHT, or HIT_BOTTOM.
	 */
	public void hitEntity(ITiledEntity entity, int hitType);

}
