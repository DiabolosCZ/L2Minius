package l2minius.gameserver.utils;

import l2minius.commons.util.Rnd;
import l2minius.gameserver.ThreadPoolManager;
import l2minius.gameserver.data.xml.holder.NpcHolder;
import l2minius.gameserver.instancemanager.ReflectionManager;
import l2minius.gameserver.model.GameObjectTasks;
import l2minius.gameserver.model.entity.Reflection;
import l2minius.gameserver.model.instances.NpcInstance;
import l2minius.gameserver.templates.npc.NpcTemplate;

public class NpcUtils
{
	public static NpcInstance spawnSingle(int npcId, int x, int y, int z)
	{
		return spawnSingle(npcId, new Location(x, y, z, -1), ReflectionManager.DEFAULT, 0);
	}

	public static NpcInstance spawnSingle(int npcId, int x, int y, int z, long despawnTime)
	{
		return spawnSingle(npcId, new Location(x, y, z, -1), ReflectionManager.DEFAULT, despawnTime);
	}

	public static NpcInstance spawnSingle(int npcId, int x, int y, int z, int h, long despawnTime)
	{
		return spawnSingle(npcId, new Location(x, y, z, h), ReflectionManager.DEFAULT, despawnTime);
	}

	public static NpcInstance spawnSingle(int npcId, Location loc)
	{
		return spawnSingle(npcId, loc, ReflectionManager.DEFAULT, 0);
	}

	public static NpcInstance spawnSingle(int npcId, Location loc, long despawnTime)
	{
		return spawnSingle(npcId, loc, ReflectionManager.DEFAULT, despawnTime);
	}

	public static NpcInstance spawnSingle(int npcId, Location loc, Reflection reflection)
	{
		return spawnSingle(npcId, loc, reflection, 0);
	}

	public static NpcInstance spawnSingle(int npcId, Location loc, Reflection reflection, long despawnTime)
	{
		NpcTemplate template = NpcHolder.getInstance().getTemplate(npcId);
		if (template == null)
		{
			throw new NullPointerException("Npc template id : " + npcId + " not found!");
		}

		NpcInstance npc = template.getNewInstance();
		npc.setHeading(loc.h < 0 ? Rnd.get(0xFFFF) : loc.h);
		npc.setSpawnedLoc(loc);
		npc.setReflection(reflection);
		npc.setCurrentHpMp(npc.getMaxHp(), npc.getMaxMp(), true);

		npc.spawnMe(npc.getSpawnedLoc());
		if (despawnTime > 0)
		{
			ThreadPoolManager.getInstance().schedule(new GameObjectTasks.DeleteTask(npc), despawnTime);
		}
		return npc;
	}
}
