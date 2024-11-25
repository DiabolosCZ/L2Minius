package l2minius.gameserver.permission.actor.player;

import l2minius.gameserver.model.Player;
import l2minius.gameserver.permission.PlayerPermission;

public interface JoinFightClubPermission extends PlayerPermission
{
	boolean joinSignFightClub(Player p0);

	default void sendPermissionDeniedError(Player actor)
	{
		actor.sendMessage(getPermissionDeniedError(actor));
	}

	String getPermissionDeniedError(Player p0);
}
