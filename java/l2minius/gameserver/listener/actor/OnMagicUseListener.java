package l2minius.gameserver.listener.actor;

import l2minius.gameserver.listener.CharListener;
import l2minius.gameserver.model.Creature;
import l2minius.gameserver.model.Skill;

public interface OnMagicUseListener extends CharListener
{
	public void onMagicUse(Creature actor, Skill skill, Creature target, boolean alt);
}
