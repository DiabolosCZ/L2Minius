package l2minius.gameserver.network.serverpackets;

public class ExShowQuestInfo extends L2GameServerPacket
{
	public static final L2GameServerPacket STATIC = new ExShowQuestInfo();

	@Override
	protected final void writeImpl()
	{
		this.writeEx(0x20);
	}
}