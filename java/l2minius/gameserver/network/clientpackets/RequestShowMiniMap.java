package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.Zone;
import l2minius.gameserver.network.serverpackets.ShowMiniMap;
import l2minius.gameserver.network.serverpackets.components.SystemMsg;
import l2minius.gameserver.scripts.Functions;

public class RequestShowMiniMap extends L2GameClientPacket
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

		// Map of Hellbound
		if (activeChar.isActionBlocked(Zone.BLOCKED_ACTION_MINIMAP) || (activeChar.isInZone("[Hellbound_territory]") && Functions.getItemCount(activeChar, 9994) == 0))
		{
			activeChar.sendPacket(SystemMsg.THIS_IS_AN_AREA_WHERE_YOU_CANNOT_USE_THE_MINI_MAP);
			return;
		}

		this.sendPacket(new ShowMiniMap(activeChar, 0));
	}
}