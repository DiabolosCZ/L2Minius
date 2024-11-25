package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.model.Player;
import l2minius.gameserver.network.serverpackets.ExBR_ProductList;

public class RequestExBR_ProductList extends L2GameClientPacket
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

		activeChar.sendPacket(new ExBR_ProductList());
	}
}