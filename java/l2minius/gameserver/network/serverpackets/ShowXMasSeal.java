package l2minius.gameserver.network.serverpackets;

public class ShowXMasSeal extends L2GameServerPacket
{
	private int _item;

	public ShowXMasSeal(int item)
	{
		this._item = item;
	}

	@Override
	protected void writeImpl()
	{
		this.writeC(0xf8);
		this.writeD(this._item);
	}
}