package l2minius.gameserver.listener.event;

import l2minius.gameserver.listener.EventListener;
import l2minius.gameserver.model.entity.events.GlobalEvent;

public interface OnStartStopListener extends EventListener
{
	void onStart(GlobalEvent event);

	void onStop(GlobalEvent event);
}
