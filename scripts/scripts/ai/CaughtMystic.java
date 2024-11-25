package ai;

import l2minius.commons.util.Rnd;
import l2minius.gameserver.ai.Mystic;
import l2minius.gameserver.model.Creature;
import l2minius.gameserver.model.instances.NpcInstance;
import l2minius.gameserver.scripts.Functions;

public class CaughtMystic extends Mystic
{
	private static final int TIME_TO_LIVE = 60000;
	private final long TIME_TO_DIE = System.currentTimeMillis() + TIME_TO_LIVE;

	public CaughtMystic(NpcInstance actor)
	{
		super(actor);
	}

	@Override
	public boolean isGlobalAI()
	{
		return true;
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();

		if (Rnd.chance(75))
		{
			Functions.npcSayCustomMessage(getActor(), "scripts.ai.CaughtMob.spawn");
		}
	}

	@Override
	protected void onEvtDead(Creature killer)
	{
		if (Rnd.chance(75))
		{
			Functions.npcSayCustomMessage(getActor(), "scripts.ai.CaughtMob.death");
		}

		super.onEvtDead(killer);
	}

	@Override
	protected boolean thinkActive()
	{
		NpcInstance actor = getActor();
		if (System.currentTimeMillis() >= TIME_TO_DIE)
		{
			actor.deleteMe();
			return false;
		}
		return super.thinkActive();
	}
}
