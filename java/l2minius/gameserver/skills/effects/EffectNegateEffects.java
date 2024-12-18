package l2minius.gameserver.skills.effects;

import l2minius.gameserver.model.Effect;
import l2minius.gameserver.stats.Env;

public class EffectNegateEffects extends Effect
{
	public EffectNegateEffects(Env env, EffectTemplate template)
	{
		super(env, template);
	}

	public EffectNegateEffects(Effect effect)
	{
		super(effect);
	}

	@Override
	public void onStart()
	{
		super.onStart();
	}

	@Override
	public void onExit()
	{
		super.onExit();
	}

	@Override
	public boolean onActionTime()
	{
		for (Effect e : _effected.getEffectList().getAllEffects())
		{
			if (!e.getStackType().equals(EffectTemplate.NO_STACK) && (e.getStackType().equals(getStackType()) || e.getStackType().equals(getStackType2())) || !e.getStackType2().equals(EffectTemplate.NO_STACK) && (e.getStackType2().equals(getStackType()) || e.getStackType2().equals(getStackType2())))
			{
				if (e.getStackOrder() <= getStackOrder())
				{
					e.exit();
				}
			}
		}
		return false;
	}
}