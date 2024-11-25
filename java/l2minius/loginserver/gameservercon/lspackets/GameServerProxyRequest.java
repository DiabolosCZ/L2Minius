package l2minius.loginserver.gameservercon.lspackets;

import l2minius.loginserver.gameservercon.SendablePacket;

public class GameServerProxyRequest extends SendablePacket
{
	private final String accountName;
	private final String ip;

	public GameServerProxyRequest(String accountName, String ip)
	{
		super();
		this.accountName = accountName;
		this.ip = ip;
	}

	@Override
	protected void writeImpl()
	{
		writeC(9);
		writeS(accountName);
		writeS(ip);
	}
}
