package ai.residences.fortress.siege;

import ai.residences.SiegeGuardFighter;
import l2minius.gameserver.model.Creature;
import l2minius.gameserver.model.instances.NpcInstance;
import l2minius.gameserver.network.serverpackets.components.NpcString;
import l2minius.gameserver.scripts.Functions;

/**
 * @author VISTALL
 * @date 20:10/19.04.2011
 */
public class RebelCommander extends SiegeGuardFighter
{
	public RebelCommander(NpcInstance actor)
	{
		super(actor);
	}

	@Override
	public void onEvtDead(Creature killer)
	{
		super.onEvtDead(killer);

		Functions.npcSay(getActor(), NpcString.DONT_THINK_THAT_ITS_GONNA_END_LIKE_THIS);
	}
}
