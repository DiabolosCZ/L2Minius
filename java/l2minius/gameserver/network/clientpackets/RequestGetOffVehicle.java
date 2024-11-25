package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.data.BoatHolder;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.entity.boat.Boat;
import l2minius.gameserver.utils.Location;

public class RequestGetOffVehicle extends L2GameClientPacket
{
	// Format: cdddd
	private int _objectId;
	private Location _location = new Location();

	@Override
	protected void readImpl()
	{
		this._objectId = this.readD();
		this._location.x = this.readD();
		this._location.y = this.readD();
		this._location.z = this.readD();
	}

	@Override
	protected void runImpl()
	{
		Player player = this.getClient().getActiveChar();
		if (player == null)
		{
			return;
		}

		Boat boat = BoatHolder.getInstance().getBoat(this._objectId);
		if (boat == null || boat.isMoving)
		{
			player.sendActionFailed();
			return;
		}

		boat.oustPlayer(player, this._location, false);
	}
}