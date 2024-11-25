package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.cache.CrestCache;
import l2minius.gameserver.network.serverpackets.AllianceCrest;

public class RequestAllyCrest extends L2GameClientPacket
{
	// format: cd

	private int _crestId;

	@Override
	protected void readImpl()
	{
		this._crestId = this.readD();
	}

	@Override
	protected void runImpl()
	{
		if (this._crestId == 0)
		{
			return;
		}
		byte[] data = CrestCache.getInstance().getAllyCrest(this._crestId);
		if (data != null)
		{
			AllianceCrest ac = new AllianceCrest(this._crestId, data);
			this.sendPacket(ac);
		}
	}
}