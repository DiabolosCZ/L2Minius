package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.data.xml.holder.ResidenceHolder;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.entity.residence.Fortress;
import l2minius.gameserver.network.serverpackets.ExShowFortressMapInfo;

public class RequestFortressMapInfo extends L2GameClientPacket
{
	private int _fortressId;

	@Override
	protected void readImpl()
	{
		this._fortressId = this.readD();
	}

	@Override
	protected void runImpl()
	{
		Player player = this.getClient().getActiveChar();
		if (player == null)
		{
			return;
		}
		Fortress fortress = ResidenceHolder.getInstance().getResidence(Fortress.class, this._fortressId);
		if (fortress != null)
		{
			this.sendPacket(new ExShowFortressMapInfo(fortress));
		}
	}
}