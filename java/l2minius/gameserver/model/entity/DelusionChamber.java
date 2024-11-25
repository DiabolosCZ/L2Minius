package l2minius.gameserver.model.entity;

import java.util.concurrent.Future;

import l2minius.commons.threading.RunnableImpl;
import l2minius.gameserver.ThreadPoolManager;
import l2minius.gameserver.data.xml.holder.InstantZoneHolder;
import l2minius.gameserver.instancemanager.DimensionalRiftManager;
import l2minius.gameserver.instancemanager.ReflectionManager;
import l2minius.gameserver.model.Party;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.instances.NpcInstance;
import l2minius.gameserver.templates.InstantZone;
import l2minius.gameserver.utils.Location;

public class DelusionChamber extends DimensionalRift
{
	private Future<?> killRiftTask;

	public DelusionChamber(Party party, int type, int room)
	{
		super(party, type, room);
	}

	@Override
	public synchronized void createNewKillRiftTimer()
	{
		if (killRiftTask != null)
		{
			killRiftTask.cancel(false);
			killRiftTask = null;
		}

		killRiftTask = ThreadPoolManager.getInstance().schedule(new RunnableImpl()
		{
			@Override
			public void runImpl() throws Exception
			{
				if (getParty() != null && !getParty().getMembers().isEmpty())
				{
					for (Player p : getParty().getMembers())
					{
						if (p.getReflection() == DelusionChamber.this)
						{
							String var = p.getVar("backCoords");
							if (var == null || var.equals(""))
							{
								continue;
							}
							p.teleToLocation(Location.parseLoc(var), ReflectionManager.DEFAULT);
							p.unsetVar("backCoords");
						}
					}
				}
				collapse();
			}
		}, 100L);
	}

	@Override
	public void partyMemberExited(Player player)
	{
		if (getPlayersInside(false) < 2 || getPlayersInside(true) == 0)
		{
			createNewKillRiftTimer();
			return;
		}
	}

	@Override
	public void manualExitRift(Player player, NpcInstance npc)
	{
		if (!player.isInParty() || player.getParty().getReflection() != this)
		{
			return;
		}

		if (!player.getParty().isLeader(player))
		{
			DimensionalRiftManager.getInstance().showHtmlFile(player, "rift/NotPartyLeader.htm", npc);
			return;
		}

		createNewKillRiftTimer();
	}

	@Override
	public String getName()
	{
		InstantZone iz = InstantZoneHolder.getInstance().getInstantZone(_roomType + 120);
		return iz.getName();
	}

	@Override
	protected int getManagerId()
	{
		return 32664;
	}
}