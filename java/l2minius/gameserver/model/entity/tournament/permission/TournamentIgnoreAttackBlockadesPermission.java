package l2minius.gameserver.model.entity.tournament.permission;

import l2minius.gameserver.model.Creature;
import l2minius.gameserver.model.Skill;
import l2minius.gameserver.permission.actor.IgnoreAttackBlockadesPermission;

public class TournamentIgnoreAttackBlockadesPermission implements IgnoreAttackBlockadesPermission
{
	@Override
	public boolean canIgnoreAttackBlockades(Creature actor, Creature target, Skill skill, boolean force)
	{
		return true;
	}
}
