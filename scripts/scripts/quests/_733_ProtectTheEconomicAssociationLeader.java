package quests;

import l2minius.gameserver.data.xml.holder.EventHolder;
import l2minius.gameserver.model.entity.events.EventType;
import l2minius.gameserver.model.entity.events.impl.DominionSiegeRunnerEvent;
import l2minius.gameserver.model.quest.Quest;
import l2minius.gameserver.scripts.ScriptFile;

/**
 * @author VISTALL
 * @date 8:17/10.06.2011
 */
public class _733_ProtectTheEconomicAssociationLeader extends Quest implements ScriptFile
{
	public _733_ProtectTheEconomicAssociationLeader()
	{
		super(PARTY_NONE);
		DominionSiegeRunnerEvent runnerEvent = EventHolder.getInstance().getEvent(EventType.MAIN_EVENT, 1);
		runnerEvent.addBreakQuest(this);
	}

	@Override
	public void onLoad()
	{

	}

	@Override
	public void onReload()
	{

	}

	@Override
	public void onShutdown()
	{

	}
}