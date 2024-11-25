package l2minius.gameserver.listener.actor.ai;

import l2minius.gameserver.ai.CtrlEvent;
import l2minius.gameserver.listener.AiListener;
import l2minius.gameserver.model.Creature;

public interface OnAiEventListener extends AiListener
{
	public void onAiEvent(Creature actor, CtrlEvent evt, Object[] args);
}
