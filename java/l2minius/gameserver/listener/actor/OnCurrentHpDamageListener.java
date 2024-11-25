package l2minius.gameserver.listener.actor;

import l2minius.gameserver.listener.CharListener;
import l2minius.gameserver.model.Creature;
import l2minius.gameserver.model.Skill;

public interface OnCurrentHpDamageListener extends CharListener
{
	public void onCurrentHpDamage(Creature actor, double damage, Creature attacker, Skill skill);
}
