package l2minius.gameserver.instancemanager.achievements_engine.conditions;

import l2minius.gameserver.instancemanager.achievements_engine.base.Condition;
import l2minius.gameserver.model.Player;

public class Karma extends Condition
{
	public Karma(Object value)
	{
		super(value);
		setName("Karma Count");
	}

	@Override
	public boolean meetConditionRequirements(Player player)
	{
		if (getValue() == null)
		{
			return false;
		}

		int val = Integer.parseInt(getValue().toString());

		if (player.getKarma() >= val)
		{
			return true;
		}

		return false;
	}
}