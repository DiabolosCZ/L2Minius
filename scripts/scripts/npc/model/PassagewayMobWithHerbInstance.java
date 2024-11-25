package npc.model;

import l2minius.gameserver.model.Creature;
import l2minius.gameserver.model.instances.MonsterInstance;
import l2minius.gameserver.templates.npc.NpcTemplate;

/**
 * Моб при смерти дропает херб "Fiery Demon Blood"
 * @author SYS
 */
public final class PassagewayMobWithHerbInstance extends MonsterInstance
{
	public PassagewayMobWithHerbInstance(int objectId, NpcTemplate template)
	{
		super(objectId, template);
	}

	public static final int FieryDemonBloodHerb = 9849;

	@Override
	public void calculateRewards(Creature lastAttacker)
	{
		if (lastAttacker == null)
		{
			return;
		}

		super.calculateRewards(lastAttacker);

		if (lastAttacker.isPlayable())
		{
			dropItem(lastAttacker.getPlayer(), FieryDemonBloodHerb, 1);
		}
	}
}