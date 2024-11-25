package l2minius.gameserver.model.items.attachment;

import l2minius.gameserver.model.Player;

public interface PickableAttachment extends ItemAttachment
{
	boolean canPickUp(Player player);

	void pickUp(Player player);
}
