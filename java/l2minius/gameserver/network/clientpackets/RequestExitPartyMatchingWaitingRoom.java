package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.instancemanager.MatchingRoomManager;
import l2minius.gameserver.model.Player;

/**
 * Format: (ch)
 */
public class RequestExitPartyMatchingWaitingRoom extends L2GameClientPacket
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

		MatchingRoomManager.getInstance().removeFromWaitingList(player);
	}
}