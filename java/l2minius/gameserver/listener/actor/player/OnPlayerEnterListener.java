package l2minius.gameserver.listener.actor.player;

import l2minius.gameserver.listener.PlayerListener;
import l2minius.gameserver.model.Player;

public interface OnPlayerEnterListener extends PlayerListener
{
	public void onPlayerEnter(Player player);
}
