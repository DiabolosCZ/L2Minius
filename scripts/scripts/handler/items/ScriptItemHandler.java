package handler.items;

import l2minius.gameserver.handler.items.IItemHandler;
import l2minius.gameserver.model.Playable;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.entity.CCPHelpers.itemLogs.ItemActionType;
import l2minius.gameserver.model.entity.CCPHelpers.itemLogs.ItemLogHandler;
import l2minius.gameserver.model.items.ItemInstance;
import l2minius.gameserver.utils.Location;

public abstract class ScriptItemHandler implements IItemHandler
{
	@Override
	public boolean pickupItem(Playable playable, ItemInstance item)
	{
		return true;
	}

	@Override
	public void dropItem(Player player, ItemInstance item, long count, Location loc)
	{
		if (item.isEquipped())
		{
			player.getInventory().unEquipItem(item);
			player.sendUserInfo(true);
		}

		item = player.getInventory().removeItemByObjectId(item.getObjectId(), count, "DropItem");
		if (item == null)
		{
			player.sendActionFailed();
			return;
		}

		item.dropToTheGround(player, loc);
		player.disableDrop(1000);

		player.sendChanges();

		ItemLogHandler.getInstance().addLog(player, item, count, ItemActionType.DROPPED_ON_PURPOSE);
	}
}
