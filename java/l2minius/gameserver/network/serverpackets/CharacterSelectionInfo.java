package l2minius.gameserver.network.serverpackets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import l2minius.gameserver.Config;
import l2minius.gameserver.dao.CharacterDAO;
import l2minius.gameserver.data.xml.holder.CharTemplateHolder;
import l2minius.gameserver.database.DatabaseFactory;
import l2minius.gameserver.model.CharSelectInfoPackage;
import l2minius.gameserver.model.base.Experience;
import l2minius.gameserver.model.items.Inventory;
import l2minius.gameserver.templates.PlayerTemplate;
import l2minius.gameserver.templates.StatsSet;
import l2minius.gameserver.utils.AutoBan;

public class CharacterSelectionInfo extends L2GameServerPacket
{
	// d (SdSddddddddddffdQdddddddddddddddddddddddddddddddddddddddffdddchhd)
	private static final Logger _log = LoggerFactory.getLogger(CharacterSelectionInfo.class);

	private final String loginName;

	private final int sessionId;

	private final CharSelectInfoPackage[] characterPackages;

	public CharacterSelectionInfo(String loginName, int sessionId)
	{
		this.sessionId = sessionId;
		this.loginName = loginName;
		this.characterPackages = loadCharacterSelectInfo(loginName);
	}

	public CharSelectInfoPackage[] getCharInfo()
	{
		return this.characterPackages;
	}

	@Override
	protected final void writeImpl()
	{
		int size = this.characterPackages != null ? this.characterPackages.length : 0;

		this.writeC(0x09);
		this.writeD(size);
		this.writeD(0x07); // Kamael, 0x07 ?
		this.writeC(0);

		long lastAccess = -1L;
		int lastUsed = -1;
		for (int i = 0; i < size; i++)
		{
			if (lastAccess < this.characterPackages[i].getLastAccess())
			{
				lastAccess = this.characterPackages[i].getLastAccess();
				lastUsed = i;
			}
		}

		for (int i = 0; i < size; i++)
		{
			CharSelectInfoPackage charInfoPackage = this.characterPackages[i];

			this.writeS(charInfoPackage.getName());
			this.writeD(charInfoPackage.getCharId()); // ?
			this.writeS(this.loginName);
			this.writeD(this.sessionId);
			this.writeD(charInfoPackage.getClanId());
			this.writeD(0x00); // ??

			this.writeD(charInfoPackage.getSex());
			this.writeD(charInfoPackage.getRace());
			this.writeD(charInfoPackage.getBaseClassId());

			this.writeD(0x01); // active ??

			this.writeD(charInfoPackage.getX());
			this.writeD(charInfoPackage.getY());
			this.writeD(charInfoPackage.getZ());

			this.writeF(charInfoPackage.getCurrentHp());
			this.writeF(charInfoPackage.getCurrentMp());

			this.writeD(charInfoPackage.getSp());
			this.writeQ(charInfoPackage.getExp());
			int lvl = charInfoPackage.getLevel();
			this.writeF(Experience.getExpPercent(lvl, charInfoPackage.getExp()));
			this.writeD(lvl);

			this.writeD(charInfoPackage.getKarma());
			this.writeD(charInfoPackage.getPk());
			this.writeD(charInfoPackage.getPvP());

			this.writeD(0x00);
			this.writeD(0x00);
			this.writeD(0x00);
			this.writeD(0x00);
			this.writeD(0x00);
			this.writeD(0x00);
			this.writeD(0x00);

			for (int paperdollId : Inventory.PAPERDOLL_ORDER)
			{
				this.writeD(charInfoPackage.getPaperdollItemId(paperdollId));
			}

			this.writeD(charInfoPackage.getHairStyle());
			this.writeD(charInfoPackage.getHairColor());
			this.writeD(charInfoPackage.getFace());

			this.writeF(charInfoPackage.getMaxHp()); // hp max
			this.writeF(charInfoPackage.getMaxMp()); // mp max

			this.writeD(charInfoPackage.getAccessLevel() > -100 ? charInfoPackage.getDeleteTimer() : -1);
			this.writeD(charInfoPackage.getClassId());
			this.writeD(i == lastUsed ? 1 : 0);

			this.writeC(Math.min(charInfoPackage.getPaperdollEnchantEffect(Inventory.PAPERDOLL_RHAND), 127));
			this.writeD(charInfoPackage.getPaperdollAugmentationId(Inventory.PAPERDOLL_RHAND));
			int weaponId = charInfoPackage.getPaperdollItemId(Inventory.PAPERDOLL_RHAND);
			if (weaponId == 8190)
			{
				this.writeD(301);
			}
			else if (weaponId == 8689)
			{
				this.writeD(302);
			}
			else
			{
				this.writeD(0x00);
			}

			// Freya by Vistall:
			this.writeD(0x00); // npdid - 16024 Tame Tiny Baby Kookaburra A9E89C
			this.writeD(0x00); // level
			this.writeD(0x00); // ?
			this.writeD(0x00); // food? - 1200
			this.writeF(0x00); // max Hp
			this.writeF(0x00); // cur Hp

			this.writeD(charInfoPackage.getVitalityPoints());
		}
	}

	public static CharSelectInfoPackage[] loadCharacterSelectInfo(String loginName)
	{
		CharSelectInfoPackage charInfoPackage;
		List<CharSelectInfoPackage> characterList = new ArrayList<>();

		try (Connection con = DatabaseFactory.getInstance().getConnection(); PreparedStatement statement = con.prepareStatement("SELECT obj_Id, sex, char_name, x, y, z, pkkills, pvpkills, face, hairstyle, haircolor, clanid, karma, deletetime, lastAccess, accesslevel, vitality FROM characters WHERE account_name=? LIMIT 7"))
		{
			statement.setString(1, loginName);

			try (ResultSet rset = statement.executeQuery())
			{
				while (rset.next()) // fills the package
				{
					charInfoPackage = restoreChar(rset, con);
					if (charInfoPackage != null)
					{
						characterList.add(charInfoPackage);
					}
				}
			}
		}
		catch (SQLException e)
		{
			_log.error("could not restore charInfo:", e);
		}

		return characterList.toArray(new CharSelectInfoPackage[characterList.size()]);
	}

	private static StatsSet restoreClasses(int objId, Connection con)
	{
		StatsSet set = new StatsSet();

		try (PreparedStatement statement = con.prepareStatement("SELECT class_id, level, maxHp, curHp, maxMp, curMp, exp, sp, isBase, active FROM character_subclasses WHERE char_obj_id=?"))
		{
			statement.setInt(1, objId);

			try (ResultSet rset = statement.executeQuery())
			{
				while (rset.next())
				{
					if (rset.getInt("isBase") == 1)
					{
						set.set("baseClassId", rset.getInt("class_id"));
					}
					if (rset.getInt("active") == 1)
					{
						set.set("activeClassId", rset.getInt("class_id"));
						set.set("activeLevel", rset.getInt("level"));
						set.set("activeMaxHp", rset.getInt("maxHp"));
						set.set("activeMaxMp", rset.getInt("maxMp"));
						set.set("activeCurHp", rset.getDouble("curHp"));
						set.set("activeCurMp", rset.getDouble("curMp"));
						set.set("activeExp", rset.getLong("exp"));
						set.set("activeSp", rset.getInt("sp"));
					}
				}
			}
		}
		catch (SQLException e)
		{
			_log.error("could not restore CharacterSelectionInfo classes:", e);
		}

		return set;
	}

	private static CharSelectInfoPackage restoreChar(ResultSet charData, Connection con)
	{
		CharSelectInfoPackage charInfoPackage = null;
		try
		{
			int objectId = charData.getInt("obj_Id");
			StatsSet subclassesSet = restoreClasses(objectId, con);
			boolean female = charData.getInt("sex") == 1;
			PlayerTemplate template = CharTemplateHolder.getInstance().getTemplate(subclassesSet.getInteger("baseClassId"), female);
			if (template == null)
			{
				_log.error("restoreChar fail | template == null | objectId: " + objectId + " | classId: " + subclassesSet.getInteger("baseClassId") + " | female: " + female);
				return null;
			}
			String name = charData.getString("char_name");
			charInfoPackage = new CharSelectInfoPackage(objectId, name);
			charInfoPackage.setLevel(subclassesSet.getInteger("activeLevel"));
			charInfoPackage.setMaxHp(subclassesSet.getInteger("activeMaxHp"));
			charInfoPackage.setCurrentHp(subclassesSet.getDouble("activeCurHp"));
			charInfoPackage.setMaxMp(subclassesSet.getInteger("activeMaxMp"));
			charInfoPackage.setCurrentMp(subclassesSet.getDouble("activeCurMp"));

			charInfoPackage.setX(charData.getInt("x"));
			charInfoPackage.setY(charData.getInt("y"));
			charInfoPackage.setZ(charData.getInt("z"));
			charInfoPackage.setPk(charData.getInt("pkkills"));
			charInfoPackage.setPvP(charData.getInt("pvpkills"));

			charInfoPackage.setFace(charData.getInt("face"));
			charInfoPackage.setHairStyle(charData.getInt("hairstyle"));
			charInfoPackage.setHairColor(charData.getInt("haircolor"));
			charInfoPackage.setSex(female ? 1 : 0);

			charInfoPackage.setExp(subclassesSet.getLong("activeExp"));
			charInfoPackage.setSp(subclassesSet.getInteger("activeSp"));
			charInfoPackage.setClanId(charData.getInt("clanid"));

			charInfoPackage.setKarma(charData.getInt("karma"));
			charInfoPackage.setRace(template.race.ordinal());
			charInfoPackage.setClassId(subclassesSet.getInteger("activeClassId"));
			charInfoPackage.setBaseClassId(subclassesSet.getInteger("baseClassId"));
			long deleteTime = charData.getLong("deletetime");
			int deleteDays;
			if (Config.DELETE_DAYS > 0)
			{
				if (deleteTime > 0)
				{
					deleteTime = System.currentTimeMillis() / 1000 - deleteTime;
					deleteDays = (int) (deleteTime / 3600 / 24);
					if (deleteDays >= Config.DELETE_DAYS)
					{
						CharacterDAO.getInstance().deleteCharByObjId(objectId);
						return null;
					}
					deleteTime = Config.DELETE_DAYS * 3600L * 24L - deleteTime;
				}
				else
				{
					deleteTime = 0;
				}
			}
			charInfoPackage.setDeleteTimer((int) deleteTime);
			charInfoPackage.setLastAccess(charData.getLong("lastAccess") * 1000L);
			charInfoPackage.setAccessLevel(charData.getInt("accesslevel"));
			int points = charData.getInt("vitality") + (int) ((System.currentTimeMillis() - charInfoPackage.getLastAccess()) / 15.);
			if (points > 20000)
			{
				points = 20000;
			}
			else if (points < 0)
			{
				points = 0;
			}
			charInfoPackage.setVitalityPoints(points);

			if (charInfoPackage.getAccessLevel() < 0 && !AutoBan.checkIsBanned(objectId))
			{
				charInfoPackage.setAccessLevel(0);
			}
		}
		catch (SQLException e)
		{
			_log.error("SQLException in CharacterSelectionInfo ", e);
		}

		return charInfoPackage;
	}
}