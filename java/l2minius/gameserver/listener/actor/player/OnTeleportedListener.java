package l2minius.gameserver.listener.actor.player;

import l2minius.gameserver.listener.PlayerListener;
import l2minius.gameserver.model.Player;

public interface OnTeleportedListener extends PlayerListener
{
	void onTeleported(Player p0);
}
