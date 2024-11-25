package ai.freya;

import l2minius.commons.threading.RunnableImpl;
import l2minius.gameserver.ThreadPoolManager;
import l2minius.gameserver.ai.Fighter;
import l2minius.gameserver.model.Creature;
import l2minius.gameserver.model.instances.NpcInstance;
import l2minius.gameserver.tables.SkillTable;

public class Glacier extends Fighter
{
	public Glacier(NpcInstance actor)
	{
		super(actor);
		actor.block();
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		getActor().setNpcState(1);
		ThreadPoolManager.getInstance().schedule(new Freeze(), 800);
		ThreadPoolManager.getInstance().schedule(new Despawn(), 30000L);
	}

	@Override
	protected void onEvtDead(Creature killer)
	{
		for (Creature cha : getActor().getAroundCharacters(350, 100))
		{
			if (cha.isPlayer())
			{
				cha.altOnMagicUseTimer(cha, SkillTable.getInstance().getInfo(6301, 1));
			}
		}

		super.onEvtDead(killer);
	}

	private class Freeze extends RunnableImpl
	{
		@Override
		public void runImpl()
		{
			getActor().setNpcState(2);
		}
	}

	private class Despawn extends RunnableImpl
	{
		@Override
		public void runImpl()
		{
			getActor().deleteMe();
		}
	}
}