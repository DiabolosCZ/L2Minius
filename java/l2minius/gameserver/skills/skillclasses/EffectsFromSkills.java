package l2minius.gameserver.skills.skillclasses;

import java.util.List;

import l2minius.gameserver.model.Creature;
import l2minius.gameserver.model.Skill;
import l2minius.gameserver.templates.StatsSet;

public class EffectsFromSkills extends Skill
{
	public EffectsFromSkills(StatsSet set)
	{
		super(set);
	}

	@Override
	public void useSkill(Creature activeChar, List<Creature> targets)
	{
		for (Creature target : targets)
		{
			if (target != null)
			{
				for (AddedSkill as : getAddedSkills())
				{
					as.getSkill().getEffects(activeChar, target, false, false);
				}
			}
		}
	}
}