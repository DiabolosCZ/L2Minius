package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.pledge.Clan;
import l2minius.gameserver.network.serverpackets.PledgeReceiveWarList;

public class RequestPledgeWarList extends L2GameClientPacket
{
	// format: (ch)dd
	static int _type;
	private int _page;

	@Override
	protected void readImpl()
	{
		this._page = this.readD();
		_type = this.readD();
	}

	@Override
	protected void runImpl()
	{
		Player activeChar = this.getClient().getActiveChar();
		if (activeChar == null)
		{
			return;
		}

		Clan clan = activeChar.getClan();
		if (clan != null)
		{
			activeChar.sendPacket(new PledgeReceiveWarList(clan, _type, this._page));
		}
	}
}