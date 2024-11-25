package l2minius.gameserver.network.clientpackets;

import java.util.Map;

import l2minius.gameserver.Config;
import l2minius.gameserver.handler.bbs.CommunityBoardManager;
import l2minius.gameserver.handler.bbs.ICommunityBoardHandler;
import l2minius.gameserver.handler.bypass.BypassHandler;
import l2minius.gameserver.instancemanager.QuestManager;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.entity.achievements.Achievements;
import l2minius.gameserver.model.entity.events.fightclubmanager.FightClubEventManager;
import l2minius.gameserver.model.quest.Quest;
import l2minius.gameserver.network.serverpackets.SystemMessage2;
import l2minius.gameserver.network.serverpackets.components.SystemMsg;
import l2minius.gameserver.scripts.Scripts;
import l2minius.gameserver.utils.AccountEmail;

public class RequestTutorialPassCmdToServer extends L2GameClientPacket
{
	// format: cS

	String _bypass = null;

	@Override
	protected void readImpl()
	{
		this._bypass = this.readS();
	}

	@Override
	protected void runImpl()
	{
		Player player = this.getClient().getActiveChar();
		if (player == null)
		{
			return;
		}

		player.isntAfk();

		if (player.isInFightClub())
		{
			FightClubEventManager.getInstance().requestEventPlayerMenuBypass(player, this._bypass);
		}
		// Synerge - Achievements system
		else if (Config.ENABLE_ACHIEVEMENTS && this._bypass.startsWith("_bbs_achievements"))
		{
			String[] cm = this._bypass.split(" ");
			if (this._bypass.startsWith("_bbs_achievements_cat"))
			{
				int page = 0;
				if (cm.length < 1)
				{
					page = 1;
				}
				else
				{
					page = Integer.parseInt(cm[2]);
				}

				Achievements.getInstance().generatePage(player, Integer.parseInt(cm[1]), page);
			}
			else
			{
				Achievements.getInstance().onBypass(player, this._bypass, cm);
			}
		}
		// Synerge - Support for handling bbs events on tutorial windows
		else if (this._bypass.startsWith("_bbs"))
		{
			if (!Config.COMMUNITYBOARD_ENABLED)
			{
				player.sendPacket(new SystemMessage2(SystemMsg.THE_COMMUNITY_SERVER_IS_CURRENTLY_OFFLINE));
			}
			else
			{
				String[] cm = this._bypass.split(" ");
				final ICommunityBoardHandler handler = CommunityBoardManager.getInstance().getCommunityHandler(cm[0]);
				if (handler != null)
				{
					handler.onBypassCommand(player, this._bypass);
				}
			}
		}
		// Synerge - Support for handling scripts events on tutorial windows
		else if (this._bypass.startsWith("scripts_"))
		{
			String command = this._bypass.substring(8).trim();
			String[] word = command.split("\\s+");
			String[] args = command.substring(word[0].length()).trim().split("\\s+");
			String[] path = word[0].split(":");
			if (path.length != 2)
			{
				return;
			}

			Map<String, Object> variables = null;

			if (word.length == 1)
			{
				Scripts.getInstance().callScripts(player, path[0], path[1], variables);
			}
			else
			{
				Scripts.getInstance().callScripts(player, path[0], path[1], new Object[]
				{
					args
				}, variables);
			}
		}
		// Synerge - Support to use the bypasshandler on tutorial windows
		else if (BypassHandler.getInstance().useBypassCommandHandler(player, this._bypass))
		{

		}
		else
		{
			Quest tutorial = QuestManager.getQuest(255);

			if (tutorial != null)
			{
				player.processQuestEvent(tutorial.getName(), this._bypass, null);
			}
		}

		if (Config.ALLOW_MAIL_OPTION)
		{
			AccountEmail.onBypass(player, this._bypass);
		}
	}

	// Synerge - This packet can be used while the character is blocked
	@Override
	public boolean canBeUsedWhileBlocked()
	{
		return true;
	}
}