package l2minius.gameserver.listener.actor.ai;

import l2minius.gameserver.ai.CtrlIntention;
import l2minius.gameserver.listener.AiListener;
import l2minius.gameserver.model.Creature;

public interface OnAiIntentionListener extends AiListener
{
	public void onAiIntention(Creature actor, CtrlIntention intention, Object arg0, Object arg1);
}
