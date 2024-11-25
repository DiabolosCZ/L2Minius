package l2minius.gameserver.model.entity.tournament.permission;

import l2minius.gameserver.data.StringHolder;
import l2minius.gameserver.model.Playable;
import l2minius.gameserver.model.items.ItemInstance;
import l2minius.gameserver.permission.actor.player.LoseItemPermission;

public class TournamentLoseItemPermission implements LoseItemPermission
{
	@Override
	public boolean canLoseItem(Playable actor, ItemInstance item)
	{
		return false;
	}

	@Override
	public String getPermissionDeniedError(Playable actor, ItemInstance item)
	{
		return StringHolder.getNotNull(actor.getPlayer(), "Tournament.NotAllowed.LoseItem", new Object[0]);
	}
}
