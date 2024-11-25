package l2minius.gameserver.listener.actor.player;

import l2minius.gameserver.listener.PlayerListener;
import l2minius.gameserver.model.Player;

public interface OnPlayerExitListener extends PlayerListener
{
	public void onPlayerExit(Player player);
}
