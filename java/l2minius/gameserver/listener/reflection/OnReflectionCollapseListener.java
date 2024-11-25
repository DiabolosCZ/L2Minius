package l2minius.gameserver.listener.reflection;

import l2minius.commons.listener.Listener;
import l2minius.gameserver.model.entity.Reflection;

public interface OnReflectionCollapseListener extends Listener<Reflection>
{
	public void onReflectionCollapse(Reflection reflection);
}
