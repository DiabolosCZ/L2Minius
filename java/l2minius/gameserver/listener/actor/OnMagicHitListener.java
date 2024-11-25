package l2minius.gameserver.listener.actor;

import l2minius.gameserver.listener.CharListener;
import l2minius.gameserver.model.Creature;
import l2minius.gameserver.model.Skill;

public interface OnMagicHitListener extends CharListener
{
	public void onMagicHit(Creature actor, Skill skill, Creature caster);
}
