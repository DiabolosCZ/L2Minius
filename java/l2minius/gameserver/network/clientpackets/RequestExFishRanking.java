package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.Config;
import l2minius.gameserver.instancemanager.games.FishingChampionShipManager;
import l2minius.gameserver.model.Player;

/**
 * @author n0nam3
 * @date 08/08/2010 15:53
 */
public class RequestExFishRanking extends L2GameClientPacket
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
		if (Config.ALT_FISH_CHAMPIONSHIP_ENABLED)
		{
			FishingChampionShipManager.getInstance().showMidResult(this.getClient().getActiveChar());
		}
	}
}