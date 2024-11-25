package l2minius.gameserver.handler.voicecommands.impl;

import l2minius.gameserver.handler.voicecommands.IVoicedCommandHandler;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.entity.CCPHelpers.CCPRepair;
import l2minius.gameserver.scripts.Functions;

public class Repair extends Functions implements IVoicedCommandHandler
{
	private static final String[] COMMANDS =
	{
		"repair"
	};

	@Override
	public boolean useVoicedCommand(String command, Player activeChar, String target)
	{
		CCPRepair.repairChar(activeChar, target);
		return false;
	}

	@Override
	public String[] getVoicedCommandList()
	{
		return COMMANDS;
	}
}