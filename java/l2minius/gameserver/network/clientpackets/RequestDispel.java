package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.Config;
import l2minius.gameserver.model.Creature;
import l2minius.gameserver.model.Effect;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.Skill.SkillType;
import l2minius.gameserver.skills.EffectType;

public class RequestDispel extends L2GameClientPacket
{
	private int _objectId, _id, _level;

	@Override
	protected void readImpl()
	{
		this._objectId = this.readD();
		this._id = this.readD();
		this._level = this.readD();
	}

	@Override
	protected void runImpl()
	{
		Player activeChar = this.getClient().getActiveChar();
		if ((activeChar == null) || ((activeChar.getObjectId() != this._objectId) && (activeChar.getPet() == null)))
		{
			return;
		}

		Creature target = activeChar;
		if (activeChar.getObjectId() != this._objectId)
		{
			target = activeChar.getPet();
		}

		for (Effect e : target.getEffectList().getAllEffects())
		{
			if ((e.getDisplayId() == this._id) && (e.getDisplayLevel() == this._level))
			{
				if (!e.isOffensive() && (!e.getSkill().isMusic() || Config.ALT_DISPEL_MUSIC) && e.getSkill().isSelfDispellable() && (e.getSkill().getSkillType() != SkillType.TRANSFORMATION) && (e.getTemplate().getEffectType() != EffectType.Hourglass))
				{
					e.exit();
				}
				else
				{
					return;
				}
			}
		}
	}
}