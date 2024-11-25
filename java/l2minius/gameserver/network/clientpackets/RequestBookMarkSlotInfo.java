package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.model.Player;
import l2minius.gameserver.network.serverpackets.ExGetBookMarkInfo;

public class RequestBookMarkSlotInfo extends L2GameClientPacket
{
	@Override
	protected void readImpl()
	{
		// just trigger
	}

	@Override
	protected void runImpl()
	{
		Player activeChar = this.getClient().getActiveChar();
		activeChar.sendPacket(new ExGetBookMarkInfo(activeChar));
	}
}