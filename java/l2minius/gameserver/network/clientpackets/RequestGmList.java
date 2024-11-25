package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.model.Player;
import l2minius.gameserver.tables.GmListTable;

public class RequestGmList extends L2GameClientPacket
{
	@Override
	protected void readImpl()
	{
	}

	@Override
	protected void runImpl()
	{
		Player activeChar = this.getClient().getActiveChar();
		if (activeChar != null)
		{
			GmListTable.sendListToPlayer(activeChar);
		}
	}
}