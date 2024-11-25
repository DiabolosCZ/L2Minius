package l2minius.gameserver.model.entity.tournament.listener;

import l2minius.gameserver.listener.actor.OnCurrentHpDamageListener;
import l2minius.gameserver.model.Creature;
import l2minius.gameserver.model.Skill;
import l2minius.gameserver.model.entity.tournament.ActiveBattleManager;
import l2minius.gameserver.model.entity.tournament.BattleInstance;

public class TournamentReceiveDamageListener implements OnCurrentHpDamageListener
{
	private final BattleInstance _battleInstance;

	public TournamentReceiveDamageListener(BattleInstance battleInstance)
	{
		_battleInstance = battleInstance;
	}

	@Override
	public void onCurrentHpDamage(Creature actor, double damage, Creature attacker, Skill skill)
	{
		ActiveBattleManager.onReceivedDamage(_battleInstance, attacker, actor, damage);
	}
}
