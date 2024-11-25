package ai.hellbound;

import l2minius.gameserver.ai.Fighter;
import l2minius.gameserver.instancemanager.naia.NaiaCoreManager;
import l2minius.gameserver.model.Creature;
import l2minius.gameserver.model.instances.NpcInstance;

public class Epidos extends Fighter
{

	public Epidos(NpcInstance actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtDead(Creature killer)
	{
		NaiaCoreManager.removeSporesAndSpawnCube();
		super.onEvtDead(killer);
	}
}