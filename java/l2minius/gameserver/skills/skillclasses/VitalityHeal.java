package l2minius.gameserver.skills.skillclasses;

import java.util.List;

import l2minius.gameserver.Config;
import l2minius.gameserver.model.Creature;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.Skill;
import l2minius.gameserver.templates.StatsSet;

public class VitalityHeal extends Skill
{
	public VitalityHeal(StatsSet set)
	{
		super(set);
	}

	@Override
	public void useSkill(Creature activeChar, List<Creature> targets)
	{
		int fullPoints = Config.VITALITY_LEVELS[4];
		double percent = _power;

		for (Creature target : targets)
		{
			if (target.isPlayer())
			{
				Player player = target.getPlayer();
				double points = fullPoints / 100 * percent;
				player.addVitality(points);
			}
			getEffects(activeChar, target, getActivateRate() > 0, false);
		}

		if (isSSPossible())
		{
			activeChar.unChargeShots(isMagic());
		}
	}
}