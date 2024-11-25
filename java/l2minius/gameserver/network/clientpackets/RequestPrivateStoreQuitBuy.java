package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.Config;
import l2minius.gameserver.model.Player;

public class RequestPrivateStoreQuitBuy extends L2GameClientPacket
{
	@Override
	protected void readImpl()
	{
	}

	@Override
	protected void runImpl()
	{
		Player activeChar = this.getClient().getActiveChar();
		if (activeChar == null)
		{
			return;
		}

		if (!activeChar.isInStoreMode() || activeChar.getPrivateStoreType() != Player.STORE_PRIVATE_BUY || !Config.ALLOW_PRIVATE_STORES)
		{
			activeChar.sendActionFailed();
			return;
		}

		activeChar.setPrivateStoreType(Player.STORE_PRIVATE_NONE);
		activeChar.standUp();
		activeChar.broadcastCharInfo();
	}
}