package npc.model.events;

import l2minius.gameserver.model.instances.NpcInstance;
import l2minius.gameserver.templates.npc.NpcTemplate;

/**
 * @author VISTALL
 * @date 21:09/15.07.2011
 */
public class CleftVortexGateInstance extends NpcInstance
{
	public CleftVortexGateInstance(int objectId, NpcTemplate template)
	{
		super(objectId, template);
		setShowName(false);
	}
}
