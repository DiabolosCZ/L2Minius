package l2minius.gameserver.network.serverpackets;

/**
 * @author VISTALL
 */
public class ExDissmissMpccRoom extends L2GameServerPacket
{
	public static final L2GameServerPacket STATIC = new ExDissmissMpccRoom();

	@Override
	protected void writeImpl()
	{
		this.writeEx(0x9D);
	}
}