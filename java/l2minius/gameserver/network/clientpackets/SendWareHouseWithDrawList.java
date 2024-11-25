package l2minius.gameserver.network.clientpackets;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import l2minius.commons.math.SafeMath;
import l2minius.gameserver.Config;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.items.ItemInstance;
import l2minius.gameserver.model.items.PcInventory;
import l2minius.gameserver.model.items.Warehouse;
import l2minius.gameserver.model.items.Warehouse.WarehouseType;
import l2minius.gameserver.model.pledge.Clan;
import l2minius.gameserver.network.serverpackets.components.SystemMsg;
import l2minius.gameserver.utils.Log;

public class SendWareHouseWithDrawList extends L2GameClientPacket
{
	private static final Logger _log = LoggerFactory.getLogger(SendWareHouseWithDrawList.class);

	private int _count;
	private int[] _items;
	private long[] _itemQ;

	@Override
	protected void readImpl()
	{
		this._count = this.readD();
		if (this._count * 12 > this._buf.remaining() || this._count > Short.MAX_VALUE || this._count < 1)
		{
			this._count = 0;
			return;
		}
		this._items = new int[this._count];
		this._itemQ = new long[this._count];
		for (int i = 0; i < this._count; i++)
		{
			this._items[i] = this.readD(); // item object id
			this._itemQ[i] = this.readQ(); // count
			if (this._itemQ[i] < 1 || ArrayUtils.indexOf(this._items, this._items[i]) < i)
			{
				this._count = 0;
				break;
			}
		}
	}

	@Override
	protected void runImpl()
	{
		Player activeChar = this.getClient().getActiveChar();
		if (activeChar == null || this._count == 0)
		{
			return;
		}

		if (!activeChar.getPlayerAccess().UseWarehouse || activeChar.isActionsDisabled())
		{
			activeChar.sendActionFailed();
			return;
		}

		if (activeChar.isInStoreMode())
		{
			activeChar.sendPacket(SystemMsg.WHILE_OPERATING_A_PRIVATE_STORE_OR_WORKSHOP_YOU_CANNOT_DISCARD_DESTROY_OR_TRADE_AN_ITEM);
			return;
		}

		if (activeChar.isInTrade())
		{
			activeChar.sendActionFailed();
			return;
		}

		Warehouse warehouse = null;
		String logType = null;

		if (activeChar.getUsingWarehouseType() == WarehouseType.PRIVATE)
		{
			if (activeChar.getWithdrawWarehouse() != null)
			{
				warehouse = activeChar.getWithdrawWarehouse();
			}
			else
			{
				warehouse = activeChar.getWarehouse();
			}
			logType = Log.WarehouseWithdraw;
		}
		else if (activeChar.getUsingWarehouseType() == WarehouseType.CLAN)
		{
			logType = Log.ClanWarehouseWithdraw;
			boolean canWithdrawCWH = false;
			if (activeChar.getClan() != null)
			{
				if ((activeChar.getClanPrivileges() & Clan.CP_CL_WAREHOUSE_SEARCH) == Clan.CP_CL_WAREHOUSE_SEARCH && (Config.ALT_ALLOW_OTHERS_WITHDRAW_FROM_CLAN_WAREHOUSE || activeChar.isClanLeader() || activeChar.getVarB("canWhWithdraw")))
				{
					canWithdrawCWH = true;
				}
			}
			if (!canWithdrawCWH && activeChar.getWithdrawWarehouse() == null)
			{
				return;
			}

			if (activeChar.getWithdrawWarehouse() != null)
			{
				warehouse = activeChar.getWithdrawWarehouse();
			}
			else
			{
				warehouse = activeChar.getClan().getWarehouse();
			}
		}
		else if (activeChar.getUsingWarehouseType() == WarehouseType.FREIGHT)
		{
			if (activeChar.getWithdrawWarehouse() != null)
			{
				warehouse = activeChar.getWithdrawWarehouse();
			}
			else
			{
				warehouse = activeChar.getFreight();
			}
			logType = Log.FreightWithdraw;
		}
		else
		{
			_log.warn("Error retrieving a warehouse object for char " + activeChar.getName() + " - using warehouse type: " + activeChar.getUsingWarehouseType());
			return;
		}

		activeChar.setWithdrawWarehouse(null); // Reset the withdraw warehouse for GMs

		PcInventory inventory = activeChar.getInventory();

		inventory.writeLock();
		warehouse.writeLock();
		try
		{
			long weight = 0;
			int slots = 0;

			for (int i = 0; i < this._count; i++)
			{
				ItemInstance item = warehouse.getItemByObjectId(this._items[i]);
				if (item == null || item.getCount() < this._itemQ[i])
				{
					activeChar.sendPacket(SystemMsg.INCORRECT_ITEM_COUNT);
					return;
				}

				weight = SafeMath.addAndCheck(weight, SafeMath.mulAndCheck(item.getTemplate().getWeight(), this._itemQ[i]));
				if (!item.isStackable() || inventory.getItemByItemId(item.getItemId()) == null) // вещь требует слота
				{
					slots++;
				}
			}

			if (!activeChar.getInventory().validateCapacity(slots))
			{
				activeChar.sendPacket(SystemMsg.YOUR_INVENTORY_IS_FULL);
				return;
			}

			if (!activeChar.getInventory().validateWeight(weight))
			{
				activeChar.sendPacket(SystemMsg.YOU_HAVE_EXCEEDED_THE_WEIGHT_LIMIT);
				return;
			}

			for (int i = 0; i < this._count; i++)
			{
				ItemInstance item = warehouse.removeItemByObjectId(this._items[i], this._itemQ[i], null, null);
				activeChar.getInventory().addItem(item, logType);
			}
		}
		catch (ArithmeticException ae)
		{
			// TODO audit
			activeChar.sendPacket(SystemMsg.YOU_HAVE_EXCEEDED_THE_QUANTITY_THAT_CAN_BE_INPUTTED);
			return;
		}
		finally
		{
			warehouse.writeUnlock();
			inventory.writeUnlock();
		}

		activeChar.sendChanges();
		activeChar.sendPacket(SystemMsg.THE_TRANSACTION_IS_COMPLETE);
	}
}