package l2minius.loginserver.clientpackets;

import l2minius.loginserver.L2LoginClient;
import l2minius.loginserver.SessionKey;
import l2minius.loginserver.serverpackets.LoginFail.LoginFailReason;
import l2minius.loginserver.utils.ProxyWaitingList;

/**
 * Format: ddc
 * d: fist part of session id
 * d: second part of session id
 * c: ?
 */
public class RequestServerList extends L2LoginClientPacket
{
	private int _loginOkID1;
	private int _loginOkID2;

	@Override
	protected void readImpl()
	{
		_loginOkID1 = readD();
		_loginOkID2 = readD();
	}

	@Override
	protected void runImpl()
	{
		final L2LoginClient client = getClient();
		final SessionKey skey = client.getSessionKey();
		if (skey == null || !skey.checkLoginPair(_loginOkID1, _loginOkID2))
		{
			client.close(LoginFailReason.REASON_ACCESS_FAILED);
			return;
		}

		ProxyWaitingList.getInstance().requestProxies(client);
	}
}