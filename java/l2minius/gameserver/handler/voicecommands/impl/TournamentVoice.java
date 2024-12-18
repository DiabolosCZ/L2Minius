package l2minius.gameserver.handler.voicecommands.impl;

import l2minius.gameserver.ConfigHolder;
import l2minius.gameserver.handler.bbs.CommunityBoardManager;
import l2minius.gameserver.handler.bbs.ICommunityBoardHandler;
import l2minius.gameserver.handler.voicecommands.IVoicedCommandHandler;
import l2minius.gameserver.handler.voicecommands.VoicedCommandHandler;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.scripts.ScriptFile;

/**
 * Voiced handler for the tournament system. Opens the community main page
 *
 * @author Synerge
 */
public class TournamentVoice implements IVoicedCommandHandler, ScriptFile
{
	private static final String[] COMMANDS = new String[]
	{
		"tournament",
		"tgl",
		"gvg"
	};

	@Override
	public void onLoad()
	{
		VoicedCommandHandler.getInstance().registerVoicedCommandHandler(this);
	}

	@Override
	public void onReload()
	{
	}

	@Override
	public void onShutdown()
	{
	}

	@Override
	public String[] getVoicedCommandList()
	{
		return COMMANDS;
	}

	@Override
	public boolean useVoicedCommand(String command, Player activeChar, String args)
	{
		if (!ConfigHolder.getBool("TournamentAllowVoicedCommand"))
		{
			return false;
		}

		if (command.equals(COMMANDS[0]))
		{
			final ICommunityBoardHandler handler = CommunityBoardManager.getInstance().getCommunityHandler("_bbstournament");
			if (handler != null)
			{
				handler.onBypassCommand(activeChar, "_bbstournament_main");
			}
			return true;
		}
		return false;
	}
}