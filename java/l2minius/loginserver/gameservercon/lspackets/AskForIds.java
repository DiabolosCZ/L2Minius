package l2minius.loginserver.gameservercon.lspackets;

import l2minius.loginserver.gameservercon.SendablePacket;

public class AskForIds extends SendablePacket
{
	private int _count;

	public AskForIds(int count)
	{
		_count = count;
	}

	@Override
	protected void writeImpl()
	{
		writeC(0x07);
		writeD(_count);
	}
}
