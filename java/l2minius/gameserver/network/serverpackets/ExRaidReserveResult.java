package l2minius.gameserver.network.serverpackets;

public class ExRaidReserveResult extends L2GameServerPacket
{
	@Override
	protected void writeImpl()
	{
		this.writeEx(0xB6);
		// TODO dx[dddd]
	}
}