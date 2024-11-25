package l2minius.gameserver.listener.actor.player;

import l2minius.gameserver.listener.PlayerListener;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.entity.Reflection;

public interface OnTeleportListener extends PlayerListener
{
	public void onTeleport(Player player, int x, int y, int z, Reflection reflection);
}
