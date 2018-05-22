package net.philsprojects.game;

public interface ITiledLayerFactory
{

	public Object createObject(String source);

	public TiledElement createTile(String source);

	public TiledElementInstance createTileInstance(String source);

	public void interpretExtraLines(String[] line);

	public void finalizeLoading(TiledLayer layer);


}
