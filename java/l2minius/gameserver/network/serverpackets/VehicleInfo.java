package l2minius.gameserver.network.serverpackets;

import l2minius.gameserver.model.entity.boat.Boat;
import l2minius.gameserver.utils.Location;

public class VehicleInfo extends L2GameServerPacket
{
	private int _boatObjectId;
	private Location _loc;

	public VehicleInfo(Boat boat)
	{
		this._boatObjectId = boat.getObjectId();
		this._loc = boat.getLoc();
	}

	@Override
	protected final void writeImpl()
	{
		this.writeC(0x60);
		this.writeD(this._boatObjectId);
		this.writeD(this._loc.x);
		this.writeD(this._loc.y);
		this.writeD(this._loc.z);
		this.writeD(this._loc.h);
	}
}