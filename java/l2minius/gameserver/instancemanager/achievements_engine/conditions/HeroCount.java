package l2minius.gameserver.instancemanager.achievements_engine.conditions;

import l2minius.gameserver.instancemanager.achievements_engine.base.Condition;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.entity.Hero;
import l2minius.gameserver.model.entity.olympiad.Olympiad;
import l2minius.gameserver.templates.StatsSet;

public class HeroCount extends Condition
{
	public HeroCount(Object value)
	{
		super(value);
		setName("Hero Count");
	}

	@Override
	public boolean meetConditionRequirements(Player player)
	{
		if (getValue() == null)
		{
			return false;
		}

		int val = Integer.parseInt(getValue().toString());
		for (int hero : Hero.getInstance().getHeroes().keySet())
		{
			if (hero == player.getObjectId())
			{
				StatsSet sts = Hero.getInstance().getHeroes().get(hero);
				if (sts.getString(Olympiad.CHAR_NAME).equals(player.getName()))
				{
					if (sts.getInteger(Hero.COUNT) >= val)
					{
						return true;
					}
				}
			}
		}
		return false;
	}
}