package l2minius.gameserver.handler.items;

import org.apache.commons.lang3.ArrayUtils;

import l2minius.gameserver.model.Playable;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.items.ItemInstance;
import l2minius.gameserver.utils.Location;

public interface IItemHandler
{
	public static final IItemHandler NULL = new IItemHandler()
	{
		@Override
		public boolean useItem(Playable playable, ItemInstance item, boolean ctrl)
		{
			return false;
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
		}

		@Override
		public boolean pickupItem(Playable playable, ItemInstance item)
		{
			return true;
		}

		@Override
		public int[] getItemIds()
		{
			return ArrayUtils.EMPTY_INT_ARRAY;
		}
	};

	public boolean useItem(Playable playable, ItemInstance item, boolean ctrl);

	public void dropItem(Player player, ItemInstance item, long count, Location loc);

	public boolean pickupItem(Playable playable, ItemInstance item);

	public int[] getItemIds();
}
