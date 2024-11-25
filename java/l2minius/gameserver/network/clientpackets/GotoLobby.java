package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.network.serverpackets.CharacterSelectionInfo;

public class GotoLobby extends L2GameClientPacket
{
	@Override
	protected void readImpl()
	{
	}

	@Override
	protected void runImpl()
	{
		CharacterSelectionInfo cl = new CharacterSelectionInfo(this.getClient().getLogin(), this.getClient().getSessionKey().playOkID1);
		this.sendPacket(cl);
	}
}