package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.Config;
import l2minius.gameserver.data.xml.holder.PetitionGroupHolder;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.petition.PetitionMainGroup;
import l2minius.gameserver.network.serverpackets.ExResponseShowStepTwo;

/**
 * @author VISTALL
 */
public class RequestExShowStepTwo extends L2GameClientPacket
{
	private int _petitionGroupId;

	@Override
	protected void readImpl()
	{
		this._petitionGroupId = this.readC();
	}

	@Override
	protected void runImpl()
	{
		Player player = this.getClient().getActiveChar();
		if (player == null || !Config.EX_NEW_PETITION_SYSTEM)
		{
			return;
		}

		PetitionMainGroup group = PetitionGroupHolder.getInstance().getPetitionGroup(this._petitionGroupId);
		if (group == null)
		{
			return;
		}

		player.setPetitionGroup(group);
		player.sendPacket(new ExResponseShowStepTwo(player, group));
	}
}