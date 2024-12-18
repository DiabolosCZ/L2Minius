package l2minius.gameserver.network.serverpackets;

import l2minius.gameserver.model.Player;

public class ExUISetting extends L2GameServerPacket
{
	private final byte data[];

	public ExUISetting(Player player)
	{
		this.data = player.getKeyBindings();
	}

	@Override
	protected void writeImpl()
	{
		this.writeEx(0x70);
		this.writeD(this.data.length);
		this.writeB(this.data);
	}
}
