package l2minius.gameserver.network.serverpackets;

public class PetitionVote extends L2GameServerPacket
{
	@Override
	protected void writeImpl()
	{
		// just trigger
		this.writeC(0xFC);
	}
}