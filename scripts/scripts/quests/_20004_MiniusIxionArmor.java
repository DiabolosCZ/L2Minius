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

public class _20004_MiniusIxionArmor extends Quest implements ScriptFile
{
	private static final int IxionNPC = 90104;
	private static final int[] IxionMonster =
	{
		22828,
		22827
		
	};
	private static final int IxionBones = 91103;

	public _20004_MiniusIxionArmor()
	{
		super(true);
		addStartNpc(IxionNPC);
		addKillId(IxionMonster);
		addQuestItem(IxionBones);
	}

	@Override
	public String onEvent(String event, QuestState st, NpcInstance npc)
	{
		String htmltext = event;
		if (event.equalsIgnoreCase("IxionNPC_q287_03.htm"))
		{
			st.setState(STARTED);
			st.setCond(1);
			st.playSound(SOUND_ACCEPT);
		}
		else if (event.equalsIgnoreCase("Ixion_heavy_recipes"))
		{
			if (st.getQuestItemsCount(IxionBones) >= 500)
			{
				st.takeItems(IxionBones, 500);
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
				htmltext = "IxionNPC_q287_05.htm";
			}
			else
			{
				htmltext = "IxionNPC_q287_05.htm";
			}
		}
		else if (event.equalsIgnoreCase("Ixion_light_recipes"))
		{
			if (st.getQuestItemsCount(IxionBones) >= 500)
			{
				st.takeItems(IxionBones, 500);
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
				htmltext = "IxionNPC_q287_05.htm";
			}
			else
			{
				htmltext = "IxionNPC_q287_05.htm";
			}
		}
		else if (event.equalsIgnoreCase("Ixion_robe_recipes"))
		{
			if (st.getQuestItemsCount(IxionBones) >= 500)
			{
				st.takeItems(IxionBones, 500);
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
				htmltext = "IxionNPC_q287_05.htm";
			}
			else
			{
				htmltext = "IxionNPC_q287_05.htm";
			}
		}
		else if (event.equalsIgnoreCase("Ixion_heavy_pieces"))
		{
			if (st.getQuestItemsCount(IxionBones) >= 100)
			{
				st.takeItems(IxionBones, 100);
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
				htmltext = "IxionNPC_q287_05.htm";
			}
			else
			{
				htmltext = "IxionNPC_q287_05.htm";
			}
		}
		else if (event.equalsIgnoreCase("Ixion_light_pieces"))
		{
			if (st.getQuestItemsCount(IxionBones) >= 100)
			{
				st.takeItems(IxionBones, 100);
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
				htmltext = "IxionNPC_q287_05.htm";
			}
			else
			{
				htmltext = "IxionNPC_q287_05.htm";
			}
		}
		else if (event.equalsIgnoreCase("Ixion_robe_pieces"))
		{
			if (st.getQuestItemsCount(IxionBones) >= 100)
			{
				st.takeItems(IxionBones, 100);
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
				htmltext = "IxionNPC_q287_05.htm";
			}
			else
			{
				htmltext = "IxionNPC_q287_05.htm";
			}
		}
		
		else if (event.equalsIgnoreCase("continue"))
		{
			htmltext = "IxionNPC_q287_05.htm";
		}
		else if (event.equalsIgnoreCase("quit"))
		{
			htmltext = "IxionNPC_q287_05.htm";
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
		if (npcId == IxionNPC)
		{
			if (cond == 0)
			{
				if (st.getPlayer().getLevel() >= 82)
				{
					htmltext = "IxionNPC_q287_01.htm";
				}
				else
				{
					htmltext = "IxionNPC_q287_00.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if (cond == 1 && st.getQuestItemsCount(IxionBones) < 100)
			{
				htmltext = "IxionNPC_q287_04.htm";
			}
			else if (cond == 1 && st.getQuestItemsCount(IxionBones) >= 100)
			{
				htmltext = "IxionNPC_q287_05.htm";
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
			if (ArrayUtils.contains(IxionMonster, npcId) && Rnd.chance(60))
			{
				st.giveItems(IxionBones, 1, true);
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