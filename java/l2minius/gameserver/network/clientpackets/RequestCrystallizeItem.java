package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.Skill;
import l2minius.gameserver.model.entity.CCPHelpers.itemLogs.ItemActionType;
import l2minius.gameserver.model.entity.CCPHelpers.itemLogs.ItemLogHandler;
import l2minius.gameserver.model.items.ItemInstance;
import l2minius.gameserver.network.serverpackets.components.SystemMsg;
import l2minius.gameserver.templates.item.ItemTemplate;
import l2minius.gameserver.utils.ItemFunctions;

public class RequestCrystallizeItem extends L2GameClientPacket
{
	// Format: cdd

	private int _objectId;
	@SuppressWarnings("unused")
	private long unk;

	@Override
	protected void readImpl()
	{
		this._objectId = this.readD();
		this.unk = this.readQ(); // FIXME: count??
	}

	@Override
	protected void runImpl()
	{
		Player activeChar = this.getClient().getActiveChar();

		if (activeChar == null)
		{
			return;
		}

		if (activeChar.isActionsDisabled())
		{
			activeChar.sendActionFailed();
			return;
		}

		if (activeChar.isInStoreMode())
		{
			activeChar.sendPacket(SystemMsg.WHILE_OPERATING_A_PRIVATE_STORE_OR_WORKSHOP_YOU_CANNOT_DISCARD_DESTROY_OR_TRADE_AN_ITEM);
			return;
		}

		if (activeChar.isInTrade())
		{
			activeChar.sendActionFailed();
			return;
		}

		ItemInstance item = activeChar.getInventory().getItemByObjectId(this._objectId);
		if (item == null)
		{
			activeChar.sendActionFailed();
			return;
		}

		if (item.isHeroWeapon())
		{
			activeChar.sendPacket(SystemMsg.HERO_WEAPONS_CANNOT_BE_DESTROYED);
			return;
		}

		if (!item.canBeCrystallized(activeChar))
		{
			activeChar.sendActionFailed();
			return;
		}

		if (activeChar.isInStoreMode())
		{
			activeChar.sendPacket(SystemMsg.WHILE_OPERATING_A_PRIVATE_STORE_OR_WORKSHOP_YOU_CANNOT_DISCARD_DESTROY_OR_TRADE_AN_ITEM);
			return;
		}

		if (activeChar.isFishing())
		{
			activeChar.sendPacket(SystemMsg.YOU_CANNOT_DO_THAT_WHILE_FISHING);
			return;
		}

		if (activeChar.isInTrade())
		{
			activeChar.sendActionFailed();
			return;
		}

		int crystalAmount = item.getTemplate().getCrystalCount();
		int crystalId = item.getTemplate().getCrystalType().cry;

		// can player crystallize?
		int level = activeChar.getSkillLevel(Skill.SKILL_CRYSTALLIZE);
		if (level < 1 || crystalId - ItemTemplate.CRYSTAL_D + 1 > level)
		{
			activeChar.sendPacket(SystemMsg.CANNOT_CRYSTALLIZE_CRYSTALLIZATION_SKILL_LEVEL_TOO_LOW);
			activeChar.sendActionFailed();
			return;
		}

		if (!activeChar.getInventory().destroyItemByObjectId(this._objectId, 1L, "Crystalize"))
		{
			activeChar.sendActionFailed();
			return;
		}

		activeChar.sendPacket(SystemMsg.THE_ITEM_HAS_BEEN_SUCCESSFULLY_CRYSTALLIZED);
		ItemFunctions.addItem(activeChar, crystalId, crystalAmount, true, "Crystalize");
		activeChar.sendChanges();

		ItemLogHandler.getInstance().addLog(activeChar, item, 1L, ItemActionType.CRYSTALIZED);
	}
}