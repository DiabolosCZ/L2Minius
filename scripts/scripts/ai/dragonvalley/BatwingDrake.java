package ai.dragonvalley;

import l2minius.commons.util.Rnd;
import l2minius.gameserver.Config;
import l2minius.gameserver.ai.CtrlEvent;
import l2minius.gameserver.ai.Mystic;
import l2minius.gameserver.model.Creature;
import l2minius.gameserver.model.instances.NpcInstance;
import l2minius.gameserver.utils.NpcUtils;

/**
 * @author FandC
 *
 * Batwing Drake(22827)
 * Spawns leeches (22828) when attacked.
 */
public class BatwingDrake extends Mystic
{

	public BatwingDrake(NpcInstance actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int damage)
	{
		if (Rnd.chance(Config.BDRAKE_MS_CHANCE))
		{
			NpcInstance actor = getActor();
			NpcInstance n = NpcUtils.spawnSingle(22828, (actor.getX() + Rnd.get(-100, 100)), (actor.getY() + Rnd.get(-100, 100)), actor.getZ());
			n.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, attacker, 2);
		}
		super.onEvtAttacked(attacker, damage);
	}
}
