package l2minius.gameserver.listener.actor.player;

import l2minius.gameserver.listener.PlayerListener;
import l2minius.gameserver.model.Player;

public interface OnQuestionMarkListener extends PlayerListener
{
	public void onQuestionMarkClicked(Player player, int questionMarkId);
}
