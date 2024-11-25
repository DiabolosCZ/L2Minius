package l2minius.gameserver.templates.augmentation;

import l2minius.commons.math.random.RndSelector;

public class OptionGroup
{
	private RndSelector<Integer> _options = new RndSelector<Integer>();

	public void addOptionWithChance(int option, int chance)
	{
		_options.add(Integer.valueOf(option), chance);
	}

	public Integer random()
	{
		return _options.chance(1000000);
	}
}
