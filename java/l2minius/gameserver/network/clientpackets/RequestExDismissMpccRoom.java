package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.matching.MatchingRoom;

/**
 * @author VISTALL
 */
public class RequestExDismissMpccRoom extends L2GameClientPacket
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

		MatchingRoom room = player.getMatchingRoom();
		if (room == null || room.getType() != MatchingRoom.CC_MATCHING || (room.getLeader() != player))
		{
			return;
		}

		room.disband();
	}
}