package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.base.TeamType;
import l2minius.gameserver.model.entity.events.impl.UndergroundColiseumBattleEvent;
import l2minius.gameserver.network.serverpackets.ExPVPMatchRecord;

public class RequestPVPMatchRecord extends L2GameClientPacket
{
	@Override
	protected void readImpl()
	{

	}

	@Override
	protected void runImpl()
	{
		final Player player = this.getClient().getActiveChar();
		if (player == null)
		{
			return;
		}

		final UndergroundColiseumBattleEvent battleEvent = player.getEvent(UndergroundColiseumBattleEvent.class);
		if (battleEvent == null)
		{
			return;
		}

		player.sendPacket(new ExPVPMatchRecord(ExPVPMatchRecord.UPDATE, TeamType.NONE, battleEvent));
	}
}