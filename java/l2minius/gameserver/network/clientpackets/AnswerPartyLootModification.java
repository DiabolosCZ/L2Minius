package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.model.Party;
import l2minius.gameserver.model.Player;

public class AnswerPartyLootModification extends L2GameClientPacket
{
	public int _answer;

	@Override
	protected void readImpl()
	{
		this._answer = this.readD();
	}

	@Override
	protected void runImpl()
	{
		Player activeChar = this.getClient().getActiveChar();
		if (activeChar == null)
		{
			return;
		}

		Party party = activeChar.getParty();
		if (party != null)
		{
			party.answerLootChangeRequest(activeChar, this._answer == 1);
		}
	}
}
