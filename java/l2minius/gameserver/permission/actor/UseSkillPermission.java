package l2minius.gameserver.permission.actor;

import l2minius.gameserver.model.Creature;
import l2minius.gameserver.model.Skill;
import l2minius.gameserver.permission.CharPermission;

public interface UseSkillPermission extends CharPermission
{
	boolean canUseSkill(Creature p0, Creature p1, Skill p2);

	void sendPermissionDeniedError(Creature p0, Creature p1, Skill p2);
}
