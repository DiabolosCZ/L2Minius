package l2minius.gameserver.handler.voicecommands.impl;

import l2minius.gameserver.handler.voicecommands.IVoicedCommandHandler;
import l2minius.gameserver.instancemanager.HellboundManager;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.scripts.Functions;

public class Hellbound extends Functions implements IVoicedCommandHandler
{
	private static final String[] _commandList =
	{
		"hellbound"
	};

	@Override
	public String[] getVoicedCommandList()
	{
		return _commandList;
	}

	@Override
	public boolean useVoicedCommand(String command, Player activeChar, String target)
	{
		if (command.equals("hellbound"))
		{
			activeChar.sendMessage("Hellbound level: " + HellboundManager.getHellboundLevel());
			activeChar.sendMessage("Confidence: " + HellboundManager.getConfidence());
		}
		return false;
	}
}
