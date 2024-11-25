package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.pledge.Clan;
import l2minius.gameserver.model.pledge.RankPrivs;
import l2minius.gameserver.network.serverpackets.PledgePowerGradeList;

public class RequestPledgePowerGradeList extends L2GameClientPacket
{
	@Override
	protected void readImpl()
	{
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
			RankPrivs[] privs = clan.getAllRankPrivs();
			activeChar.sendPacket(new PledgePowerGradeList(privs));
		}
	}
}