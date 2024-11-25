package ai.monas.FurnaceSpawnRoom;

import l2minius.commons.threading.RunnableImpl;
import l2minius.gameserver.ThreadPoolManager;
import l2minius.gameserver.ai.DefaultAI;
import l2minius.gameserver.data.xml.holder.EventHolder;
import l2minius.gameserver.model.Creature;
import l2minius.gameserver.model.entity.events.EventType;
import l2minius.gameserver.model.entity.events.impl.MonasteryFurnaceEvent;
import l2minius.gameserver.model.instances.NpcInstance;
import l2minius.gameserver.network.serverpackets.components.NpcString;
import l2minius.gameserver.scripts.Functions;

/**
 * @author claww
 *         - AI for Furnace (18914).
 * 		   - The spawn its own nickname.
 * 		   - If the attack comes, the player resets target includes the Target inactivity.
 *		   - Screams in the chat during the impact.
 * 		   - After 15 seconds, removing any brazier.
 * 		   - Event starts and the room with the monsters spawn soldiers.
 * 		   - AI is tested and works.
 */
public class FurnaceBalance extends DefaultAI
{
	private boolean _firstTimeAttacked = true;

	public FurnaceBalance(NpcInstance actor)
	{
		super(actor);
		actor.setNameNpcString(NpcString.FURN2);
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int damage)
	{
		NpcInstance actor = getActor();
		if (actor == null)
		{
			return;
		}

		int event_id = actor.getAISpawnParam();
		MonasteryFurnaceEvent furnace = EventHolder.getInstance().getEvent(EventType.MAIN_EVENT, event_id);

		if (_firstTimeAttacked && !furnace.isInProgress())
		{
			_firstTimeAttacked = false;
			attacker.setTarget(null);
			actor.setTargetable(false);
			actor.setNpcState((byte) 1);
			Functions.npcShout(actor, NpcString.FURN1);
			furnace.registerActions();
			ThreadPoolManager.getInstance().schedule(new ScheduleTimerTask(), 15000);
		}

		super.onEvtAttacked(attacker, damage);
	}

	private class ScheduleTimerTask extends RunnableImpl
	{
		@Override
		public void runImpl()
		{
			NpcInstance actor = getActor();
			int event_id = actor.getAISpawnParam();
			MonasteryFurnaceEvent furnace = EventHolder.getInstance().getEvent(EventType.MAIN_EVENT, event_id);
			furnace.spawnAction(MonasteryFurnaceEvent.STANDART_ROOM, true);
		}
	}

	@Override
	protected void onEvtDead(Creature killer)
	{
		_firstTimeAttacked = true;
		super.onEvtDead(killer);
	}
}