package l2minius.gameserver.model.instances;

import l2minius.gameserver.templates.npc.NpcTemplate;

public class NpcNotSayInstance extends NpcInstance
{
	public NpcNotSayInstance(int objectID, NpcTemplate template)
	{
		super(objectID, template);
		setHasChatWindow(false);
	}
}
