package l2minius.gameserver.network.clientpackets;

import java.util.HashSet;
import java.util.Set;

import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.matching.MatchingRoom;
import l2minius.gameserver.network.serverpackets.ExMpccPartymasterList;

/**
 * @author VISTALL
 */
public class RequestExMpccPartymasterList extends L2GameClientPacket
{
	@Override
	protected void readImpl()
	{
		//
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
		if (room == null || room.getType() != MatchingRoom.CC_MATCHING)
		{
			return;
		}

		Set<String> set = new HashSet<String>();
		for (Player $member : room.getPlayers())
		{
			if ($member.getParty() != null)
			{
				set.add($member.getParty().getLeader().getName());
			}
		}

		player.sendPacket(new ExMpccPartymasterList(set));
	}
}