package l2minius.gameserver.model.instances;

import l2minius.gameserver.ai.CtrlEvent;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.Skill;
import l2minius.gameserver.templates.npc.NpcTemplate;

public class ChestInstance extends MonsterInstance
{
	public ChestInstance(int objectId, NpcTemplate template)
	{
		super(objectId, template);
	}

	public void tryOpen(Player opener, Skill skill)
	{
		getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, opener, 100);
	}

	@Override
	public boolean canChampion()
	{
		return false;
	}
}