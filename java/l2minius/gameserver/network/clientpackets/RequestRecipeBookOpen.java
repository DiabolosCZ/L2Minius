package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.model.Player;
import l2minius.gameserver.network.serverpackets.RecipeBookItemList;

public class RequestRecipeBookOpen extends L2GameClientPacket
{
	private boolean isDwarvenCraft;

	@Override
	protected void readImpl()
	{
		if (this._buf.hasRemaining())
		{
			this.isDwarvenCraft = this.readD() == 0;
		}
	}

	@Override
	protected void runImpl()
	{
		Player activeChar = this.getClient().getActiveChar();
		if (activeChar == null)
		{
			return;
		}

		this.sendPacket(new RecipeBookItemList(activeChar, this.isDwarvenCraft));
	}
}