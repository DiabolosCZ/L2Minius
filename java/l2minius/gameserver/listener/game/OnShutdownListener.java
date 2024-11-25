package l2minius.gameserver.listener.game;

import l2minius.gameserver.Shutdown;
import l2minius.gameserver.listener.GameListener;

public interface OnShutdownListener extends GameListener
{
	void onShutdown(Shutdown.ShutdownMode p0);
}
