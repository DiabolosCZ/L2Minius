package l2minius.gameserver.skills.skillclasses;

import java.util.List;

import l2minius.commons.threading.RunnableImpl;
import l2minius.gameserver.ThreadPoolManager;
import l2minius.gameserver.model.Creature;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.Skill;
import l2minius.gameserver.model.instances.FeedableBeastInstance;
import l2minius.gameserver.templates.StatsSet;

public class BeastFeed extends Skill
{
	public BeastFeed(StatsSet set)
	{
		super(set);
	}

	@Override
	public void useSkill(Creature activeChar, List<Creature> targets)
	{
		for (Creature target : targets)
		{
			ThreadPoolManager.getInstance().execute(new RunnableImpl()
			{
				@Override
				public void runImpl() throws Exception
				{
					if (target instanceof FeedableBeastInstance)
					{
						((FeedableBeastInstance) target).onSkillUse((Player) activeChar, _id);
					}
				}
			});
		}
	}
}
