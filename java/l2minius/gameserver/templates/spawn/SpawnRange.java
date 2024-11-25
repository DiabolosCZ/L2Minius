package l2minius.gameserver.templates.spawn;

import l2minius.gameserver.utils.Location;

public interface SpawnRange
{
	Location getRandomLoc(int geoIndex);
}
