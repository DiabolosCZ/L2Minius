package l2minius.gameserver.stats.conditions;

import l2minius.gameserver.model.entity.Reflection;
import l2minius.gameserver.stats.Env;

/**
 * @author VISTALL
 */
public class ConditionPlayerInstanceZone extends Condition
{
	private final int _id;

	public ConditionPlayerInstanceZone(int id)
	{
		_id = id;
	}

	@Override
	protected boolean testImpl(Env env)
	{
		Reflection ref = env.character.getReflection();

		return ref.getInstancedZoneId() == _id;
	}
}