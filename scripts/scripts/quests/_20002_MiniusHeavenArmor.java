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

public class _20002_MiniusHeavenArmor extends Quest implements ScriptFile
{
	private static final int HeavenNPC = 90102;
	private static final int[] HeavenMonster =
	{
		22815,
		22858
		
	};
	private static final int HeavenBones = 91102;

	public _20002_MiniusHeavenArmor()
	{
		super(true);
		addStartNpc(HeavenNPC);
		addKillId(HeavenMonster);
		addQuestItem(HeavenBones);
	}

	@Override
	public String onEvent(String event, QuestState st, NpcInstance npc)
	{
		String htmltext = event;
		if (event.equalsIgnoreCase("HeavenNPC_q287_03.htm"))
		{
			st.setState(STARTED);
			st.setCond(1);
			st.playSound(SOUND_ACCEPT);
		}
		else if (event.equalsIgnoreCase("Heaven_heavy_recipes"))
		{
			if (st.getQuestItemsCount(HeavenBones) >= 250)
			{
				st.takeItems(HeavenBones, 250);
				switch (Rnd.get(1, 7))
				{
				case 1:
					st.giveItems(600016, 1);
					break;
				case 2:
					st.giveItems(600017, 1);
					break;
				case 3:
					st.giveItems(600018, 1);
					break;
				case 4:
					st.giveItems(600019, 1);
					break;
				case 5:
					st.giveItems(600020, 1);
					break;
				case 6:
					st.giveItems(600031, 1);
					break;
				case 7:
					st.giveItems(600032, 1);
					break;
				}
				htmltext = "HeavenNPC_q287_05.htm";
			}
			else
			{
				htmltext = "HeavenNPC_q287_05.htm";
			}
		}
		else if (event.equalsIgnoreCase("Heaven_light_recipes"))
		{
			if (st.getQuestItemsCount(HeavenBones) >= 250)
			{
				st.takeItems(HeavenBones, 250);
				switch (Rnd.get(1, 6))
				{
				case 1:
					st.giveItems(600021, 1);
					break;
				case 2:
					st.giveItems(600022, 1);
					break;
				case 3:
					st.giveItems(600023, 1);
					break;
				case 4:
					st.giveItems(600024, 1);
					break;
				case 5:
					st.giveItems(600025, 1);
					break;
				case 6:
					st.giveItems(600031, 1);
					break;
				}
				htmltext = "HeavenNPC_q287_05.htm";
			}
			else
			{
				htmltext = "HeavenNPC_q287_05.htm";
			}
		}
		else if (event.equalsIgnoreCase("Heaven_robe_recipes"))
		{
			if (st.getQuestItemsCount(HeavenBones) >= 250)
			{
				st.takeItems(HeavenBones, 250);
				switch (Rnd.get(1, 6))
				{
				case 1:
					st.giveItems(600026, 1);
					break;
				case 2:
					st.giveItems(600027, 1);
					break;
				case 3:
					st.giveItems(600028, 1);
					break;
				case 4:
					st.giveItems(600029, 1);
					break;
				case 5:
					st.giveItems(600030, 1);
					break;
				case 6:
					st.giveItems(600031, 1);
					break;
				}
				htmltext = "HeavenNPC_q287_05.htm";
			}
			else
			{
				htmltext = "HeavenNPC_q287_05.htm";
			}
		}
		else if (event.equalsIgnoreCase("Heaven_heavy_pieces"))
		{
			if (st.getQuestItemsCount(HeavenBones) >= 50)
			{
				st.takeItems(HeavenBones, 50);
				switch (Rnd.get(1, 7))
				{
				case 1:
					st.giveItems(700016, 5);
					break;
				case 2:
					st.giveItems(700017, 5);
					break;
				case 3:
					st.giveItems(700018, 5);
					break;
				case 4:
					st.giveItems(700019, 5);
					break;
				case 5:
					st.giveItems(700020, 5);
					break;
				case 6:
					st.giveItems(700031, 5);
					break;
				case 7:
					st.giveItems(700032, 5);
					break;
				}
				htmltext = "HeavenNPC_q287_05.htm";
			}
			else
			{
				htmltext = "HeavenNPC_q287_05.htm";
			}
		}
		else if (event.equalsIgnoreCase("Heaven_light_pieces"))
		{
			if (st.getQuestItemsCount(HeavenBones) >= 50)
			{
				st.takeItems(HeavenBones, 50);
				switch (Rnd.get(1, 6))
				{
				case 1:
					st.giveItems(700021, 5);
					break;
				case 2:
					st.giveItems(700022, 5);
					break;
				case 3:
					st.giveItems(700023, 5);
					break;
				case 4:
					st.giveItems(700024, 5);
					break;
				case 5:
					st.giveItems(700025, 5);
					break;
				case 6:
					st.giveItems(700031, 5);
					break;
				}
				htmltext = "HeavenNPC_q287_05.htm";
			}
			else
			{
				htmltext = "HeavenNPC_q287_05.htm";
			}
		}
		else if (event.equalsIgnoreCase("Heaven_robe_pieces"))
		{
			if (st.getQuestItemsCount(HeavenBones) >= 50)
			{
				st.takeItems(HeavenBones, 50);
				switch (Rnd.get(1, 6))
				{
				case 1:
					st.giveItems(700026, 5);
					break;
				case 2:
					st.giveItems(700027, 5);
					break;
				case 3:
					st.giveItems(700028, 5);
					break;
				case 4:
					st.giveItems(700029, 5);
					break;
				case 5:
					st.giveItems(700030, 5);
					break;
				case 6:
					st.giveItems(700031, 5);
					break;
				}
				htmltext = "HeavenNPC_q287_05.htm";
			}
			else
			{
				htmltext = "HeavenNPC_q287_05.htm";
			}
		}
		
		else if (event.equalsIgnoreCase("continue"))
		{
			htmltext = "HeavenNPC_q287_05.htm";
		}
		else if (event.equalsIgnoreCase("quit"))
		{
			htmltext = "HeavenNPC_q287_05.htm";
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
		if (npcId == HeavenNPC)
		{
			if (cond == 0)
			{
				if (st.getPlayer().getLevel() >= 76)
				{
					htmltext = "HeavenNPC_q287_01.htm";
				}
				else
				{
					htmltext = "HeavenNPC_q287_00.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if (cond == 1 && st.getQuestItemsCount(HeavenBones) < 50)
			{
				htmltext = "HeavenNPC_q287_04.htm";
			}
			else if (cond == 1 && st.getQuestItemsCount(HeavenBones) >= 50)
			{
				htmltext = "HeavenNPC_q287_05.htm";
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
			if (ArrayUtils.contains(HeavenMonster, npcId) && Rnd.chance(30))
			{
				st.giveItems(HeavenBones, 1, true);
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