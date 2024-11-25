package l2minius.gameserver.network.serverpackets.components;

import l2minius.gameserver.model.Player;
import l2minius.gameserver.network.serverpackets.L2GameServerPacket;

public interface IStaticPacket
{
	L2GameServerPacket packet(Player player);
}
