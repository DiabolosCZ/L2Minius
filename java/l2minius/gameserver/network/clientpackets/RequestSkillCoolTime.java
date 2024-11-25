package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.model.Player;
import l2minius.gameserver.network.serverpackets.SkillCoolTime;

public class RequestSkillCoolTime extends L2GameClientPacket
{
	@Override
	protected void readImpl()
	{

	}

	@Override
	protected void runImpl()
	{
		Player player = this.getClient().getActiveChar();
		if (player == null)
		{
			return;
		}

		player.sendPacket(new SkillCoolTime(player));
	}
}