package l2minius.gameserver.listener.actor.npc;

import l2minius.gameserver.listener.NpcListener;
import l2minius.gameserver.model.instances.NpcInstance;

public interface OnSpawnListener extends NpcListener
{
	public void onSpawn(NpcInstance actor);
}
