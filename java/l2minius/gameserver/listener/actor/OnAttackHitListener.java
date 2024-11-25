package l2minius.gameserver.listener.actor;

import l2minius.gameserver.listener.CharListener;
import l2minius.gameserver.model.Creature;

public interface OnAttackHitListener extends CharListener
{
	public void onAttackHit(Creature actor, Creature attacker);
}
