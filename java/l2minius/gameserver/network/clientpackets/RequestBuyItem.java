package l2minius.gameserver.network.clientpackets;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import l2minius.commons.math.SafeMath;
import l2minius.gameserver.Config;
import l2minius.gameserver.data.xml.holder.BuyListHolder;
import l2minius.gameserver.data.xml.holder.BuyListHolder.NpcTradeList;
import l2minius.gameserver.instancemanager.ReflectionManager;
import l2minius.gameserver.model.Creature;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.entity.residence.Castle;
import l2minius.gameserver.model.instances.NpcInstance;
import l2minius.gameserver.model.items.TradeItem;
import l2minius.gameserver.network.serverpackets.ExBuySellList;
import l2minius.gameserver.network.serverpackets.components.SystemMsg;

/**
 * format:		cddb, b - array of (dd)
 */
public class RequestBuyItem extends L2GameClientPacket
{
	private static final Logger _log = LoggerFactory.getLogger(RequestBuyItem.class);

	private int _listId;
	private int _count;
	private int[] _items;
	private long[] _itemQ;

	@Override
	protected void readImpl()
	{
		this._listId = this.readD();
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
			this._items[i] = this.readD();
			this._itemQ[i] = this.readQ();
			if (this._itemQ[i] < 1)
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
		// Проверяем, не подменили ли id
		if (activeChar == null || this._count == 0 || (activeChar.getBuyListId() != this._listId))
		{
			// TODO audit
			return;
		}

		if (activeChar.isActionsDisabled())
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

		if (activeChar.isFishing())
		{
			activeChar.sendPacket(SystemMsg.YOU_CANNOT_DO_THAT_WHILE_FISHING);
			return;
		}

		if (!Config.ALT_GAME_KARMA_PLAYER_CAN_SHOP && activeChar.getKarma() > 0 && !activeChar.isGM())
		{
			activeChar.sendActionFailed();
			return;
		}

		NpcInstance merchant = activeChar.getLastNpc();
		boolean isValidMerchant = merchant != null && merchant.isMerchantNpc();
		if (!activeChar.isGM() && (merchant == null || !isValidMerchant || !activeChar.isInRange(merchant, Creature.INTERACTION_DISTANCE)))
		{
			activeChar.sendActionFailed();
			return;
		}

		NpcTradeList list = BuyListHolder.getInstance().getBuyList(this._listId);
		if (list == null)
		{
			// TODO audit
			activeChar.sendActionFailed();
			return;
		}

		int slots = 0;
		long weight = 0;
		long totalPrice = 0;
		long tax = 0;
		double taxRate = 0;

		Castle castle = null;
		if (merchant != null)
		{
			castle = merchant.getCastle(activeChar);
			if (castle != null)
			{
				taxRate = castle.getTaxRate();
			}
		}

		List<TradeItem> buyList = new ArrayList<TradeItem>(this._count);
		List<TradeItem> tradeList = list.getItems();
		try
		{
			loop:
			for (int i = 0; i < this._count; i++)
			{
				int itemId = this._items[i];
				long count = this._itemQ[i];
				long price = 0;

				for (TradeItem ti : tradeList)
				{
					if (ti.getItemId() == itemId)
					{
						if (ti.isCountLimited() && ti.getCurrentValue() < count)
						{
							continue loop;
						}
						price = ti.getOwnersPrice();
					}
				}

				if (price == 0 && (!activeChar.isGM() || !activeChar.getPlayerAccess().UseGMShop))
				{
					// TODO audit
					activeChar.sendActionFailed();
					return;
				}

				totalPrice = SafeMath.addAndCheck(totalPrice, SafeMath.mulAndCheck(count, price));

				TradeItem ti = new TradeItem();
				ti.setItemId(itemId);
				ti.setCount(count);
				ti.setOwnersPrice(price);

				weight = SafeMath.addAndCheck(weight, SafeMath.mulAndCheck(count, ti.getItem().getWeight()));
				if (!ti.getItem().isStackable() || activeChar.getInventory().getItemByItemId(itemId) == null)
				{
					slots++;
				}

				buyList.add(ti);
			}

			tax = (long) (totalPrice * taxRate);

			totalPrice = SafeMath.addAndCheck(totalPrice, tax);

			if (!activeChar.getInventory().validateWeight(weight))
			{
				activeChar.sendPacket(SystemMsg.YOU_HAVE_EXCEEDED_THE_WEIGHT_LIMIT);
				return;
			}

			if (!activeChar.getInventory().validateCapacity(slots))
			{
				activeChar.sendPacket(SystemMsg.YOUR_INVENTORY_IS_FULL);
				return;
			}

			if (!activeChar.reduceAdena(totalPrice, "RequestBuyItem"))
			{
				activeChar.sendPacket(SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
				return;
			}

			for (TradeItem ti : buyList)
			{
				activeChar.getInventory().addItem(ti.getItemId(), ti.getCount(), "RequestBuyItem");
			}

			// Для магазинов с ограниченным количеством товара число продаваемых предметов уменьшаем после всех проверок
			list.updateItems(buyList);

			// Add tax to castle treasury if not owned by npc clan
			if (castle != null)
			{
				if (tax > 0 && castle.getOwnerId() > 0 && activeChar.getReflection() == ReflectionManager.DEFAULT)
				{
					castle.addToTreasury(tax, true, false);
				}
			}
		}
		catch (ArithmeticException ae)
		{
			// TODO audit
			activeChar.sendPacket(SystemMsg.YOU_HAVE_EXCEEDED_THE_QUANTITY_THAT_CAN_BE_INPUTTED);
			return;
		}

		this.sendPacket(new ExBuySellList.SellRefundList(activeChar, true));
		activeChar.sendChanges();
	}
}