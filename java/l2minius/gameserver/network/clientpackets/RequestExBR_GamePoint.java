package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.model.Player;
import l2minius.gameserver.network.serverpackets.ExBR_GamePoint;

public class RequestExBR_GamePoint extends L2GameClientPacket
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

		activeChar.sendPacket(new ExBR_GamePoint(activeChar));
	}
}