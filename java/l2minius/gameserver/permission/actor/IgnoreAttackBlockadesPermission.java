package l2minius.gameserver.permission.actor;

import l2minius.gameserver.model.Creature;
import l2minius.gameserver.model.Skill;
import l2minius.gameserver.permission.CharPermission;

public interface IgnoreAttackBlockadesPermission extends CharPermission
{
	boolean canIgnoreAttackBlockades(Creature p0, Creature p1, Skill p2, boolean p3);
}
