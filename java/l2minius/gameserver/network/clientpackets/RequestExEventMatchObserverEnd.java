package l2minius.gameserver.network.clientpackets;

public class RequestExEventMatchObserverEnd extends L2GameClientPacket
{
	private int unk, unk2;

	/**
	 * format: dd
	 */
	@Override
	protected void readImpl()
	{
		this.unk = this.readD();
		this.unk2 = this.readD();
	}

	@Override
	protected void runImpl()
	{
		// TODO not implemented
	}

	// Synerge - This packet can be used while the character is blocked
	@Override
	public boolean canBeUsedWhileBlocked()
	{
		return true;
	}
}