package l2minius.gameserver.network.serverpackets;

public class JoinParty extends L2GameServerPacket
{
	public static final L2GameServerPacket SUCCESS = new JoinParty(1);
	public static final L2GameServerPacket FAIL = new JoinParty(0);

	private int _response;

	public JoinParty(int response)
	{
		this._response = response;
	}

	@Override
	protected final void writeImpl()
	{
		this.writeC(0x3A);
		this.writeD(this._response);
	}
}