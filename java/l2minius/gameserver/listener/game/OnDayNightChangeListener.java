package l2minius.gameserver.listener.game;

import l2minius.gameserver.listener.GameListener;

public interface OnDayNightChangeListener extends GameListener
{
	public void onDay();

	public void onNight();
}
