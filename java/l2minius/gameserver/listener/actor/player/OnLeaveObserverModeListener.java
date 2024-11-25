package l2minius.gameserver.listener.actor.player;

import l2minius.gameserver.listener.PlayerListener;
import l2minius.gameserver.model.Player;

public interface OnLeaveObserverModeListener extends PlayerListener
{
	void onLeaveObserverMode(Player p0);
}
