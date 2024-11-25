package l2minius.gameserver.network.serverpackets;

import l2minius.gameserver.model.entity.events.impl.DuelEvent;

public class ExDuelStart extends L2GameServerPacket
{
	private int _duelType;

	public ExDuelStart(DuelEvent e)
	{
		this._duelType = e.getDuelType();
	}

	@Override
	protected final void writeImpl()
	{
		this.writeEx(0x4e);
		this.writeD(this._duelType);
	}
}