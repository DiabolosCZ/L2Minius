package ai.Zone.DragonValley.DV_RB;

import l2minius.commons.util.Rnd;
import l2minius.gameserver.ai.Fighter;
import l2minius.gameserver.model.Creature;
import l2minius.gameserver.model.Skill;
import l2minius.gameserver.model.instances.NpcInstance;
import l2minius.gameserver.tables.SkillTable;

/**
 * @author FandC
 */

public class SpikeSlasherMinion extends Fighter
{

	private Skill paralysis = SkillTable.getInstance().getInfo(6878, 1);

	private long last_cast_anchor = 0;

	public SpikeSlasherMinion(NpcInstance actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int damage)
	{
		NpcInstance actor = getActor();
		if (last_cast_anchor < System.currentTimeMillis())
		{
			actor.doCast(paralysis, attacker, true);
			last_cast_anchor = System.currentTimeMillis() + Rnd.get(5, 10) * 1000;
		}
		super.onEvtAttacked(attacker, damage);
	}

}
