package l2minius.gameserver.handler.chat;

import l2minius.commons.data.xml.AbstractHolder;
import l2minius.gameserver.network.serverpackets.components.ChatType;

public class ChatHandler extends AbstractHolder
{
	private static final ChatHandler _instance = new ChatHandler();

	private IChatHandler[] _handlers = new IChatHandler[ChatType.VALUES.length];

	public static ChatHandler getInstance()
	{
		return _instance;
	}

	private ChatHandler()
	{

	}

	public void register(IChatHandler chatHandler)
	{
		_handlers[chatHandler.getType().ordinal()] = chatHandler;
	}

	public IChatHandler getHandler(ChatType type)
	{
		return _handlers[type.ordinal()];
	}

	@Override
	public int size()
	{
		return _handlers.length;
	}

	@Override
	public void clear()
	{

	}
}
