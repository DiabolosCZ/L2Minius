package handler.items;

import l2minius.gameserver.handler.items.ItemHandler;
import l2minius.gameserver.model.Playable;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.items.ItemInstance;
import l2minius.gameserver.network.serverpackets.ExChangeNicknameNColor;
import l2minius.gameserver.scripts.ScriptFile;

public class NameColor extends SimpleItemHandler implements ScriptFile
{
	private static final int[] ITEM_IDS = new int[]
	{
		13021,
		13307
	};

	@Override
	public int[] getItemIds()
	{
		return ITEM_IDS;
	}

	@Override
	public boolean pickupItem(Playable playable, ItemInstance item)
	{
		return true;
	}

	@Override
	public void onLoad()
	{
		ItemHandler.getInstance().registerItemHandler(this);
	}

	@Override
	public void onReload()
	{

	}

	@Override
	public void onShutdown()
	{

	}

	@Override
	protected boolean useItemImpl(Player player, ItemInstance item, boolean ctrl)
	{
		player.sendPacket(new ExChangeNicknameNColor(item.getObjectId()));
		return true;
	}
}
