package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.utils.Location;

//@Deprecated
public class RequestExGetOnAirShip extends L2GameClientPacket
{
	private int _shipId;
	private Location loc = new Location();

	@Override
	protected void readImpl()
	{
		this.loc.x = this.readD();
		this.loc.y = this.readD();
		this.loc.z = this.readD();
		this._shipId = this.readD();
	}

	@Override
	protected void runImpl()
	{
		/*
		 * L2Player activeChar = getClient().getActiveChar();
		 * if (activeChar == null)
		 * return;
		 * L2AirShip boat = (L2AirShip) L2VehicleManager.getInstance().getBoat(_shipId);
		 * if (boat == null)
		 * return;
		 * activeChar.stopMove();
		 * activeChar.setBoat(boat);
		 * activeChar.setInBoatPosition(loc);
		 * activeChar.setLoc(boat.getLoc());
		 * activeChar.broadcastPacket(new ExGetOnAirShip(activeChar, boat, loc));
		 */
	}
}