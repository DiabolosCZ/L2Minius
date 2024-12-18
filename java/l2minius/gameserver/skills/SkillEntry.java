package l2minius.gameserver.skills;

import java.util.AbstractMap;

import l2minius.gameserver.model.Skill;

public class SkillEntry extends AbstractMap.SimpleImmutableEntry<SkillEntryType, Skill>
{
	private static final long serialVersionUID = 8420914670988897657L;
	private boolean _disabled;

	public SkillEntry(SkillEntryType key, Skill value)
	{
		super(key, value);
	}

	public boolean isDisabled()
	{
		return _disabled;
	}

	public void setDisabled(boolean disabled)
	{
		_disabled = disabled;
	}
}
