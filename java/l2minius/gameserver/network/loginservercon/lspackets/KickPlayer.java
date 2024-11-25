package l2minius.gameserver.network.loginservercon.lspackets;

import l2minius.gameserver.cache.Msg;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.network.GameClient;
import l2minius.gameserver.network.loginservercon.AuthServerCommunication;
import l2minius.gameserver.network.loginservercon.ReceivablePacket;
import l2minius.gameserver.network.serverpackets.ServerClose;

public class KickPlayer extends ReceivablePacket
{
	String account;

	@Override
	public void readImpl()
	{
		this.account = this.readS();
	}

	@Override
	protected void runImpl()
	{
		GameClient client = AuthServerCommunication.getInstance().removeWaitingClient(this.account);
		if (client == null)
		{
			client = AuthServerCommunication.getInstance().removeAuthedClient(this.account);
		}
		if (client == null)
		{
			return;
		}
		final Player activeChar = client.getActiveChar();
		if (activeChar != null)
		{
			activeChar.sendPacket(Msg.ANOTHER_PERSON_HAS_LOGGED_IN_WITH_THE_SAME_ACCOUNT);
			activeChar.kick();
		}
		else
		{
			client.close(ServerClose.STATIC);
		}
	}
}