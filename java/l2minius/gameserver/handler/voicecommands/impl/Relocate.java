package l2minius.gameserver.handler.voicecommands.impl;

import java.util.List;

import l2minius.gameserver.Config;
import l2minius.gameserver.cache.Msg;
import l2minius.gameserver.handler.voicecommands.IVoicedCommandHandler;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.network.serverpackets.SystemMessage;
import l2minius.gameserver.scripts.Functions;
import l2minius.gameserver.skills.skillclasses.Call;
import l2minius.gameserver.utils.Location;

public class Relocate extends Functions implements IVoicedCommandHandler
{

	private static final String[] _commandList = {"km-all-to-me"};

	@Override
	public String[] getVoicedCommandList()
	{
		return _commandList;
	}

	@Override
	public boolean useVoicedCommand(String command, Player activeChar, String target)
	{
		if (!Config.ENABLE_KM_ALL_TO_ME)
		{
			return false;
		}
		if (command.equalsIgnoreCase("km-all-to-me"))
		{
			if (!activeChar.isClanLeader())
			{
				activeChar.sendPacket(Msg.ONLY_THE_CLAN_LEADER_IS_ENABLED);
				return false;
			}
			SystemMessage msg = Call.canSummonHere(activeChar);
			if (msg != null)
			{
				activeChar.sendPacket(msg);
				return false;
			}
			List<Player> players = activeChar.getClan().getOnlineMembers(activeChar.getObjectId());
			for (Player player : players)
			{
				if (Call.canBeSummoned(activeChar, player) == null)
				{
					player.summonCharacterRequest(activeChar, Location.findAroundPosition(activeChar, 100, 150), 5);
				}
			}
			return true;
		}
		return false;
	}
}