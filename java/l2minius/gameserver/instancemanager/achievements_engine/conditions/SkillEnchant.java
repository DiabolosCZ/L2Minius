package l2minius.gameserver.instancemanager.achievements_engine.conditions;

import l2minius.gameserver.instancemanager.achievements_engine.base.Condition;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.Skill;

public class SkillEnchant extends Condition
{
	public SkillEnchant(Object value)
	{
		super(value);
		setName("Skill Enchant");
	}

	@Override
	public boolean meetConditionRequirements(Player player)
	{
		if (getValue() == null)
		{
			return false;
		}

		int val = Integer.parseInt(getValue().toString());

		for (Skill s : player.getAllSkills())
		{
			String lvl = String.valueOf(s.getLevel());
			if (lvl.length() > 2)
			{
				int sklvl = Integer.parseInt(lvl.substring(1));
				if (sklvl >= val)
				{
					return true;
				}
			}
		}

		return false;
	}
}