package l2minius.gameserver.network.serverpackets;

import l2minius.gameserver.instancemanager.CursedWeaponsManager;

public class ExCursedWeaponList extends L2GameServerPacket
{
	private int[] cursedWeapon_ids;

	public ExCursedWeaponList()
	{
		this.cursedWeapon_ids = CursedWeaponsManager.getInstance().getCursedWeaponsIds();
	}

	@Override
	protected final void writeImpl()
	{
		this.writeEx(0x46);
		this.writeDD(this.cursedWeapon_ids, true);
	}
}