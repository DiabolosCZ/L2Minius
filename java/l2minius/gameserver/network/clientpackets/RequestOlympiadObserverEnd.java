package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.model.Player;

public class RequestOlympiadObserverEnd extends L2GameClientPacket
{
	@Override
	protected void readImpl()
	{
	}

	@Override
	protected void runImpl()
	{
		Player activeChar = this.getClient().getActiveChar();
		if (activeChar == null)
		{
			return;
		}
		if (activeChar.getObserverMode() == Player.OBSERVER_STARTED)
		{
			if (activeChar.getOlympiadObserveGame() != null)
			{
				activeChar.leaveOlympiadObserverMode(true);
			}
		}
	}

	// Synerge - This packet can be used while the character is blocked
	@Override
	public boolean canBeUsedWhileBlocked()
	{
		return true;
	}
}