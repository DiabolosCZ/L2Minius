package l2minius.gameserver.listener.actor;

import l2minius.gameserver.listener.CharListener;
import l2minius.gameserver.model.Creature;

public interface OnDeleteListener extends CharListener
{
	void onDelete(Creature p0);
}
