package l2minius.gameserver.listener.item;

import l2minius.gameserver.listener.PlayerListener;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.items.ItemInstance;

public interface OnItemEnchantListener extends PlayerListener
{
	public void onEnchantFinish(Player player, ItemInstance item, boolean succeed);
}
