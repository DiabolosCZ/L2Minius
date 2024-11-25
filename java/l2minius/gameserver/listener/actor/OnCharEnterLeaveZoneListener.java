package l2minius.gameserver.listener.actor;

import l2minius.gameserver.model.Creature;
import l2minius.gameserver.model.Zone;

public interface OnCharEnterLeaveZoneListener
{
	void onEnter(Creature p0, Zone p1);

	void onLeave(Creature p0, Zone p1);
}
