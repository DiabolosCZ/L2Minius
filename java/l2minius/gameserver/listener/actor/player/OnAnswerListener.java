package l2minius.gameserver.listener.actor.player;

import l2minius.gameserver.listener.PlayerListener;

public interface OnAnswerListener extends PlayerListener
{
	void sayYes();

	void sayNo();
}
