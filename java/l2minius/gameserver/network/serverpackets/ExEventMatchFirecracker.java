package l2minius.gameserver.network.serverpackets;

public class ExEventMatchFirecracker extends L2GameServerPacket
{
	@Override
	protected void writeImpl()
	{
		this.writeEx(0x05);
		// TODO d
	}
}