package l2minius.gameserver.stats.conditions;

import l2minius.gameserver.stats.Env;
import l2minius.gameserver.utils.PositionUtils;

public class ConditionTargetDirection extends Condition
{
	private final PositionUtils.TargetDirection _dir;

	public ConditionTargetDirection(PositionUtils.TargetDirection direction)
	{
		_dir = direction;
	}

	@Override
	protected boolean testImpl(Env env)
	{
		return PositionUtils.getDirectionTo(env.target, env.character) == _dir;
	}
}
