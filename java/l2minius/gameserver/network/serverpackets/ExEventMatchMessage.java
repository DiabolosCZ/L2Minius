package l2minius.gameserver.network.serverpackets;

public class ExEventMatchMessage extends L2GameServerPacket
{
	@Override
	protected void writeImpl()
	{
		this.writeEx(0x0F);
		// TODO cS
	}
}