package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.model.Creature;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.Skill;
import l2minius.gameserver.model.items.attachment.FlagItemAttachment;
import l2minius.gameserver.network.serverpackets.ActionFail;
import l2minius.gameserver.tables.SkillTable;

public class RequestMagicSkillUse extends L2GameClientPacket
{
	private Integer _magicId;
	private boolean _ctrlPressed;
	private boolean _shiftPressed;

	@Override
	protected void readImpl()
	{
		this._magicId = this.readD();
		this._ctrlPressed = this.readD() != 0;
		this._shiftPressed = this.readC() != 0;
	}

	@Override
	protected void runImpl()
	{
		Player activeChar = this.getClient().getActiveChar();

		if (activeChar == null)
		{
			this.getClient().sendPacket(ActionFail.STATIC);
			return;
		}

		activeChar.setActive();

		if (activeChar.isOutOfControl())
		{
			activeChar.setMacroSkill(null);
			activeChar.sendActionFailed();
			return;
		}

		if (activeChar.getMacroSkill() != null)
		{
			this._magicId = Integer.valueOf(activeChar.getMacroSkill().getId());
		}
		Skill skill = SkillTable.getInstance().getInfo(this._magicId.intValue(), activeChar.getSkillLevel(this._magicId));

		if (activeChar.isPendingOlyEnd())
		{
			if ((skill != null) && (skill.isOffensive()))
			{
				activeChar.setMacroSkill(null);
				activeChar.sendActionFailed();
				return;
			}
		}
		if (skill != null)
		{
			if ((!skill.isActive()) && (!skill.isToggle()))
			{
				activeChar.setMacroSkill(null);
				return;
			}

			FlagItemAttachment attachment = activeChar.getActiveWeaponFlagAttachment();
			if ((attachment != null) && (!attachment.canCast(activeChar, skill)))
			{
				activeChar.setMacroSkill(null);
				activeChar.sendActionFailed();
				return;
			}

			if ((activeChar.getTransformation() != 0) && (!activeChar.getAllSkills().contains(skill)))
			{
				activeChar.setMacroSkill(null);
				return;
			}

			if ((skill.isToggle()) && (activeChar.getEffectList().getEffectsBySkill(skill) != null))
			{
				activeChar.setMacroSkill(null);
				activeChar.getEffectList().stopEffect(skill.getId());
				activeChar.sendActionFailed();
				return;
			}

			Creature target = skill.getAimingTarget(activeChar, activeChar.getTarget());

//			if ((target == null) || target.isDead())
//			{
//				activeChar.sendPacket(SystemMsg.INVALID_TARGET);
//				return;
//			}

			activeChar.setGroundSkillLoc(null);

			if (activeChar.getMacroSkill() != null)
			{
				if (skill.getReuseDelay(activeChar) < 9000L)
				{
					activeChar.setReuseDelay(skill.getReuseDelay(activeChar) - 3000L);
					activeChar.setMacroSkill(null);
				}
			}
//			if (!activeChar.isCastingNow() && !skill.isToggle() && skill.isOffensive())
//			{
//				activeChar.broadcastPacket(new MoveToPawn(activeChar, target, (int) activeChar.getDistance(target)));
//			}
			activeChar.getAI().Cast(skill, target, this._ctrlPressed, this._shiftPressed);
		}
		else
		{
			activeChar.setMacroSkill(null);
			activeChar.sendActionFailed();
		}
	}
}