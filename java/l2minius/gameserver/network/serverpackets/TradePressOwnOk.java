package l2minius.gameserver.network.serverpackets;

//@Deprecated
public class TradePressOwnOk extends L2GameServerPacket
{
	@Override
	protected final void writeImpl()
	{
		this.writeC(0x53);
	}
}