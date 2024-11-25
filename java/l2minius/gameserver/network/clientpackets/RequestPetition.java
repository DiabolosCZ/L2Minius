package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.Config;
import l2minius.gameserver.instancemanager.PetitionManager;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.petition.PetitionMainGroup;
import l2minius.gameserver.model.petition.PetitionSubGroup;

public final class RequestPetition extends L2GameClientPacket
{
	private String _content;
	private int _type;

	@Override
	protected void readImpl()
	{
		this._content = this.readS();
		this._type = this.readD();
	}

	@Override
	protected void runImpl()
	{
		Player player = this.getClient().getActiveChar();
		if (player == null)
		{
			return;
		}

		if (Config.EX_NEW_PETITION_SYSTEM)
		{
			PetitionMainGroup group = player.getPetitionGroup();
			if (group == null)
			{
				return;
			}

			PetitionSubGroup subGroup = group.getSubGroup(this._type);
			if (subGroup == null)
			{
				return;
			}

			subGroup.getHandler().handle(player, this._type, this._content);
		}
		else
		{
			PetitionManager.getInstance().handle(player, this._type, this._content);
		}
	}
}
