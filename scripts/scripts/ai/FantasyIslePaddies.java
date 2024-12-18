package ai;

import l2minius.gameserver.GameTimeController;
import l2minius.gameserver.ai.CharacterAI;
import l2minius.gameserver.data.xml.holder.EventHolder;
import l2minius.gameserver.listener.game.OnDayNightChangeListener;
import l2minius.gameserver.model.entity.events.EventType;
import l2minius.gameserver.model.entity.events.impl.FantasiIsleParadEvent;
import l2minius.gameserver.model.instances.NpcInstance;

public class FantasyIslePaddies extends CharacterAI
{
	public FantasyIslePaddies(NpcInstance actor)
	{
		super(actor);
		GameTimeController.getInstance().addListener(new StartEvent());
	}

	private class StartEvent implements OnDayNightChangeListener
	{
		private StartEvent()
		{
			if (GameTimeController.getInstance().isNowNight())
			{
				onNight();
			}
			else
			{
				onDay();
			}
		}

		/**
		 * Вызывается, когда на сервере наступает ночь
		 */
		@Override
		public void onNight()
		{
			NpcInstance actor = (NpcInstance) getActor();
			if (actor != null)
			{
				FantasiIsleParadEvent n_event = EventHolder.getInstance().getEvent(EventType.MAIN_EVENT, 10031);
				FantasiIsleParadEvent d_event = EventHolder.getInstance().getEvent(EventType.MAIN_EVENT, 10032);
				n_event.registerActions();
				d_event.stopEvent();
			}
		}

		/**
		 * Вызывается, когда на сервере наступает день
		 */
		@Override
		public void onDay()
		{
			NpcInstance actor = (NpcInstance) getActor();
			if (actor != null)
			{
				FantasiIsleParadEvent n_event = EventHolder.getInstance().getEvent(EventType.MAIN_EVENT, 10031);
				FantasiIsleParadEvent d_event = EventHolder.getInstance().getEvent(EventType.MAIN_EVENT, 10032);
				n_event.stopEvent();
				d_event.registerActions();
			}
		}
	}
}