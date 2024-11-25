package events.CustomDropItems3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import l2minius.commons.util.Rnd;
import l2minius.gameserver.Config;
import l2minius.gameserver.listener.actor.OnDeathListener;
import l2minius.gameserver.model.Creature;
import l2minius.gameserver.model.actor.listener.CharListenerList;
import l2minius.gameserver.model.instances.MonsterInstance;
import l2minius.gameserver.model.instances.NpcInstance;
import l2minius.gameserver.scripts.Functions;
import l2minius.gameserver.scripts.ScriptFile;

public class CustomDropItems3 extends Functions implements ScriptFile, OnDeathListener
{
	private static final Logger _log = LoggerFactory.getLogger(CustomDropItems3.class);

	private static final int[] DROP = Config.CustomGrandBossDropItemsId;
	private static final int[] CDItemsCountDropMin = Config.CustomGrandBossDropItemsCountDropMin;
	private static final int[] CDItemsCountDropMax = Config.CustomGrandBossDropItemsCountDropMax;
	private static final double[] CustomDropItemsChance = Config.CustomGrandBossDropItemsChance;
	private static boolean ALLOW_MIN_MAX_PLAYER_LVL = Config.CustomGrandBossDropItemsAllowMinMaxPlayerLvl;
	private static final int MIN_PLAYER_LVL = Config.CustomGrandBossDropItemsMinPlayerLvl;
	private static final int MAX_PLAYER_LVL = Config.CustomGrandBossDropItemsMaxPlayerLvl;
	private static boolean ALLOW_MIN_MAX_MOB_LVL = Config.CustomGrandBossDropItemsAllowMinMaxMobLvl;
	private static final int MIN_MOB_LVL = Config.CustomGrandBossDropItemsMinMobLvl;
	private static final int MAX_MOB_LVL = Config.CustomGrandBossDropItemsMaxMobLvl;
	private static boolean ALLOW_ONLY_RB_DROPS = Config.CustomGrandBossDropItemsAllowOnlyRbDrops;
	private static boolean _active = false;

	@Override
	public void onLoad()
	{
		CharListenerList.addGlobal(this);
		if (Config.AllowCustomGrandBossDropItems)
		{
			_active = true;
			_log.info("Loaded CustomDropItems: CustomDropItems [state: activated]");
		}
		else
		{
			_log.info("Loaded CustomDropItems: CustomDropItems [state: deactivated]");
		}
	}

	@Override
	public void onReload()
	{

	}

	@Override
	public void onShutdown()
	{

	}

	@Override
	public void onDeath(Creature cha, Creature killer)
	{
		if (!ALLOW_ONLY_RB_DROPS && (cha.isBoss()))
		{
			if ((ALLOW_MIN_MAX_PLAYER_LVL && checkValidate(killer, cha, true, false)) && (ALLOW_MIN_MAX_MOB_LVL && checkValidate(killer, cha, false, true)))
			{
				dropItemMob(cha, killer);
			}
			else if ((ALLOW_MIN_MAX_PLAYER_LVL && checkValidate(killer, cha, true, false)) && !ALLOW_MIN_MAX_MOB_LVL)
			{
				dropItemMob(cha, killer);
			}
			else if (!ALLOW_MIN_MAX_PLAYER_LVL && (ALLOW_MIN_MAX_MOB_LVL && checkValidate(killer, cha, false, true)))
			{
				dropItemMob(cha, killer);
			}
			else if (!ALLOW_MIN_MAX_PLAYER_LVL && !ALLOW_MIN_MAX_MOB_LVL)
			{
				dropItemMob(cha, killer);
			}
			else
			{
				return;
			}
		}
		else if (ALLOW_ONLY_RB_DROPS && (cha.isBoss()))
		{
			if ((ALLOW_MIN_MAX_PLAYER_LVL && checkValidate(killer, cha, true, false)) && (ALLOW_MIN_MAX_MOB_LVL && checkValidate(killer, cha, false, true)))
			{
				dropItemRb(cha, killer);
			}
			else if ((ALLOW_MIN_MAX_PLAYER_LVL && checkValidate(killer, cha, true, false)) && !ALLOW_MIN_MAX_MOB_LVL)
			{
				dropItemRb(cha, killer);
			}
			else if (!ALLOW_MIN_MAX_PLAYER_LVL && (ALLOW_MIN_MAX_MOB_LVL && checkValidate(killer, cha, false, true)))
			{
				dropItemRb(cha, killer);
			}
			else if (!ALLOW_MIN_MAX_PLAYER_LVL && !ALLOW_MIN_MAX_MOB_LVL)
			{
				dropItemRb(cha, killer);
			}
			else
			{
				return;
			}
		}
		else
		{
			return;
		}
	}

	private boolean checkValidate(Creature killer, Creature mob, boolean lvlPlayer, boolean lvlMob)
	{
		if (mob == null || killer == null)
		{
			return false;
		}

		if (lvlPlayer && (killer.getLevel() >= MIN_PLAYER_LVL && killer.getLevel() <= MAX_PLAYER_LVL))
		{
			return true;
		}

		if (lvlMob && (mob.getLevel() >= MIN_MOB_LVL && mob.getLevel() <= MAX_MOB_LVL))
		{
			return true;
		}

		return false;
	}

	private void dropItemMob(Creature cha, Creature killer)
	{
		if (_active && SimpleCheckDrop(cha, killer))
		{
			for (int i = 0; i < DROP.length; i++)
			{
				if (Rnd.chance(CustomDropItemsChance[i] * killer.getPlayer().getRateItems() * ((MonsterInstance) cha).getTemplate().rateHp))
				{
					((MonsterInstance) cha).dropItem(killer.getPlayer(), DROP[i], Rnd.get(CDItemsCountDropMin[i], CDItemsCountDropMax[i]));
				}
				else
				{
					return;
				}
			}
		}
	}

	private void dropItemRb(Creature cha, Creature killer)
	{
		if (_active)
		{
			for (int i = 0; i < DROP.length; i++)
			{
				if (Rnd.chance(CustomDropItemsChance[i] * killer.getPlayer().getRateItems() * ((NpcInstance) cha).getTemplate().rateHp))
				{
					((NpcInstance) cha).dropItem(killer.getPlayer(), DROP[i], Rnd.get(CDItemsCountDropMin[i], CDItemsCountDropMax[i]));
				}
				else
				{
					return;
				}
			}
		}
	}
}