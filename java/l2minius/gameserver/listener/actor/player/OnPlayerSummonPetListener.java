package l2minius.gameserver.listener.actor.player;

import l2minius.gameserver.listener.PlayerListener;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.Summon;

public interface OnPlayerSummonPetListener extends PlayerListener
{
	void onSummonPet(Player p0, Summon p1);
}
