package l2minius.gameserver.skills.skillclasses;

import java.util.List;

import l2minius.gameserver.ai.CtrlIntention;
import l2minius.gameserver.model.Creature;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.Skill;
import l2minius.gameserver.model.instances.NpcInstance;
import l2minius.gameserver.network.serverpackets.components.CustomMessage;
import l2minius.gameserver.stats.Formulas;
import l2minius.gameserver.templates.StatsSet;

public class DeleteHateOfMe extends Skill
{
	public DeleteHateOfMe(StatsSet set)
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
				if (activeChar.isPlayer() && ((Player) activeChar).isGM())
				{
					activeChar.sendMessage(new CustomMessage("l2minius.gameserver.skills.Formulas.Chance", (Player) activeChar).addString(getName()).addNumber(getActivateRate()));
				}

				if (target.isNpc() && Formulas.calcSkillSuccess(activeChar, target, this, getActivateRate()))
				{
					NpcInstance npc = (NpcInstance) target;
					npc.getAggroList().remove(activeChar, true);
					npc.getAI().setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
				}
				getEffects(activeChar, target, true, false);
			}
		}
	}
}