package ai.hellbound;

import l2minius.gameserver.ai.Fighter;
import l2minius.gameserver.instancemanager.naia.NaiaTowerManager;
import l2minius.gameserver.model.Creature;
import l2minius.gameserver.model.instances.NpcInstance;

public class NaiaSpore extends Fighter
{
	public NaiaSpore(NpcInstance actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtDead(Creature killer)
	{
		NpcInstance actor = getActor();
		NaiaTowerManager.handleEpidosIndex(actor);

		super.onEvtDead(killer);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();

		NpcInstance actor = getActor();
		if (NaiaTowerManager.isEpidosSpawned())
		{
			actor.decayMe();
		}
		else
		{
			NaiaTowerManager.addSpore(actor);
		}
	}
}