package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.cache.ItemInfoCache;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.items.ItemInfo;
import l2minius.gameserver.network.serverpackets.ActionFail;
import l2minius.gameserver.network.serverpackets.ExRpItemLink;

public class RequestExRqItemLink extends L2GameClientPacket
{
	private int _objectId;

	@Override
	protected void readImpl()
	{
		this._objectId = this.readD();
	}

	@Override
	protected void runImpl()
	{
		ItemInfo item;
		if ((item = ItemInfoCache.getInstance().get(this._objectId)) == null)
		{
			// Nik: Support for question mark listeners. Used for party find and other shits. objectId is used as the questionMarkId. Use with caution.
			this.getClient().getActiveChar().getListeners().onQuestionMarkClicked(this._objectId);

			if (this._objectId >= 5000000 && this._objectId < 6000000)
			{
				Player player = this.getClient().getActiveChar();
				String varName = "DisabledAnnounce" + this._objectId;
				if (!player.containsQuickVar(varName))
				{
					player.addQuickVar(varName, "true");
					player.sendMessage("Announcement Disabled!");
				}
			}
			this.sendPacket(ActionFail.STATIC);
		}
		else
		{
			this.sendPacket(new ExRpItemLink(item));
		}
	}
}