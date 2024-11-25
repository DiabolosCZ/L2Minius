package l2minius.gameserver.handler.petition;

import l2minius.gameserver.model.Player;

public interface IPetitionHandler
{
	void handle(Player player, int id, String txt);
}
