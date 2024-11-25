package l2minius.gameserver.handler.voicecommands.impl;

import l2minius.gameserver.handler.voicecommands.IVoicedCommandHandler;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.entity.CCPHelpers.CCPPoll;

public class PollVoice implements IVoicedCommandHandler
{
	private static final String[] COMMANDS =
	{
		"poll"
	};

	@Override
	public boolean useVoicedCommand(String command, Player activeChar, String target)
	{
		CCPPoll.bypass(activeChar, target.split(" "));
		return true;
	}

	@Override
	public String[] getVoicedCommandList()
	{
		return COMMANDS;
	}

}
