package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.model.Player;

public class RequestFriendDel extends L2GameClientPacket
{
	private String _name;

	@Override
	protected void readImpl()
	{
		this._name = this.readS(16);
	}

	@Override
	protected void runImpl()
	{
		Player player = this.getClient().getActiveChar();
		if (player == null)
		{
			return;
		}

		player.getFriendList().removeFriend(this._name);
	}
}