package l2minius.loginserver.gameservercon.lspackets;

import l2minius.loginserver.gameservercon.SendablePacket;

public class PingRequest extends SendablePacket
{
	@Override
	protected void writeImpl()
	{
		writeC(0xff);
	}
}