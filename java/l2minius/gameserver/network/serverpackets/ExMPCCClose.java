package l2minius.gameserver.network.serverpackets;

/**
 * Close the CommandChannel Information window
 */
public class ExMPCCClose extends L2GameServerPacket
{
	public static final L2GameServerPacket STATIC = new ExMPCCClose();

	@Override
	protected void writeImpl()
	{
		this.writeEx(0x13);
	}
}
