package ai.freya;

import bosses.AntharasManager;
import l2minius.gameserver.ai.CtrlEvent;
import l2minius.gameserver.ai.Fighter;
import l2minius.gameserver.model.Creature;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.instances.NpcInstance;
import l2minius.gameserver.tables.SkillTable;

public class AntharasMinion extends Fighter
{
	public AntharasMinion(NpcInstance actor)
	{
		super(actor);
		actor.startDebuffImmunity();
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		for (Player p : AntharasManager.getZone().getInsidePlayers())
		{
			notifyEvent(CtrlEvent.EVT_AGGRESSION, p, 5000);
		}
	}

	@Override
	protected void onEvtDead(Creature killer)
	{
		getActor().doCast(SkillTable.getInstance().getInfo(5097, 1), getActor(), true);
		super.onEvtDead(killer);
	}
}