package l2minius.gameserver.permission.actor.player;

import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.items.ItemInstance;
import l2minius.gameserver.permission.PlayerPermission;

public interface EnchantItemPermission extends PlayerPermission
{
	boolean canEnchantItem(Player p0, ItemInstance p1, ItemInstance p2, ItemInstance p3);

	void sendPermissionDeniedError(Player p0, ItemInstance p1, ItemInstance p2, ItemInstance p3);
}
