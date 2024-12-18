package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.instancemanager.QuestManager;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.quest.Quest;

public class RequestTutorialClientEvent extends L2GameClientPacket
{
	// format: cd
	int event = 0;

	/**
	 * Пакет от клиента, если вы в туториале подергали мышкой как надо - клиент пришлет его со значением 1 ну или нужным ивентом
	 */
	@Override
	protected void readImpl()
	{
		this.event = this.readD();
	}

	@Override
	protected void runImpl()
	{
		Player player = this.getClient().getActiveChar();
		if (player == null)
		{
			return;
		}

		Quest tutorial = QuestManager.getQuest(255);
		if (tutorial != null)
		{
			player.processQuestEvent(tutorial.getName(), "CE" + this.event, null);
		}
	}
}