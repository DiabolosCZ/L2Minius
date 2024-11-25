package l2minius.gameserver.network.serverpackets;

import l2minius.gameserver.utils.Location;

public class ObserverEnd extends L2GameServerPacket
{
	// ddSS
	private Location _loc;

	public ObserverEnd(Location loc)
	{
		this._loc = loc;
	}

	@Override
	protected final void writeImpl()
	{
		this.writeC(0xec);
		this.writeD(this._loc.x);
		this.writeD(this._loc.y);
		this.writeD(this._loc.z);
	}
}