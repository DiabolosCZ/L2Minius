package l2minius.gameserver.listener.actor;

import l2minius.gameserver.listener.CharListener;
import l2minius.gameserver.model.Creature;

/**
 * @author VISTALL
 */
public interface OnReviveListener extends CharListener
{
	public void onRevive(Creature actor);
}
