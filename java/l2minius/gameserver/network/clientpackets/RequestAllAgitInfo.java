package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.network.serverpackets.ExShowAgitInfo;

public class RequestAllAgitInfo extends L2GameClientPacket
{
	@Override
	protected void readImpl()
	{
	}

	@Override
	protected void runImpl()
	{
		this.getClient().getActiveChar().sendPacket(new ExShowAgitInfo());
	}
}