package l2minius.gameserver.handler.voicecommands.impl;

import l2minius.gameserver.handler.voicecommands.IVoicedCommandHandler;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.entity.CCPHelpers.CCPSmallCommands;
import l2minius.gameserver.scripts.Functions;

public class CombineTalismans extends Functions implements IVoicedCommandHandler
{
	private static final String[] COMMANDS = new String[]
	{
		"combine",
		"talisman"
	};

	@Override
	public boolean useVoicedCommand(String command, Player activeChar, String args)
	{
		CCPSmallCommands.combineTalismans(activeChar);
		return true;
	}

	@Override
	public String[] getVoicedCommandList()
	{
		return COMMANDS;
	}
}