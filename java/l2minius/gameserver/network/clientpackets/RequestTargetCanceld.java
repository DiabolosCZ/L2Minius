package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.Skill;
import l2minius.gameserver.network.serverpackets.components.SystemMsg;

public class RequestTargetCanceld extends L2GameClientPacket
{
	private int _unselect;

	/**
	 * packet type id 0x48
	 * format:		ch
	 */
	@Override
	protected void readImpl()
	{
		this._unselect = this.readH();
	}

	@Override
	protected void runImpl()
	{
		Player activeChar = this.getClient().getActiveChar();
		if (activeChar == null)
		{
			return;
		}

		if (activeChar.isLockedTarget())
		{
			if (activeChar.isClanAirShipDriver())
			{
				activeChar.sendPacket(SystemMsg.THIS_ACTION_IS_PROHIBITED_WHILE_STEERING);
			}

			activeChar.sendActionFailed();
			return;
		}

		if ((this._unselect == 0) && activeChar.isCastingNow())
		{
			Skill skill = activeChar.getCastingSkill();
			activeChar.abortCast(skill != null && (skill.isHandler() || skill.getHitTime(activeChar) > 1000), false);
		}
		else if (activeChar.getTarget() != null)
		{
			activeChar.setTarget(null);
		}
	}
}