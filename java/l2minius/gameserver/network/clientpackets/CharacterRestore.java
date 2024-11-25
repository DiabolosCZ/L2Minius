package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.network.GameClient;
import l2minius.gameserver.network.serverpackets.CharacterSelectionInfo;

public class CharacterRestore extends L2GameClientPacket
{
	// cd
	private int _charSlot;

	@Override
	protected void readImpl()
	{
		this._charSlot = this.readD();
	}

	@Override
	protected void runImpl()
	{
		GameClient client = this.getClient();

		client.markRestoredChar(this._charSlot);
		CharacterSelectionInfo cl = new CharacterSelectionInfo(client.getLogin(), client.getSessionKey().playOkID1);
		this.sendPacket(cl);
		client.setCharSelection(cl.getCharInfo());
	}
}