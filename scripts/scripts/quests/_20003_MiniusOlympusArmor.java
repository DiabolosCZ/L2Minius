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

public class _20003_MiniusOlympusArmor extends Quest implements ScriptFile
{
	private static final int OlympusNPC = 90103;
	private static final int[] OlympusMonster =
	{
		22862,
		22823
		
	};
	private static final int OlympusBones = 91104;

	public _20003_MiniusOlympusArmor()
	{
		super(true);
		addStartNpc(OlympusNPC);
		addKillId(OlympusMonster);
		addQuestItem(OlympusBones);
	}

	@Override
	public String onEvent(String event, QuestState st, NpcInstance npc)
	{
		String htmltext = event;
		if (event.equalsIgnoreCase("OlympusNPC_q287_03.htm"))
		{
			st.setState(STARTED);
			st.setCond(1);
			st.playSound(SOUND_ACCEPT);
		}
		else if (event.equalsIgnoreCase("Olympus_heavy_recipes"))
		{
			if (st.getQuestItemsCount(OlympusBones) >= 500)
			{
				st.takeItems(OlympusBones, 500);
				switch (Rnd.get(1, 6))
				{
				case 1:
					st.giveItems(600033, 1);
					break;
				case 2:
					st.giveItems(600034, 1);
					break;
				case 3:
					st.giveItems(600035, 1);
					break;
				case 4:
					st.giveItems(600036, 1);
					break;
				case 5:
					st.giveItems(600037, 1);
					break;
				case 6:
					st.giveItems(600048, 1);
					break;
				}
				htmltext = "OlympusNPC_q287_05.htm";
			}
			else
			{
				htmltext = "OlympusNPC_q287_05.htm";
			}
		}
		else if (event.equalsIgnoreCase("Olympus_light_recipes"))
		{
			if (st.getQuestItemsCount(OlympusBones) >= 500)
			{
				st.takeItems(OlympusBones, 500);
				switch (Rnd.get(1, 6))
				{
				case 1:
					st.giveItems(600038, 1);
					break;
				case 2:
					st.giveItems(600039, 1);
					break;
				case 3:
					st.giveItems(600040, 1);
					break;
				case 4:
					st.giveItems(600041, 1);
					break;
				case 5:
					st.giveItems(600042, 1);
					break;
				case 6:
					st.giveItems(600048, 1);
					break;
				}
				htmltext = "OlympusNPC_q287_05.htm";
			}
			else
			{
				htmltext = "OlympusNPC_q287_05.htm";
			}
		}
		else if (event.equalsIgnoreCase("Olympus_robe_recipes"))
		{
			if (st.getQuestItemsCount(OlympusBones) >= 500)
			{
				st.takeItems(OlympusBones, 500);
				switch (Rnd.get(1, 6))
				{
				case 1:
					st.giveItems(600043, 1);
					break;
				case 2:
					st.giveItems(600044, 1);
					break;
				case 3:
					st.giveItems(600045, 1);
					break;
				case 4:
					st.giveItems(600046, 1);
					break;
				case 5:
					st.giveItems(600047, 1);
					break;
				case 6:
					st.giveItems(600048, 1);
					break;
				}
				htmltext = "OlympusNPC_q287_05.htm";
			}
			else
			{
				htmltext = "OlympusNPC_q287_05.htm";
			}
		}
		else if (event.equalsIgnoreCase("Olympus_heavy_pieces"))
		{
			if (st.getQuestItemsCount(OlympusBones) >= 100)
			{
				st.takeItems(OlympusBones, 100);
				switch (Rnd.get(1, 7))
				{
				case 1:
					st.giveItems(700033, 5);
					break;
				case 2:
					st.giveItems(700034, 5);
					break;
				case 3:
					st.giveItems(700035, 5);
					break;
				case 4:
					st.giveItems(700036, 5);
					break;
				case 5:
					st.giveItems(700037, 5);
					break;
				case 6:
					st.giveItems(700048, 5);
					break;
				}
				htmltext = "OlympusNPC_q287_05.htm";
			}
			else
			{
				htmltext = "OlympusNPC_q287_05.htm";
			}
		}
		else if (event.equalsIgnoreCase("Olympus_light_pieces"))
		{
			if (st.getQuestItemsCount(OlympusBones) >= 100)
			{
				st.takeItems(OlympusBones, 100);
				switch (Rnd.get(1, 6))
				{
				case 1:
					st.giveItems(700038, 5);
					break;
				case 2:
					st.giveItems(700039, 5);
					break;
				case 3:
					st.giveItems(700040, 5);
					break;
				case 4:
					st.giveItems(700041, 5);
					break;
				case 5:
					st.giveItems(700042, 5);
					break;
				case 6:
					st.giveItems(700048, 5);
					break;
				}
				htmltext = "OlympusNPC_q287_05.htm";
			}
			else
			{
				htmltext = "OlympusNPC_q287_05.htm";
			}
		}
		else if (event.equalsIgnoreCase("Olympus_robe_pieces"))
		{
			if (st.getQuestItemsCount(OlympusBones) >= 100)
			{
				st.takeItems(OlympusBones, 100);
				switch (Rnd.get(1, 6))
				{
				case 1:
					st.giveItems(700043, 5);
					break;
				case 2:
					st.giveItems(700044, 5);
					break;
				case 3:
					st.giveItems(700045, 5);
					break;
				case 4:
					st.giveItems(700046, 5);
					break;
				case 5:
					st.giveItems(700047, 5);
					break;
				case 6:
					st.giveItems(700048, 5);
					break;
				}
				htmltext = "OlympusNPC_q287_05.htm";
			}
			else
			{
				htmltext = "OlympusNPC_q287_05.htm";
			}
		}
		
		else if (event.equalsIgnoreCase("continue"))
		{
			htmltext = "OlympusNPC_q287_05.htm";
		}
		else if (event.equalsIgnoreCase("quit"))
		{
			htmltext = "OlympusNPC_q287_05.htm";
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
		if (npcId == OlympusNPC)
		{
			if (cond == 0)
			{
				if (st.getPlayer().getLevel() >= 82)
				{
					htmltext = "OlympusNPC_q287_01.htm";
				}
				else
				{
					htmltext = "OlympusNPC_q287_00.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if (cond == 1 && st.getQuestItemsCount(OlympusBones) < 100)
			{
				htmltext = "OlympusNPC_q287_04.htm";
			}
			else if (cond == 1 && st.getQuestItemsCount(OlympusBones) >= 100)
			{
				htmltext = "OlympusNPC_q287_05.htm";
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
			if (ArrayUtils.contains(OlympusMonster, npcId) && Rnd.chance(45))
			{
				st.giveItems(OlympusBones, 1, true);
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