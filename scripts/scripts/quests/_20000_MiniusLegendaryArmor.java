package quests;

import org.apache.commons.lang3.ArrayUtils;

import l2minius.commons.util.Rnd;
import l2minius.gameserver.model.instances.NpcInstance;
import l2minius.gameserver.model.quest.Quest;
import l2minius.gameserver.model.quest.QuestState;
import l2minius.gameserver.scripts.ScriptFile;

/**
 * @author pchayka
 */

public class _20000_MiniusLegendaryArmor extends Quest implements ScriptFile
{
	private static final int LegendaryNPC = 90100;
	private static final int[] LegendaryMonster =
	{
		//Red room
		22747,
		22748,
		22749,
		22750,
		22751,
		22752,
		22753,
		//Green room
		22754,
		22755,
		22756,
		22757,
		22758,
		22759,
		//Blue room
		22760,
		22761,
		22762,
		22763,
		22765,
		22753
		
		
	};
	private static final int LegendaryBones = 91100;

	public _20000_MiniusLegendaryArmor()
	{
		super(true);
		addStartNpc(LegendaryNPC);
		addKillId(LegendaryMonster);
		addQuestItem(LegendaryBones);
	}

	@Override
	public String onEvent(String event, QuestState st, NpcInstance npc)
	{
		String htmltext = event;
		if (event.equalsIgnoreCase("LegendaryNPC_q287_03.htm"))
		{
			st.setState(STARTED);
			st.setCond(1);
			st.playSound(SOUND_ACCEPT);
		}
		else if (event.equalsIgnoreCase("legendary_heavy_recipes"))
		{
			if (st.getQuestItemsCount(LegendaryBones) >= 500)
			{
				st.takeItems(LegendaryBones, 500);
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
				htmltext = "LegendaryNPC_q287_05.htm";
			}
			else
			{
				htmltext = "LegendaryNPC_q287_05.htm";
			}
		}
		else if (event.equalsIgnoreCase("legendary_light_recipes"))
		{
			if (st.getQuestItemsCount(LegendaryBones) >= 500)
			{
				st.takeItems(LegendaryBones, 500);
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
				htmltext = "LegendaryNPC_q287_05.htm";
			}
			else
			{
				htmltext = "LegendaryNPC_q287_05.htm";
			}
		}
		else if (event.equalsIgnoreCase("legendary_robe_recipes"))
		{
			if (st.getQuestItemsCount(LegendaryBones) >= 500)
			{
				st.takeItems(LegendaryBones, 500);
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
				htmltext = "LegendaryNPC_q287_05.htm";
			}
			else
			{
				htmltext = "LegendaryNPC_q287_05.htm";
			}
		}
		else if (event.equalsIgnoreCase("legendary_heavy_pieces"))
		{
			if (st.getQuestItemsCount(LegendaryBones) >= 100)
			{
				st.takeItems(LegendaryBones, 100);
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
				htmltext = "LegendaryNPC_q287_05.htm";
			}
			else
			{
				htmltext = "LegendaryNPC_q287_05.htm";
			}
		}
		else if (event.equalsIgnoreCase("legendary_light_pieces"))
		{
			if (st.getQuestItemsCount(LegendaryBones) >= 100)
			{
				st.takeItems(LegendaryBones, 100);
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
				htmltext = "LegendaryNPC_q287_05.htm";
			}
			else
			{
				htmltext = "LegendaryNPC_q287_05.htm";
			}
		}
		else if (event.equalsIgnoreCase("legendary_robe_pieces"))
		{
			if (st.getQuestItemsCount(LegendaryBones) >= 100)
			{
				st.takeItems(LegendaryBones, 100);
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
				htmltext = "LegendaryNPC_q287_05.htm";
			}
			else
			{
				htmltext = "LegendaryNPC_q287_05.htm";
			}
		}
		
		else if (event.equalsIgnoreCase("continue"))
		{
			htmltext = "LegendaryNPC_q287_05.htm";
		}
		else if (event.equalsIgnoreCase("quit"))
		{
			htmltext = "LegendaryNPC_q287_05.htm";
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
		if (npcId == LegendaryNPC)
		{
			if (cond == 0)
			{
				if (st.getPlayer().getLevel() >= 84)
				{
					htmltext = "LegendaryNPC_q287_01.htm";
				}
				else
				{
					htmltext = "LegendaryNPC_q287_00.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if (cond == 1 && st.getQuestItemsCount(LegendaryBones) < 100)
			{
				htmltext = "LegendaryNPC_q287_04.htm";
			}
			else if (cond == 1 && st.getQuestItemsCount(LegendaryBones) >= 100)
			{
				htmltext = "LegendaryNPC_q287_05.htm";
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
			if (ArrayUtils.contains(LegendaryMonster, npcId) && Rnd.chance(60))
			{
				st.giveItems(LegendaryBones, 1, true);
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