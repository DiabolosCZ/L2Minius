package l2minius.gameserver.listener.zone;

import l2minius.commons.listener.Listener;
import l2minius.gameserver.model.Creature;
import l2minius.gameserver.model.Zone;

public interface OnZoneEnterLeaveListener extends Listener<Zone>
{
	public void onZoneEnter(Zone zone, Creature actor);

	public void onZoneLeave(Zone zone, Creature actor);

	public void onEquipChanged(Zone zone, Creature actor);
}
