package l2minius.gameserver.network.serverpackets;

import java.util.Collection;

import l2minius.gameserver.data.xml.holder.PetitionGroupHolder;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.petition.PetitionMainGroup;
import l2minius.gameserver.utils.Language;

/**
 * @author VISTALL
 */
public class ExResponseShowStepOne extends L2GameServerPacket
{
	private Language _language;

	public ExResponseShowStepOne(Player player)
	{
		this._language = player.getLanguage();
	}

	@Override
	protected void writeImpl()
	{
		this.writeEx(0xAE);
		Collection<PetitionMainGroup> petitionGroups = PetitionGroupHolder.getInstance().getPetitionGroups();
		this.writeD(petitionGroups.size());
		for (PetitionMainGroup group : petitionGroups)
		{
			this.writeC(group.getId());
			this.writeS(group.getName(this._language));
		}
	}
}