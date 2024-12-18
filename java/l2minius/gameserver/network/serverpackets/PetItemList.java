package l2minius.gameserver.network.serverpackets;

import l2minius.gameserver.model.instances.PetInstance;
import l2minius.gameserver.model.items.ItemInstance;

public class PetItemList extends L2GameServerPacket
{
	private ItemInstance[] items;

	public PetItemList(PetInstance cha)
	{
		this.items = cha.getInventory().getItems();
	}

	@Override
	protected final void writeImpl()
	{
		this.writeC(0xb3);
		this.writeH(this.items.length);

		for (ItemInstance item : this.items)
		{
			this.writeItemInfo(item);
		}
	}
}