package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.network.serverpackets.QuestList;

public class RequestQuestList extends L2GameClientPacket
{
	@Override
	protected void readImpl()
	{
	}

	@Override
	protected void runImpl()
	{
		this.sendPacket(new QuestList(this.getClient().getActiveChar()));
	}
}