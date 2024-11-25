package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.model.Creature;
import l2minius.gameserver.network.serverpackets.ExCursedWeaponList;

public class RequestCursedWeaponList extends L2GameClientPacket
{
	@Override
	protected void readImpl()
	{
	}

	@Override
	protected void runImpl()
	{
		Creature activeChar = this.getClient().getActiveChar();
		if (activeChar == null)
		{
			return;
		}

		activeChar.sendPacket(new ExCursedWeaponList());
	}
}