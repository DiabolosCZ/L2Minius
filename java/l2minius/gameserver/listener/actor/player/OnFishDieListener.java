package l2minius.gameserver.listener.actor.player;

import l2minius.gameserver.listener.PlayerListener;
import l2minius.gameserver.model.Player;

public interface OnFishDieListener extends PlayerListener
{
	void onFishDied(Player player, int fishId, boolean isMonster);
}
