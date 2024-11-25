package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.handler.usercommands.IUserCommandHandler;
import l2minius.gameserver.handler.usercommands.UserCommandHandler;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.network.serverpackets.components.CustomMessage;

/**
 * format:  cd
 * Example package team /loc:
 * AA 00 00 00 00
 */
public class BypassUserCmd extends L2GameClientPacket
{
	private int _command;

	@Override
	protected void readImpl()
	{
		this._command = this.readD();
	}

	@Override
	protected void runImpl()
	{
		Player activeChar = this.getClient().getActiveChar();
		if (activeChar == null)
		{
			return;
		}

		IUserCommandHandler handler = UserCommandHandler.getInstance().getUserCommandHandler(this._command);

		if (handler == null)
		{
			activeChar.sendMessage(new CustomMessage("common.S1NotImplemented", activeChar).addString(String.valueOf(this._command)));
		}
		else
		{
			handler.useUserCommand(this._command, activeChar);
		}
	}
}