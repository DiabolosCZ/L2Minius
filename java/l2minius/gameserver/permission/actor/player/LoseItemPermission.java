package l2minius.gameserver.permission.actor.player;

import l2minius.gameserver.model.Playable;
import l2minius.gameserver.model.items.ItemInstance;
import l2minius.gameserver.permission.PlayablePermission;

public interface LoseItemPermission extends PlayablePermission
{
	boolean canLoseItem(Playable p0, ItemInstance p1);

	default void sendPermissionDeniedError(Playable actor, ItemInstance item)
	{
		actor.sendMessage(getPermissionDeniedError(actor, item));
	}

	String getPermissionDeniedError(Playable p0, ItemInstance p1);
}
