package l2minius.gameserver.listener.actor;

import l2minius.gameserver.listener.CharListener;
import l2minius.gameserver.model.Creature;

public interface OnDeathListener extends CharListener
{
	public void onDeath(Creature actor, Creature killer);
}
