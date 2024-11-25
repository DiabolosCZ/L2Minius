package l2minius.gameserver.instancemanager.achievements_engine.conditions;

import l2minius.gameserver.instancemanager.achievements_engine.base.Condition;
import l2minius.gameserver.model.Player;

public class ClanLeader extends Condition
{
	public ClanLeader(Object value)
	{
		super(value);
		setName("Be Clan Leader");
	}

	@Override
	public boolean meetConditionRequirements(Player player)
	{
		if (getValue() == null)
		{
			return false;
		}

		if (player.getClan() != null)
		{
			if (player.isClanLeader())
			{
				return true;
			}
		}

		return false;
	}
}