package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.model.Player;
import l2minius.gameserver.network.serverpackets.HennaEquipList;

public class RequestHennaList extends L2GameClientPacket
{
	@Override
	protected void readImpl()
	{
		// readD(); - unknown
	}

	@Override
	protected void runImpl()
	{
		Player player = this.getClient().getActiveChar();
		if (player == null)
		{
			return;
		}

		player.sendPacket(new HennaEquipList(player));
	}
}