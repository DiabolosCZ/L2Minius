package l2minius.gameserver.network.serverpackets;

public class WithdrawAlliance extends L2GameServerPacket
{
	@Override
	protected final void writeImpl()
	{
		this.writeC(0xAB);
		// TODO d
	}
}