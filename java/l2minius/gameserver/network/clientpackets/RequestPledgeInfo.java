package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.pledge.Clan;
import l2minius.gameserver.network.serverpackets.PledgeInfo;
import l2minius.gameserver.tables.ClanTable;

public class RequestPledgeInfo extends L2GameClientPacket
{
	private int _clanId;

	@Override
	protected void readImpl()
	{
		this._clanId = this.readD();
	}

	@Override
	protected void runImpl()
	{
		Player activeChar = this.getClient().getActiveChar();
		if (activeChar == null)
		{
			return;
		}
		if (this._clanId < 10000000)
		{
			activeChar.sendActionFailed();
			return;
		}
		Clan clan = ClanTable.getInstance().getClan(this._clanId);
		if (clan == null)
		{
			// Util.handleIllegalPlayerAction(activeChar, "RequestPledgeInfo[40]", "Clan data for clanId " + _clanId + " is missing", 1);
			// _log.warn("Host " + getClient().getIpAddr() + " possibly sends fake packets. activeChar: " + activeChar);
			activeChar.sendActionFailed();
			return;
		}

		activeChar.sendPacket(new PledgeInfo(clan));
	}
}