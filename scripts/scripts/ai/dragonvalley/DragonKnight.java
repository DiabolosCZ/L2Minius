package ai.dragonvalley;

import l2minius.commons.util.Rnd;
import l2minius.gameserver.ai.CtrlEvent;
import l2minius.gameserver.ai.Fighter;
import l2minius.gameserver.model.Creature;
import l2minius.gameserver.model.instances.NpcInstance;
import l2minius.gameserver.utils.NpcUtils;

public class DragonKnight extends Fighter
{
	public DragonKnight(NpcInstance actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtDead(Creature killer)
	{
		super.onEvtDead(killer);
		switch (getActor().getNpcId())
		{
		case 22844:
			if (Rnd.chance(50))
			{
				NpcInstance n = NpcUtils.spawnSingle(22845, getActor().getLoc());
				n.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, killer, 2);
			}
			break;
		case 22845:
			if (Rnd.chance(50))
			{
				NpcInstance n = NpcUtils.spawnSingle(22846, getActor().getLoc());
				n.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, killer, 2);
			}
			break;
		}

	}
}