package l2minius.gameserver.model.instances;

import l2minius.commons.lang.reference.HardReference;
import l2minius.gameserver.idfactory.IdFactory;
import l2minius.gameserver.model.GameObject;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.reference.L2Reference;
import l2minius.gameserver.network.serverpackets.MyTargetSelected;

public class ControlKeyInstance extends GameObject
{
	protected HardReference<ControlKeyInstance> reference;

	public ControlKeyInstance()
	{
		super(IdFactory.getInstance().getNextId());
		reference = new L2Reference<ControlKeyInstance>(this);
	}

	@Override
	public HardReference<ControlKeyInstance> getRef()
	{
		return reference;
	}

	@Override
	public void onAction(Player player, boolean shift)
	{
		if (player.getTarget() != this)
		{
			player.setTarget(this);
			player.sendPacket(new MyTargetSelected(getObjectId(), 0));
			return;
		}

		player.sendActionFailed();
	}
}
