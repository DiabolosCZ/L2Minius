package l2minius.gameserver.model.items.listeners;

import l2minius.gameserver.listener.inventory.OnEquipListener;
import l2minius.gameserver.model.Playable;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.Skill;
import l2minius.gameserver.model.Skill.SkillType;
import l2minius.gameserver.model.items.ItemInstance;
import l2minius.gameserver.templates.item.ItemTemplate;

public final class AccessoryListener implements OnEquipListener
{
	private static final AccessoryListener _instance = new AccessoryListener();

	public static AccessoryListener getInstance()
	{
		return _instance;
	}

	@Override
	public void onUnequip(int slot, ItemInstance item, Playable actor)
	{
		if (!item.isEquipable())
		{
			return;
		}

		Player player = (Player) actor;

		if (item.getBodyPart() == ItemTemplate.SLOT_L_BRACELET && item.getTemplate().getAttachedSkills().length > 0)
		{
			int agathionId = player.getAgathionId();
			int transformNpcId = player.getTransformationTemplate();
			for (Skill skill : item.getTemplate().getAttachedSkills())
			{
				if (agathionId > 0 && skill.getNpcId() == agathionId)
				{
					player.setAgathion(0);
				}
				if (skill.getNpcId() == transformNpcId && skill.getSkillType() == SkillType.TRANSFORMATION)
				{
					player.setTransformation(0);
				}
			}
		}

		if (item.isAccessory() || item.getTemplate().isTalisman() || item.getTemplate().isBracelet())
		{
			player.sendUserInfo(true);
			// TODO [G1ta0] отладить отображение аксессуаров
			// player.sendPacket(new ItemList(player, false));
		}
		else
		{
			player.broadcastCharInfo();
		}
	}

	@Override
	public void onEquip(int slot, ItemInstance item, Playable actor)
	{
		if (!item.isEquipable())
		{
			return;
		}

		Player player = (Player) actor;

		if (item.isAccessory() || item.getTemplate().isTalisman() || item.getTemplate().isBracelet())
		{
			player.sendUserInfo(true);
			// TODO [G1ta0] отладить отображение аксессуаров
			// player.sendPacket(new ItemList(player, false));
		}
		else
		{
			player.broadcastCharInfo();
		}
	}
}