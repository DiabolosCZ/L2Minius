package events.CustomDropItems4;

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

public class CustomDropItems4 extends Functions implements ScriptFile, OnDeathListener
{
	private static final Logger _log = LoggerFactory.getLogger(CustomDropItems4.class);

	private static final int[] DROP = Config.CustomRaidBossDropItemsId;
	private static final int[] CDItemsCountDropMin = Config.CustomRaidBossDropItemsCountDropMin;
	private static final int[] CDItemsCountDropMax = Config.CustomRaidBossDropItemsCountDropMax;
	private static final double[] CustomDropItemsChance = Config.CustomRaidBossDropItemsChance;
	private static boolean ALLOW_MIN_MAX_PLAYER_LVL = Config.CustomRaidBossDropItemsAllowMinMaxPlayerLvl;
	private static final int MIN_PLAYER_LVL = Config.CustomRaidBossDropItemsMinPlayerLvl;
	private static final int MAX_PLAYER_LVL = Config.CustomRaidBossDropItemsMaxPlayerLvl;
	private static boolean ALLOW_MIN_MAX_MOB_LVL = Config.CustomRaidBossDropItemsAllowMinMaxMobLvl;
	private static final int MIN_MOB_LVL = Config.CustomRaidBossDropItemsMinMobLvl;
	private static final int MAX_MOB_LVL = Config.CustomRaidBossDropItemsMaxMobLvl;
	private static boolean ALLOW_ONLY_RB_DROPS = Config.CustomRaidBossDropItemsAllowOnlyRbDrops;
	private static boolean _active = false;

	@Override
	public void onLoad()
	{
		CharListenerList.addGlobal(this);
		if (Config.AllowCustomRaidBossDropItems)
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
		if (!ALLOW_ONLY_RB_DROPS && (cha.isRaid()))
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
		else if (ALLOW_ONLY_RB_DROPS && (cha.isRaid()))
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