package l2minius.gameserver.listener.actor;

import l2minius.gameserver.listener.CharListener;
import l2minius.gameserver.model.Creature;

public interface OnAttackListener extends CharListener
{
	public void onAttack(Creature actor, Creature target);
}
