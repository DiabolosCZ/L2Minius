package l2minius.gameserver.network.serverpackets;

public class ExEventMatchUserInfo extends L2GameServerPacket
{
	@Override
	protected void writeImpl()
	{
		this.writeEx(0x02);
		// TODO dSdddddddd
	}
}