package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.model.GameObjectsStorage;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.matching.MatchingRoom;
import l2minius.gameserver.network.serverpackets.components.SystemMsg;

/**
 * format (ch) d
 */
public class RequestOustFromPartyRoom extends L2GameClientPacket
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

		MatchingRoom room = player.getMatchingRoom();
		if (room == null || room.getType() != MatchingRoom.PARTY_MATCHING || (room.getLeader() != player))
		{
			return;
		}

		Player member = GameObjectsStorage.getPlayer(this._objectId);
		if ((member == null) || (member == room.getLeader()))
		{
			return;
		}

		final int type = room.getMemberType(member);
		if (type == MatchingRoom.ROOM_MASTER)
		{
			return;
		}
		if (type == MatchingRoom.PARTY_MEMBER)
		{
			player.sendPacket(SystemMsg.YOU_CANNOT_DISMISS_A_PARTY_MEMBER_BY_FORCE);
			return;
		}
		room.removeMember(member, true);
	}
}