package l2minius.gameserver.listener.game;

import l2minius.gameserver.Shutdown;
import l2minius.gameserver.listener.GameListener;

public interface OnAbortShutdownListener extends GameListener
{
	void onAbortShutdown(Shutdown.ShutdownMode p0, int p1);
}
