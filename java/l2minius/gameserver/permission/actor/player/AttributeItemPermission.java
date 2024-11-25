package l2minius.gameserver.permission.actor.player;

import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.items.ItemInstance;
import l2minius.gameserver.permission.PlayablePermission;

public interface AttributeItemPermission extends PlayablePermission
{
	boolean canAttributeItem(Player p0, ItemInstance p1, ItemInstance p2);

	void sendPermissionDeniedError(Player p0, ItemInstance p1, ItemInstance p2);
}
