package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.entity.events.impl.DuelEvent;

public class RequestDuelSurrender extends L2GameClientPacket
{
	@Override
	protected void readImpl()
	{
	}

	@Override
	protected void runImpl()
	{
		Player player = this.getClient().getActiveChar();
		if (player == null)
		{
			return;
		}

		DuelEvent duelEvent = player.getEvent(DuelEvent.class);
		if (duelEvent == null)
		{
			return;
		}

		duelEvent.packetSurrender(player);
	}
}