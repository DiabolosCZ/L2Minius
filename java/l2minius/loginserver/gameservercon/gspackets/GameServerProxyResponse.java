package l2minius.loginserver.gameservercon.gspackets;

import l2minius.loginserver.gameservercon.ReceivablePacket;
import l2minius.loginserver.utils.ProxyWaitingList;

public class GameServerProxyResponse extends ReceivablePacket
{
	private String accountName;
	private String proxyIp;

	@Override
	protected void readImpl()
	{
		accountName = readS();
		proxyIp = readS();
	}

	@Override
	protected void runImpl()
	{
		ProxyWaitingList.getInstance().receiveProxy(getGameServer().getId(), accountName, proxyIp);
	}
}
