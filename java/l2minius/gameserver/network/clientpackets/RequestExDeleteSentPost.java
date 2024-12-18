package l2minius.gameserver.network.clientpackets;

import java.util.Collection;

import org.apache.commons.lang3.ArrayUtils;

import l2minius.gameserver.dao.MailDAO;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.mail.Mail;
import l2minius.gameserver.network.serverpackets.ExShowSentPostList;

/**
 * Запрос на удаление отправленных сообщений. Удалить можно только письмо без вложения. Отсылается при нажатии на "delete" в списке отправленных писем.
 * @see ExShowSentPostList
 * @see RequestExDeleteReceivedPost
 */
public class RequestExDeleteSentPost extends L2GameClientPacket
{
	private int _count;
	private int[] _list;

	/**
	 * format: dx[d]
	 */
	@Override
	protected void readImpl()
	{
		this._count = this.readD(); // количество элементов для удаления
		if (this._count * 4 > this._buf.remaining() || this._count > Short.MAX_VALUE || this._count < 1)
		{
			this._count = 0;
			return;
		}
		this._list = new int[this._count];
		for (int i = 0; i < this._count; i++)
		{
			this._list[i] = this.readD(); // уникальный номер письма
		}
	}

	@Override
	protected void runImpl()
	{
		Player activeChar = this.getClient().getActiveChar();
		if (activeChar == null || this._count == 0)
		{
			return;
		}

		Collection<Mail> mails = MailDAO.getInstance().getSentMailByOwnerId(activeChar.getObjectId());
		if (!mails.isEmpty())
		{
			for (Mail mail : mails)
			{
				if (ArrayUtils.contains(this._list, mail.getMessageId()))
				{
					if (mail.getAttachments().isEmpty())
					{
						// FIXME [G1ta0] если почта не прочитана получателем, возможно имеет смысл удалять ее совсем, на офф. сервере не удаляется.
						/*
						 * if (mail.isUnread())
						 * mail.delete();
						 * else
						 */
						MailDAO.getInstance().deleteSentMailByMailId(activeChar.getObjectId(), mail.getMessageId());
					}
				}
			}
		}

		activeChar.sendPacket(new ExShowSentPostList(activeChar));
	}
}