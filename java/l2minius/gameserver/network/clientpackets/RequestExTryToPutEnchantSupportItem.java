package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.items.ItemInstance;
import l2minius.gameserver.model.items.PcInventory;
import l2minius.gameserver.network.serverpackets.ExPutEnchantSupportItemResult;
import l2minius.gameserver.utils.ItemFunctions;

public class RequestExTryToPutEnchantSupportItem extends L2GameClientPacket
{
	private int _itemId;
	private int _catalystId;

	@Override
	protected void readImpl()
	{
		this._catalystId = this.readD();
		this._itemId = this.readD();
	}

	@Override
	protected void runImpl()
	{
		Player activeChar = this.getClient().getActiveChar();
		if (activeChar == null)
		{
			return;
		}

		PcInventory inventory = activeChar.getInventory();
		ItemInstance itemToEnchant = inventory.getItemByObjectId(this._itemId);
		ItemInstance catalyst = inventory.getItemByObjectId(this._catalystId);

		if (ItemFunctions.checkCatalyst(itemToEnchant, catalyst))
		{
			activeChar.sendPacket(new ExPutEnchantSupportItemResult(1));
		}
		else
		{
			activeChar.sendPacket(new ExPutEnchantSupportItemResult(0));
		}
	}
}