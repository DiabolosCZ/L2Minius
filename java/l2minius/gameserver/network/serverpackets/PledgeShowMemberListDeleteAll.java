package l2minius.gameserver.network.serverpackets;

public class PledgeShowMemberListDeleteAll extends L2GameServerPacket
{
	public static final L2GameServerPacket STATIC = new PledgeShowMemberListDeleteAll();

	@Override
	protected final void writeImpl()
	{
		this.writeC(0x88);
	}
}