package ai.hellbound;

import l2minius.commons.util.Rnd;
import l2minius.gameserver.ai.CtrlIntention;
import l2minius.gameserver.ai.Fighter;
import l2minius.gameserver.model.Creature;
import l2minius.gameserver.model.instances.NpcInstance;
import l2minius.gameserver.scripts.Functions;

public class TownGuard extends Fighter
{
	public TownGuard(NpcInstance actor)
	{
		super(actor);
	}

	@Override
	protected void onIntentionAttack(Creature target)
	{
		NpcInstance actor = getActor();
		if (getIntention() == CtrlIntention.AI_INTENTION_ACTIVE && Rnd.chance(50))
		{
			Functions.npcSay(actor, "Invader!");
		}
		super.onIntentionAttack(target);
	}
}