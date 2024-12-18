package services;

import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.instances.NpcInstance;
import l2minius.gameserver.scripts.Functions;
import l2minius.gameserver.utils.Location;

public class TeleToMDT extends Functions
{
	public void toMDT()
	{
		Player player = getSelf();
		NpcInstance npc = getNpc();
		if (player == null || npc == null || !NpcInstance.canBypassCheck(player, npc))
		{
			return;
		}

		player.setVar("backCoords", player.getLoc().toXYZString(), -1);
		player.teleToLocation(12661, 181687, -3560);
	}

	public void fromMDT()
	{
		Player player = getSelf();
		NpcInstance npc = getNpc();
		if (player == null || npc == null || !NpcInstance.canBypassCheck(player, npc))
		{
			return;
		}

		String var = player.getVar("backCoords");
		if (var == null || var.equals(""))
		{
			teleOut();
			return;
		}
		player.teleToLocation(Location.parseLoc(var));
	}

	public void teleOut()
	{
		Player player = getSelf();
		NpcInstance npc = getNpc();
		if (player == null || npc == null)
		{
			return;
		}
		player.teleToLocation(12902, 181011, -3563);
		show("I don't know from where you came here, but I can teleport you the another border side.", player, npc);
	}
}