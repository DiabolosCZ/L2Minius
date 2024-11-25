package l2minius.gameserver.skills.effects;

import l2minius.gameserver.model.Effect;
import l2minius.gameserver.stats.Env;

public class EffectMuteAttack extends Effect
{
	public EffectMuteAttack(Env env, EffectTemplate template)
	{
		super(env, template);
	}

	public EffectMuteAttack(Effect effect)
	{
		super(effect);
	}

	@Override
	public void onStart()
	{
		super.onStart();

		if (!_effected.startAMuted())
		{
			_effected.abortCast(true, true);
			_effected.abortAttack(true, true);
		}
	}

	@Override
	public void onExit()
	{
		super.onExit();
		_effected.stopAMuted();
	}

	@Override
	public boolean onActionTime()
	{
		return false;
	}
}