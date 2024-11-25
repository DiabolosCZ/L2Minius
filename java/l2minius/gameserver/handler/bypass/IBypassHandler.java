package l2minius.gameserver.handler.bypass;

import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.instances.NpcInstance;

public interface IBypassHandler
{
	String[] getBypasses();

	void onBypassFeedback(NpcInstance npc, Player player, String command);
}
