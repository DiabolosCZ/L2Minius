package l2minius.gameserver.handler.chat;

import l2minius.gameserver.network.serverpackets.components.ChatType;

public interface IChatHandler
{
	void say();

	ChatType getType();
}
