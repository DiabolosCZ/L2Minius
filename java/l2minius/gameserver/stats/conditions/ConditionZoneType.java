package l2minius.gameserver.stats.conditions;

import l2minius.gameserver.model.Zone.ZoneType;
import l2minius.gameserver.stats.Env;

public class ConditionZoneType extends Condition
{
	private final ZoneType _zoneType;

	public ConditionZoneType(String zoneType)
	{
		_zoneType = ZoneType.valueOf(zoneType);
	}

	@Override
	protected boolean testImpl(Env env)
	{
		if (!env.character.isPlayer())
		{
			return false;
		}
		return env.character.isInZone(_zoneType);
	}
}