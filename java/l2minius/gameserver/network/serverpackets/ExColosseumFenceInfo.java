package l2minius.gameserver.network.serverpackets;

public class ExColosseumFenceInfo extends L2GameServerPacket
{
	@Override
	protected void writeImpl()
	{
		this.writeEx(0x03);
		// TODO ddddddd
	}
}