package l2minius.gameserver.network.serverpackets;

public class ExDuelEnemyRelation extends L2GameServerPacket
{
	@Override
	protected void writeImpl()
	{
		this.writeEx(0x59);
		// just trigger
	}
}