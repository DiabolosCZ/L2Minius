package l2minius.gameserver.permission.actor.player;

import l2minius.gameserver.model.Creature;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.permission.PlayerPermission;

public interface ResurrectPermission extends PlayerPermission
{
	boolean canResurrect(Player p0, Creature p1, boolean p2, boolean p3);

	void sendPermissionDeniedError(Player p0, Creature p1, boolean p2, boolean p3);
}
