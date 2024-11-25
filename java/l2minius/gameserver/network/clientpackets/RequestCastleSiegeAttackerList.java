package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.data.xml.holder.ResidenceHolder;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.entity.residence.Residence;
import l2minius.gameserver.network.serverpackets.CastleSiegeAttackerList;

public class RequestCastleSiegeAttackerList extends L2GameClientPacket
{
	private int _unitId;

	@Override
	protected void readImpl()
	{
		this._unitId = this.readD();
	}

	@Override
	protected void runImpl()
	{
		Player player = this.getClient().getActiveChar();
		if (player == null)
		{
			return;
		}

		Residence residence = ResidenceHolder.getInstance().getResidence(this._unitId);
		if (residence != null)
		{
			this.sendPacket(new CastleSiegeAttackerList(residence));
		}
	}
}