package l2minius.gameserver.skills.effects;

import l2minius.gameserver.model.Effect;
import l2minius.gameserver.model.instances.NpcInstance;
import l2minius.gameserver.stats.Env;

public class EffectEnervation extends Effect
{
	public EffectEnervation(Env env, EffectTemplate template)
	{
		super(env, template);
	}

	public EffectEnervation(Effect effect)
	{
		super(effect);
	}

	@Override
	public void onStart()
	{
		super.onStart();
		if (_effected.isNpc())
		{
			((NpcInstance) _effected).setParameter("DebuffIntention", 0.5);
		}
	}

	@Override
	public boolean onActionTime()
	{
		return false;
	}

	@Override
	public void onExit()
	{
		super.onExit();
		if (_effected.isNpc())
		{
			((NpcInstance) _effected).setParameter("DebuffIntention", 1.);
		}
	}
}