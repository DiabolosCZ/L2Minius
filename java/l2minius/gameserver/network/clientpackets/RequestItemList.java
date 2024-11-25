package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.model.Player;
import l2minius.gameserver.network.serverpackets.StatusUpdate;

public class RequestItemList extends L2GameClientPacket
{
	@Override
	protected void readImpl()
	{
	}

	@Override
	protected void runImpl()
	{
		Player activeChar = this.getClient().getActiveChar();
		if (activeChar == null)
		{
			return;
		}

		if (!activeChar.getPlayerAccess().UseInventory || activeChar.isBlocked())
		{
			activeChar.sendActionFailed();
			return;
		}

		activeChar.sendItemList(true);
		activeChar.sendStatusUpdate(false, false, StatusUpdate.CUR_LOAD);
	}
}