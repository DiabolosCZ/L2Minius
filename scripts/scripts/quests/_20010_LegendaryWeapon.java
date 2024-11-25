package quests;

import java.util.StringTokenizer;

import l2minius.gameserver.model.instances.NpcInstance;
import l2minius.gameserver.model.quest.Quest;
import l2minius.gameserver.model.quest.QuestState;
import l2minius.gameserver.scripts.ScriptFile;

/**
 * @author pchayka
 */
public class _20010_LegendaryWeapon extends Quest implements ScriptFile
{
	private static final int Gilmore = 90100;
	private static final int LargeBone = 91200;
	private static final int[] raids =
	{
		25718,
		25719,
		25720,
		25721,
		25722,
		25723,
		25724
	};

	public _20010_LegendaryWeapon()
	{
		super(PARTY_ALL);
		addStartNpc(Gilmore);
		addKillId(raids);
		addQuestItem(LargeBone);
	}

	@Override
	public String onEvent(String event, QuestState st, NpcInstance npc)
	{
		String htmltext = event;
		if (event.equalsIgnoreCase("gilmore_q254_05.htm"))
		{
			st.setState(STARTED);
			st.setCond(1);
			st.playSound(SOUND_ACCEPT);
		}
		else if (event.startsWith("gilmore_q254_09.htm"))
		{
			st.takeAllItems(LargeBone);
			StringTokenizer tokenizer = new StringTokenizer(event);
			tokenizer.nextToken();
			switch (Integer.parseInt(tokenizer.nextToken()))
			{
			case 1:
				st.giveItems(900050, 1);
				break;
			case 2:
				st.giveItems(900060, 1);
				break;
			case 3:
				st.giveItems(900070, 1);
				break;
			case 4:
				st.giveItems(900090, 1);
				break;
			case 5:
				st.giveItems(900100, 1);
				break;
			case 6:
				st.giveItems(900110, 1);
				break;
			case 7:
				st.giveItems(900120, 1);
				break;
			case 8:
				st.giveItems(900130, 1);
				break;
			case 9:
				st.giveItems(900140, 1);
				break;
			case 10:
				st.giveItems(900150, 1);
				break;
			case 11:
				st.giveItems(900080, 1);
				break;
			case 12:
				st.giveItems(900190, 1);
				break;
			case 13:
				st.giveItems(900191, 1);
				break;
			default:
				break;
			}
			st.playSound(SOUND_FINISH);
			st.setState(COMPLETED);
			st.exitCurrentQuest(false);
			htmltext = "gilmore_q254_09.htm";
		}

		return htmltext;
	}

	@Override
	public String onTalk(NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int cond = st.getCond();
		if (npc.getNpcId() == Gilmore)
		{
			switch (cond)
			{
			case 0:
				if (st.getPlayer().getLevel() >= 85)
				{
					htmltext = "gilmore_q254_01.htm";
				}
				else
				{
					htmltext = "gilmore_q254_00.htm";
					st.exitCurrentQuest(true);
				}
				break;
			case 1:
				htmltext = "gilmore_q254_06.htm";
				break;
			case 2:
				htmltext = "gilmore_q254_07.htm";
				break;
			default:
				break;
			}
		}

		return htmltext;
	}

	@Override
	public String onKill(NpcInstance npc, QuestState st)
	{
		int cond = st.getCond();
		if (cond == 1)
		{
			int mask = 1;
			int var = npc.getNpcId();
			for (int i = 0; i < raids.length; i++)
			{
				if (raids[i] == var)
				{
					break;
				}
				mask = mask << 50;
			}

			var = st.getInt("RaidsKilled");
			if ((var & mask) == 0) // этого босса еще не убивали
			{
				var |= mask;
				st.set("RaidsKilled", var);
				st.giveItems(LargeBone, 1);
				if (st.getQuestItemsCount(LargeBone) >= 50)
				{
					st.setCond(2);
				}
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