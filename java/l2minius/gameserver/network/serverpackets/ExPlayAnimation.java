package l2minius.gameserver.network.serverpackets;

public class ExPlayAnimation extends L2GameServerPacket
{
	@Override
	protected void writeImpl()
	{
		this.writeEx(0x5A);
		// TODO dcdS
	}
}