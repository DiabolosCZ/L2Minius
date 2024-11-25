package l2minius.gameserver.network.serverpackets;

import l2minius.gameserver.model.instances.DoorInstance;

/**
 * 60
 * d6 6d c0 4b		door id
 * 8f 14 00 00 		x
 * b7 f1 00 00 		y
 * 60 f2 ff ff 		z
 * 00 00 00 00 		??
 *
 * format  dddd    rev 377  ID:%d X:%d Y:%d Z:%d
 *         ddddd   rev 419
 */
public class DoorInfo extends L2GameServerPacket
{
	private int obj_id, door_id, view_hp;

	// @Deprecated
	public DoorInfo(DoorInstance door)
	{
		this.obj_id = door.getObjectId();
		this.door_id = door.getDoorId();
		this.view_hp = door.isHPVisible() ? 1 : 0;
	}

	@Override
	protected final void writeImpl()
	{
		this.writeC(0x4C);
		this.writeD(this.obj_id);
		this.writeD(this.door_id);
		this.writeD(this.view_hp); // отображать ли хп у двери или стены
	}
}