package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.Config;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.pledge.Clan;
import l2minius.gameserver.model.pledge.UnitMember;
import l2minius.gameserver.network.serverpackets.NickNameChanged;
import l2minius.gameserver.network.serverpackets.components.CustomMessage;
import l2minius.gameserver.network.serverpackets.components.SystemMsg;
import l2minius.gameserver.utils.Util;

public class RequestGiveNickName extends L2GameClientPacket
{
	private String _target;
	private String _title;

	@Override
	protected void readImpl()
	{
		this._target = this.readS(Config.CNAME_MAXLEN);
		this._title = this.readS();
	}

	@Override
	protected void runImpl()
	{
		Player activeChar = this.getClient().getActiveChar();
		if (activeChar == null)
		{
			return;
		}

		if (!this._title.isEmpty() && !Util.isMatchingRegexp(this._title, Config.CLAN_TITLE_TEMPLATE))
		{
			activeChar.sendMessage("Incorrect title.");
			return;
		}

		// Дворяне могут устанавливать/менять себе title
		if (activeChar.isNoble() && this._target.matches(activeChar.getName()))
		{
			activeChar.setTitle(this._title);
			activeChar.sendPacket(SystemMsg.YOUR_TITLE_HAS_BEEN_CHANGED);
			activeChar.broadcastPacket(new NickNameChanged(activeChar));
			return;
		}
		// Can the player change/give a title?
		else if ((activeChar.getClanPrivileges() & Clan.CP_CL_MANAGE_TITLES) != Clan.CP_CL_MANAGE_TITLES)
		{
			return;
		}

		if (activeChar.getClan().getLevel() < 3)
		{
			activeChar.sendPacket(SystemMsg.A_PLAYER_CAN_ONLY_BE_GRANTED_A_TITLE_IF_THE_CLAN_IS_LEVEL_3_OR_ABOVE);
			return;
		}

		UnitMember member = activeChar.getClan().getAnyMember(this._target);
		if (member != null)
		{
			member.setTitle(this._title);
			if (member.isOnline())
			{
				member.getPlayer().sendPacket(SystemMsg.YOUR_TITLE_HAS_BEEN_CHANGED);
				member.getPlayer().sendChanges();
			}
		}
		else
		{
			activeChar.sendMessage(new CustomMessage("l2minius.gameserver.clientpackets.RequestGiveNickName.NotInClan", activeChar));
		}

	}
}