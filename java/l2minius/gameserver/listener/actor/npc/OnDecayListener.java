package l2minius.gameserver.listener.actor.npc;

import l2minius.gameserver.listener.NpcListener;
import l2minius.gameserver.model.instances.NpcInstance;

public interface OnDecayListener extends NpcListener
{
	public void onDecay(NpcInstance actor);
}
