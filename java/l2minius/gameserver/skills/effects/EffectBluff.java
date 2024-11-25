package l2minius.gameserver.skills.effects;

import l2minius.gameserver.model.Effect;
import l2minius.gameserver.network.serverpackets.FinishRotating;
import l2minius.gameserver.network.serverpackets.StartRotating;
import l2minius.gameserver.stats.Env;

public final class EffectBluff extends Effect
{
	public EffectBluff(Env env, EffectTemplate template)
	{
		super(env, template);
	}

	public EffectBluff(Effect effect)
	{
		super(effect);
	}

	@Override
	public boolean checkCondition()
	{
		if (getEffected().isNpc() && !getEffected().isMonster())
		{
			return false;
		}
		return super.checkCondition();
	}

	@Override
	public void onStart()
	{
		getEffected().broadcastPacket(new StartRotating(getEffected(), getEffected().getHeading(), 1, 65535));
		getEffected().broadcastPacket(new FinishRotating(getEffected(), getEffector().getHeading(), 65535));
		getEffected().setHeading(getEffector().getHeading());
	}

	@Override
	public boolean isHidden()
	{
		return true;
	}

	@Override
	public boolean onActionTime()
	{
		return false;
	}
}