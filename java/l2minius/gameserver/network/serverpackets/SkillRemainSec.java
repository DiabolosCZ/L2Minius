package l2minius.gameserver.network.serverpackets;

public class SkillRemainSec extends L2GameServerPacket
{
	@Override
	protected void writeImpl()
	{
		this.writeC(0xD8);
		// TODO ddddddd
	}
}