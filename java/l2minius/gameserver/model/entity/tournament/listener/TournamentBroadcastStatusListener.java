package l2minius.gameserver.model.entity.tournament.listener;

import l2minius.gameserver.listener.actor.OnStatusUpdateBroadcastListener;
import l2minius.gameserver.model.Creature;
import l2minius.gameserver.model.Playable;
import l2minius.gameserver.model.entity.tournament.BattleInstance;
import l2minius.gameserver.model.entity.tournament.BattleObservationManager;

public class TournamentBroadcastStatusListener implements OnStatusUpdateBroadcastListener
{
	private final BattleInstance battleInstance;

	public TournamentBroadcastStatusListener(BattleInstance battleInstance)
	{
		super();
		this.battleInstance = battleInstance;
	}

	@Override
	public void onStatusUpdate(Creature creature)
	{
		if (creature.isPlayable())
		{
			BattleObservationManager.broadcastFighterStatusUpdate(battleInstance, (Playable) creature);
			return;
		}
		throw new AssertionError(creature + " present in " + this.getClass().getName());
	}
}
