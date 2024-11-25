package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.model.Party;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.network.serverpackets.ExMPCCShowPartyMemberInfo;

public class RequestExMPCCShowPartyMembersInfo extends L2GameClientPacket
{
	private int _objectId;

	@Override
	protected void readImpl()
	{
		this._objectId = this.readD();
	}

	@Override
	protected void runImpl()
	{
		Player activeChar = this.getClient().getActiveChar();

		if (activeChar == null || !activeChar.isInParty() || !activeChar.getParty().isInCommandChannel())
		{
			return;
		}

		for (Party party : activeChar.getParty().getCommandChannel().getParties())
		{
			Player leader = party.getLeader();
			if (leader != null && leader.getObjectId() == this._objectId)
			{
				activeChar.sendPacket(new ExMPCCShowPartyMemberInfo(party));
				break;
			}
		}
	}
}