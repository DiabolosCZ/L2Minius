package l2minius.gameserver.skills.effects;

import l2minius.gameserver.model.Effect;
import l2minius.gameserver.stats.Env;

public class EffectCharmOfCourage extends Effect
{
	public EffectCharmOfCourage(Env env, EffectTemplate template)
	{
		super(env, template);
	}

	public EffectCharmOfCourage(Effect effect)
	{
		super(effect);
	}

	@Override
	public void onStart()
	{
		super.onStart();
		if (_effected.isPlayer())
		{
			_effected.getPlayer().setCharmOfCourage(true);
		}
	}

	@Override
	public void onExit()
	{
		super.onExit();
		_effected.getPlayer().setCharmOfCourage(false);
	}

	@Override
	public boolean onActionTime()
	{
		return false;
	}
}