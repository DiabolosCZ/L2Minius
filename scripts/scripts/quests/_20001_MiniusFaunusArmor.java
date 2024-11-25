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

public class _20001_MiniusFaunusArmor extends Quest implements ScriptFile
{
	private static final int FaunusNPC = 90101;
	private static final int[] FaunusMonster =
	{
		22830,
		22861,
		22826
		
	};
	private static final int FaunusBones = 91101;

	public _20001_MiniusFaunusArmor()
	{
		super(true);
		addStartNpc(FaunusNPC);
		addKillId(FaunusMonster);
		addQuestItem(FaunusBones);
	}

	@Override
	public String onEvent(String event, QuestState st, NpcInstance npc)
	{
		String htmltext = event;
		if (event.equalsIgnoreCase("FaunusNPC_q287_03.htm"))
		{
			st.setState(STARTED);
			st.setCond(1);
			st.playSound(SOUND_ACCEPT);
		}
		else if (event.equalsIgnoreCase("Faunus_heavy_recipes"))
		{
			if (st.getQuestItemsCount(FaunusBones) >= 350)
			{
				st.takeItems(FaunusBones, 350);
				switch (Rnd.get(1, 6))
				{
				case 1:
					st.giveItems(600000, 1);
					break;
				case 2:
					st.giveItems(600001, 1);
					break;
				case 3:
					st.giveItems(600002, 1);
					break;
				case 4:
					st.giveItems(600003, 1);
					break;
				case 5:
					st.giveItems(600004, 1);
					break;
				case 6:
					st.giveItems(600015, 1);
					break;
				}
				htmltext = "FaunusNPC_q287_05.htm";
			}
			else
			{
				htmltext = "FaunusNPC_q287_05.htm";
			}
		}
		else if (event.equalsIgnoreCase("Faunus_light_recipes"))
		{
			if (st.getQuestItemsCount(FaunusBones) >= 350)
			{
				st.takeItems(FaunusBones, 350);
				switch (Rnd.get(1, 6))
				{
				case 1:
					st.giveItems(600005, 1);
					break;
				case 2:
					st.giveItems(600006, 1);
					break;
				case 3:
					st.giveItems(600007, 1);
					break;
				case 4:
					st.giveItems(600008, 1);
					break;
				case 5:
					st.giveItems(600009, 1);
					break;
				case 6:
					st.giveItems(600015, 1);
					break;
				}
				htmltext = "FaunusNPC_q287_05.htm";
			}
			else
			{
				htmltext = "FaunusNPC_q287_05.htm";
			}
		}
		else if (event.equalsIgnoreCase("Faunus_robe_recipes"))
		{
			if (st.getQuestItemsCount(FaunusBones) >= 350)
			{
				st.takeItems(FaunusBones, 350);
				switch (Rnd.get(1, 6))
				{
				case 1:
					st.giveItems(600010, 1);
					break;
				case 2:
					st.giveItems(600011, 1);
					break;
				case 3:
					st.giveItems(600012, 1);
					break;
				case 4:
					st.giveItems(600013, 1);
					break;
				case 5:
					st.giveItems(600014, 1);
					break;
				case 6:
					st.giveItems(600015, 1);
					break;
				}
				htmltext = "FaunusNPC_q287_05.htm";
			}
			else
			{
				htmltext = "FaunusNPC_q287_05.htm";
			}
		}
		else if (event.equalsIgnoreCase("Faunus_heavy_pieces"))
		{
			if (st.getQuestItemsCount(FaunusBones) >= 75)
			{
				st.takeItems(FaunusBones, 75);
				switch (Rnd.get(1, 6))
				{
				case 1:
					st.giveItems(700000, 5);
					break;
				case 2:
					st.giveItems(700001, 5);
					break;
				case 3:
					st.giveItems(700002, 5);
					break;
				case 4:
					st.giveItems(700003, 5);
					break;
				case 5:
					st.giveItems(700004, 5);
					break;
				case 6:
					st.giveItems(700015, 5);
					break;
				}
				htmltext = "FaunusNPC_q287_05.htm";
			}
			else
			{
				htmltext = "FaunusNPC_q287_05.htm";
			}
		}
		else if (event.equalsIgnoreCase("Faunus_light_pieces"))
		{
			if (st.getQuestItemsCount(FaunusBones) >= 75)
			{
				st.takeItems(FaunusBones, 75);
				switch (Rnd.get(1, 6))
				{
				case 1:
					st.giveItems(700005, 5);
					break;
				case 2:
					st.giveItems(700006, 5);
					break;
				case 3:
					st.giveItems(700007, 5);
					break;
				case 4:
					st.giveItems(700008, 5);
					break;
				case 5:
					st.giveItems(700009, 5);
					break;
				case 6:
					st.giveItems(700015, 5);
					break;
				}
				htmltext = "FaunusNPC_q287_05.htm";
			}
			else
			{
				htmltext = "FaunusNPC_q287_05.htm";
			}
		}
		else if (event.equalsIgnoreCase("Faunus_robe_pieces"))
		{
			if (st.getQuestItemsCount(FaunusBones) >= 75)
			{
				st.takeItems(FaunusBones, 75);
				switch (Rnd.get(1, 6))
				{
				case 1:
					st.giveItems(700010, 5);
					break;
				case 2:
					st.giveItems(700011, 5);
					break;
				case 3:
					st.giveItems(700012, 5);
					break;
				case 4:
					st.giveItems(700013, 5);
					break;
				case 5:
					st.giveItems(700014, 5);
					break;
				case 6:
					st.giveItems(700015, 5);
					break;
				}
				htmltext = "FaunusNPC_q287_05.htm";
			}
			else
			{
				htmltext = "FaunusNPC_q287_05.htm";
			}
		}
		
		else if (event.equalsIgnoreCase("continue"))
		{
			htmltext = "FaunusNPC_q287_05.htm";
		}
		else if (event.equalsIgnoreCase("quit"))
		{
			htmltext = "FaunusNPC_q287_05.htm";
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
		if (npcId == FaunusNPC)
		{
			if (cond == 0)
			{
				if (st.getPlayer().getLevel() >= 82)
				{
					htmltext = "FaunusNPC_q287_01.htm";
				}
				else
				{
					htmltext = "FaunusNPC_q287_00.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if (cond == 1 && st.getQuestItemsCount(FaunusBones) < 75)
			{
				htmltext = "FaunusNPC_q287_04.htm";
			}
			else if (cond == 1 && st.getQuestItemsCount(FaunusBones) >= 75)
			{
				htmltext = "FaunusNPC_q287_05.htm";
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
			if (ArrayUtils.contains(FaunusMonster, npcId) && Rnd.chance(45))
			{
				st.giveItems(FaunusBones, 1, true);
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