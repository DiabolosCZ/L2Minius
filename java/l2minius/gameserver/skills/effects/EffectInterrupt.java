package l2minius.gameserver.skills.effects;

import l2minius.gameserver.model.Effect;
import l2minius.gameserver.stats.Env;

public class EffectInterrupt extends Effect
{
	public EffectInterrupt(Env env, EffectTemplate template)
	{
		super(env, template);
	}

	public EffectInterrupt(Effect effect)
	{
		super(effect);
	}

	@Override
	public void onStart()
	{
		super.onStart();
		if (!getEffected().isRaid())
		{
			getEffected().abortCast(true, true);
		}
	}

	@Override
	public boolean onActionTime()
	{
		return false;
	}
}