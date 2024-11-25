package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.Config;
import l2minius.gameserver.model.Player;

public class SetPrivateStoreWholeMsg extends L2GameClientPacket
{
	private String _storename;

	@Override
	protected void readImpl()
	{
		this._storename = this.readS(32);
	}

	@Override
	protected void runImpl()
	{
		Player activeChar = this.getClient().getActiveChar();
		if (activeChar == null)
		{
			return;
		}

		if (Config.containsAbuseWord(this._storename))
		{
			this._storename = "...";
		}

		activeChar.setSellStoreName(this._storename);
	}
}