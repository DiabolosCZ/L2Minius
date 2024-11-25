package l2minius.gameserver.network.loginservercon.gspackets;

import l2minius.gameserver.network.loginservercon.SendablePacket;

public class PingResponse extends SendablePacket
{
	@Override
	protected void writeImpl()
	{
		this.writeC(0xff);
		this.writeQ(System.currentTimeMillis());
	}
}