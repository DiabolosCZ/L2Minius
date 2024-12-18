package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.pledge.Clan;
import l2minius.gameserver.model.pledge.UnitMember;
import l2minius.gameserver.network.serverpackets.PledgeReceiveMemberInfo;
import l2minius.gameserver.network.serverpackets.PledgeShowMemberListUpdate;
import l2minius.gameserver.network.serverpackets.SystemMessage2;
import l2minius.gameserver.network.serverpackets.components.CustomMessage;
import l2minius.gameserver.network.serverpackets.components.SystemMsg;

public class RequestPledgeSetAcademyMaster extends L2GameClientPacket
{
	private int _mode; // 1=set, 0=unset
	private String _sponsorName;
	private String _apprenticeName;

	@Override
	protected void readImpl()
	{
		this._mode = this.readD();
		this._sponsorName = this.readS(16);
		this._apprenticeName = this.readS(16);
	}

	@Override
	protected void runImpl()
	{
		Player activeChar = this.getClient().getActiveChar();
		if (activeChar == null)
		{
			return;
		}

		Clan clan = activeChar.getClan();
		if (clan == null)
		{
			return;
		}

		if ((activeChar.getClanPrivileges() & Clan.CP_CL_APPRENTICE) == Clan.CP_CL_APPRENTICE)
		{
			UnitMember sponsor = activeChar.getClan().getAnyMember(this._sponsorName);
			UnitMember apprentice = activeChar.getClan().getAnyMember(this._apprenticeName);
			if (sponsor != null && apprentice != null)
			{
				if (apprentice.getPledgeType() != Clan.SUBUNIT_ACADEMY || sponsor.getPledgeType() == Clan.SUBUNIT_ACADEMY)
				{
					return; // hack?
				}

				if (this._mode == 1)
				{
					if (sponsor.hasApprentice())
					{
						activeChar.sendMessage(new CustomMessage("l2minius.gameserver.clientpackets.RequestOustAlly.MemberAlreadyHasApprentice", activeChar));
						return;
					}
					if (apprentice.hasSponsor())
					{
						activeChar.sendMessage(new CustomMessage("l2minius.gameserver.clientpackets.RequestOustAlly.ApprenticeAlreadyHasSponsor", activeChar));
						return;
					}
					sponsor.setApprentice(apprentice.getObjectId());
					clan.broadcastToOnlineMembers(new PledgeShowMemberListUpdate(apprentice));
					clan.broadcastToOnlineMembers(new SystemMessage2(SystemMsg.S2_HAS_BEEN_DESIGNATED_AS_THE_APPRENTICE_OF_CLAN_MEMBER_S1).addString(sponsor.getName()).addString(apprentice.getName()));
				}
				else
				{
					if (!sponsor.hasApprentice())
					{
						activeChar.sendMessage(new CustomMessage("l2minius.gameserver.clientpackets.RequestOustAlly.MemberHasNoApprentice", activeChar));
						return;
					}
					sponsor.setApprentice(0);
					clan.broadcastToOnlineMembers(new PledgeShowMemberListUpdate(apprentice));
					clan.broadcastToOnlineMembers(new SystemMessage2(SystemMsg.S2_CLAN_MEMBER_C1S_APPRENTICE_HAS_BEEN_REMOVED).addString(sponsor.getName()).addString(apprentice.getName()));
				}
				if (apprentice.isOnline())
				{
					apprentice.getPlayer().broadcastCharInfo();
				}
				activeChar.sendPacket(new PledgeReceiveMemberInfo(sponsor));
			}
		}
		else
		{
			activeChar.sendMessage(new CustomMessage("l2minius.gameserver.clientpackets.RequestOustAlly.NoMasterRights", activeChar));
		}
	}
}