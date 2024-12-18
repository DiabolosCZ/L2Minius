package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.data.BoatHolder;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.entity.boat.Boat;
import l2minius.gameserver.utils.Location;

public class RequestExMoveToLocationInAirShip extends L2GameClientPacket
{
	private Location _pos = new Location();
	private Location _originPos = new Location();
	private int _boatObjectId;

	@Override
	protected void readImpl()
	{
		this._boatObjectId = this.readD();
		this._pos.x = this.readD();
		this._pos.y = this.readD();
		this._pos.z = this.readD();
		this._originPos.x = this.readD();
		this._originPos.y = this.readD();
		this._originPos.z = this.readD();
	}

	@Override
	protected void runImpl()
	{
		Player player = this.getClient().getActiveChar();
		if (player == null)
		{
			return;
		}

		Boat boat = BoatHolder.getInstance().getBoat(this._boatObjectId);
		if ((boat == null) || player.isClanAirShipDriver())
		{
			player.sendActionFailed();
			return;
		}

		boat.moveInBoat(player, this._originPos, this._pos);
	}
}