package quests;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bosses.BaylorManager;
import gnu.trove.map.hash.TIntObjectHashMap;
import l2minius.commons.util.Rnd;
import l2minius.gameserver.Config;
import l2minius.gameserver.ai.CtrlEvent;
import l2minius.gameserver.instancemanager.ReflectionManager;
import l2minius.gameserver.model.GameObjectsStorage;
import l2minius.gameserver.model.Party;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.Skill;
import l2minius.gameserver.model.entity.Reflection;
import l2minius.gameserver.model.instances.NpcInstance;
import l2minius.gameserver.model.quest.Quest;
import l2minius.gameserver.model.quest.QuestState;
import l2minius.gameserver.scripts.ScriptFile;
import l2minius.gameserver.stats.Stats;
import l2minius.gameserver.stats.funcs.FuncMul;
import l2minius.gameserver.utils.GCSArray;
import l2minius.gameserver.utils.Location;
import l2minius.gameserver.utils.ReflectionUtils;
/**
 * @author pchayka
 */

public class _20005_MiniusPoseidonArmor extends Quest implements ScriptFile
{
	private static final int INCSTANCED_ZONE_ID = 1000;
	private static final int PoseidonNPC = 90105;
	private static final int[] PoseidonMonster =
	{
		22828,
		22827
		
	};
	private static final int PoseidonDropItem = 91103;
	
	public class World
	{
		public int instanceId;
		public int status;
		public int killedCaptains;
		public int bosses;
		public List<Integer> rewarded;
	}
	
	public _20005_MiniusPoseidonArmor()
	{
		super(true);
		addStartNpc(PoseidonNPC);
		addKillId(PoseidonMonster);
		addQuestItem(PoseidonDropItem);
	}
	
	private void enterInstance(Player player, int type)
	{
		Reflection r = player.getActiveReflection();
		if (r != null)
		{
			if (player.canReenterInstance(INCSTANCED_ZONE_ID))
			{
				player.teleToLocation(r.getTeleportLoc(), r);
			}
		}
		else if (player.canEnterInstance(INCSTANCED_ZONE_ID))
		{
			Reflection ref = ReflectionUtils.enterReflection(player, INCSTANCED_ZONE_ID);
			World world = new World();
			world.rewarded = new ArrayList<Integer>();
			world.bosses = 5;
			world.instanceId = ref.getId();
		}
	}

	@Override
	public String onEvent(String event, QuestState st, NpcInstance npc)
	{
		String htmltext = event;
		Player player = st.getPlayer();
		
		if (event.equalsIgnoreCase("PoseidonNPC_q287_03.htm"))
		{
			st.setState(STARTED);
			st.setCond(1);
			st.playSound(SOUND_ACCEPT);
		}
		if (event.equalsIgnoreCase("EnterPoseidonArmorInstance"))
		{
			st.setState(STARTED);
			enterInstance(player, 1);
			return null;
		}
		else if (event.equalsIgnoreCase("Ixion_heavy_recipes"))
		{
			if (st.getQuestItemsCount(PoseidonDropItem) >= 500)
			{
				st.takeItems(PoseidonDropItem, 500);
				switch (Rnd.get(1, 7))
				{
				case 1:
					st.giveItems(600083, 1);
					break;
				case 2:
					st.giveItems(600084, 1);
					break;
				case 3:
					st.giveItems(600085, 1);
					break;
				case 4:
					st.giveItems(600086, 1);
					break;
				case 5:
					st.giveItems(600087, 1);
					break;
				case 6:
					st.giveItems(600098, 1);
					break;
				case 7:
					st.giveItems(600099, 1);
					break;
				}
				htmltext = "PoseidonNPC_q287_05.htm";
			}
			else
			{
				htmltext = "PoseidonNPC_q287_05.htm";
			}
		}
		else if (event.equalsIgnoreCase("Ixion_light_recipes"))
		{
			if (st.getQuestItemsCount(PoseidonDropItem) >= 500)
			{
				st.takeItems(PoseidonDropItem, 500);
				switch (Rnd.get(1, 6))
				{
				case 1:
					st.giveItems(600088, 1);
					break;
				case 2:
					st.giveItems(600089, 1);
					break;
				case 3:
					st.giveItems(600090, 1);
					break;
				case 4:
					st.giveItems(600091, 1);
					break;
				case 5:
					st.giveItems(600092, 1);
					break;
				case 6:
					st.giveItems(600098, 1);
					break;
				}
				htmltext = "PoseidonNPC_q287_05.htm";
			}
			else
			{
				htmltext = "PoseidonNPC_q287_05.htm";
			}
		}
		else if (event.equalsIgnoreCase("Ixion_robe_recipes"))
		{
			if (st.getQuestItemsCount(PoseidonDropItem) >= 500)
			{
				st.takeItems(PoseidonDropItem, 500);
				switch (Rnd.get(1, 6))
				{
				case 1:
					st.giveItems(600093, 1);
					break;
				case 2:
					st.giveItems(600094, 1);
					break;
				case 3:
					st.giveItems(600095, 1);
					break;
				case 4:
					st.giveItems(600096, 1);
					break;
				case 5:
					st.giveItems(600097, 1);
					break;
				case 6:
					st.giveItems(600098, 1);
					break;
				}
				htmltext = "PoseidonNPC_q287_05.htm";
			}
			else
			{
				htmltext = "PoseidonNPC_q287_05.htm";
			}
		}
		else if (event.equalsIgnoreCase("Ixion_heavy_pieces"))
		{
			if (st.getQuestItemsCount(PoseidonDropItem) >= 100)
			{
				st.takeItems(PoseidonDropItem, 100);
				switch (Rnd.get(1, 7))
				{
				case 1:
					st.giveItems(700083, 5);
					break;
				case 2:
					st.giveItems(700084, 5);
					break;
				case 3:
					st.giveItems(700085, 5);
					break;
				case 4:
					st.giveItems(700086, 5);
					break;
				case 5:
					st.giveItems(700087, 5);
					break;
				case 6:
					st.giveItems(700098, 5);
					break;
				case 7:
					st.giveItems(700099, 5);
					break;
				}
				htmltext = "PoseidonNPC_q287_05.htm";
			}
			else
			{
				htmltext = "PoseidonNPC_q287_05.htm";
			}
		}
		else if (event.equalsIgnoreCase("Ixion_light_pieces"))
		{
			if (st.getQuestItemsCount(PoseidonDropItem) >= 100)
			{
				st.takeItems(PoseidonDropItem, 100);
				switch (Rnd.get(1, 6))
				{
				case 1:
					st.giveItems(700088, 5);
					break;
				case 2:
					st.giveItems(700089, 5);
					break;
				case 3:
					st.giveItems(700090, 5);
					break;
				case 4:
					st.giveItems(700091, 5);
					break;
				case 5:
					st.giveItems(700092, 5);
					break;
				case 6:
					st.giveItems(700098, 5);
					break;
				}
				htmltext = "PoseidonNPC_q287_05.htm";
			}
			else
			{
				htmltext = "PoseidonNPC_q287_05.htm";
			}
		}
		else if (event.equalsIgnoreCase("Ixion_robe_pieces"))
		{
			if (st.getQuestItemsCount(PoseidonDropItem) >= 100)
			{
				st.takeItems(PoseidonDropItem, 100);
				switch (Rnd.get(1, 6))
				{
				case 1:
					st.giveItems(700093, 5);
					break;
				case 2:
					st.giveItems(700094, 5);
					break;
				case 3:
					st.giveItems(700095, 5);
					break;
				case 4:
					st.giveItems(700096, 5);
					break;
				case 5:
					st.giveItems(700097, 5);
					break;
				case 6:
					st.giveItems(700098, 5);
					break;
				}
				htmltext = "PoseidonNPC_q287_05.htm";
			}
			else
			{
				htmltext = "PoseidonNPC_q287_05.htm";
			}
		}
		
		else if (event.equalsIgnoreCase("continue"))
		{
			htmltext = "PoseidonNPC_q287_05.htm";
		}
		else if (event.equalsIgnoreCase("quit"))
		{
			htmltext = "PoseidonNPC_q287_05.htm";
			st.exitCurrentQuest(true);
		}
		return htmltext;
	}

	@Override
	public String onTalk(NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getCond();
		if (npcId == PoseidonNPC)
		{
			if (cond == 0)
			{
				if (st.getPlayer().getLevel() >= 82)
				{
					htmltext = "PoseidonNPC_q287_01.htm";
				}
				else
				{
					htmltext = "PoseidonNPC_q287_00.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if (cond == 1 && st.getQuestItemsCount(PoseidonDropItem) < 100)
			{
				htmltext = "PoseidonNPC_q287_04.htm";
			}
			else if (cond == 1 && st.getQuestItemsCount(PoseidonDropItem) >= 100)
			{
				htmltext = "PoseidonNPC_q287_05.htm";
			}
		}
		return htmltext;
	}
		
	@Override
	public String onKill(NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getCond();
		if (cond == 1)
		{
			if (ArrayUtils.contains(PoseidonMonster, npcId) && Rnd.chance(60))
			{
				st.giveItems(PoseidonDropItem, 1, true);
			}
		}
		return null;
	}

	@Override
	public void onLoad()
	{
	}

	@Override
	public void onReload()
	{
	}

	@Override
	public void onShutdown()
	{
	}
}