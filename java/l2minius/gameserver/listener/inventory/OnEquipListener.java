package l2minius.gameserver.listener.inventory;

import l2minius.commons.listener.Listener;
import l2minius.gameserver.model.Playable;
import l2minius.gameserver.model.items.ItemInstance;

public interface OnEquipListener extends Listener<Playable>
{
	public void onEquip(int slot, ItemInstance item, Playable actor);

	public void onUnequip(int slot, ItemInstance item, Playable actor);
}
