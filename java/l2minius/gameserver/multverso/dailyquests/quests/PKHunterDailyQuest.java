/*
 * Copyright (C) 2004-2013 L2J Server
 * This file is part of L2J Server.
 * L2J Server is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * L2J Server is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package l2minius.gameserver.multverso.dailyquests.quests;

import l2minius.gameserver.Config;
import l2minius.gameserver.listener.actor.OnDeathListener;
import l2minius.gameserver.model.Creature;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.actor.listener.CharListenerList;
import l2minius.gameserver.model.quest.QuestState;
import l2minius.gameserver.multverso.dailyquests.AbstractDailyQuest;
import l2minius.gameserver.utils.HtmlUtils;

/**
 * @author UnAfraid
 */
public class PKHunterDailyQuest extends AbstractDailyQuest
{
	public PKHunterDailyQuest()
	{
		CharListenerList.addGlobal(new OnDeathList());
	}

	@Override
	public int getQuestIntId()
	{
		// Random quest id
		return 35004;
	}

	@Override
	protected int writeHeight(Player player, int index)
	{
		switch (index)
		{
		case 1:
		{
			return 480;
		}
		case 2:
		{
			return 680;
		}
		}
		return 480;
	}

	@Override
	protected String writeQuestInfo(Player player)
	{
		final StringBuilder sb = new StringBuilder();
		sb.append("Você deve caçar aleatoriamente entre " + getMinKillsRequired() + " os " + getMaxKillsRequired() + " jogadores com karma para completar a missão.<br1>");
		sb.append("Cada vez que você aceitar a missão, a quantidade de jogadores será selecionada aleatoriamente.<br1>");
		sb.append("As regras são simples: <br1>");
		sb.append("1. Não Parte/Canal/Clan/Membro da Aliança!<br1>");
		sb.append("2. Não participante de evento ou olimpíada em missão!<br1>");
		sb.append("3. Players com karma não contam!<br1>");
		sb.append("4. Não matar mesmo player no último  " + (Config.ANTIFEED_INTERVAL / 60000) + " mins!<br1>");
		sb.append("5. Não matar na Arena!<br1>");
		sb.append("6. O alvo deve ser pelo menos  " + getMinLevel() + " ou acima a ser contado!<br1>");
		sb.append("<font color=\"LEVEL\">Zariche e Akamanah dá 2 pontos!!</font><br1>");
		return sb.toString();
	}

	@Override
	protected String writeQuestProgress(Player player)
	{
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return "Você deve fazer a missão para verificar seu progresso!";
		}

		final StringBuilder sb = new StringBuilder();
		sb.append("Progresso:<br>");
		sb.append(HtmlUtils.getWeightGauge(450, st.getInt("KILLS"), st.getInt("KILLS_NEEDED"), false));
		sb.append("<br>");

		sb.append("You must hunt down from " + st.getInt("KILLS_NEEDED") + " players with karma in order to complete the quest.<br1>");
		sb.append("Each time you accept the quest the amount of players will be randomly selected.<br1>");
		sb.append("As regras são simples: <br1>");
		sb.append("1. Não Parte/Canal/Clan/Membro da Aliança!<br1>");
		sb.append("2. Não participante de evento ou olimpíada em missão!<br1>");
		sb.append("3. Players com karma não contam!<br1>");
		sb.append("4. Não matar mesmo player no último " + (Config.ANTIFEED_INTERVAL / 60000) + " mins!<br1>");
		sb.append("5. Não matar na Arena!<br1>");
		sb.append("6. O alvo deve ser pelo menos  " + getMinLevel() + " ou acima a ser contado!<br1>");
		sb.append("<font color=\"LEVEL\">Zariche e Akamanah dá 2 pontos!!</font><br1>");
		return sb.toString();
	}

	@Override
	public void onQuestStart(QuestState st)
	{
		st.set("KILLS", "0");
		st.set("KILLS_NEEDED", getRandomKillsRequired());
	}

	private class OnDeathList implements OnDeathListener
	{
		@Override
		public void onDeath(Creature actor, Creature killer)
		{
			if (!actor.isPlayer())
			{
				return;
			}

			final Player player = actor.getPlayer();
			if (!validateKill(player, killer != null ? killer.getPlayer() : null) || (player.getKarma() < 1))
			{
				return;
			}

			final Player attacker = killer != null ? killer.getPlayer() : null;
			final Player attackerMember = getRandomPartyMember(attacker);
			final QuestState st = attackerMember != null ? attackerMember.getQuestState(getName()) : null;
			if ((attackerMember == null) || (st == null) || st.isCompleted())
			{
				return;
			}
			st.set("KILLS", st.getInt("KILLS") + (player.isCursedWeaponEquipped() ? 2 : 1));
			if (st.getInt("KILLS") >= st.getInt("KILLS_NEEDED"))
			{
				st.setState(COMPLETED);
				st.setRestartTime();
				onQuestFinish(st);
			}
			else
			{
				showScreenMessage(attackerMember, "progress " + st.get("KILLS") + "/" + st.get("KILLS_NEEDED") + " completed!", 5000);
			}
		}
	}
}
