package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.model.GameObjectsStorage;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.matching.MatchingRoom;

/**
 * @author VISTALL
 */
public class RequestExOustFromMpccRoom extends L2GameClientPacket
{
	private int _objectId;

	@Override
	protected void readImpl()
	{
		this._objectId = this.readD();
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

		Player member = GameObjectsStorage.getPlayer(this._objectId);
		if ((member == null) || (member == room.getLeader()))
		{
			return;
		}

		room.removeMember(member, true);
	}
}