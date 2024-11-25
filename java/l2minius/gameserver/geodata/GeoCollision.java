package l2minius.gameserver.geodata;

import l2minius.commons.geometry.Shape;

public interface GeoCollision
{
	public Shape getShape();

	public byte[][] getGeoAround();

	public void setGeoAround(byte[][] geo);

	public boolean isConcrete();
}
