package l2minius.gameserver.autofarm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import l2minius.gameserver.database.DatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoFarmSettings {
    private static final Logger _log = LoggerFactory.getLogger(AutoFarmSettings.class);

    private final int playerId;
    private final List<Integer> attackSkills = new ArrayList<>();
    private final List<Integer> chanceSkills = new ArrayList<>();
    private final List<Integer> lifeSkills = new ArrayList<>();
    private final List<Integer> selfSkills = new ArrayList<>();

    public AutoFarmSettings(int playerId) {
        this.playerId = playerId;
    }

    public List<Integer> getAttackSkills() {
        return attackSkills;
    }

    public List<Integer> getChanceSkills() {
        return chanceSkills;
    }

    public List<Integer> getLifeSkills() {
        return lifeSkills;
    }

    public List<Integer> getSelfSkills() {
        return selfSkills;
    }

    /**
     * Uloží nastavení do databáze
     */
    public void save(String skillType, List<Integer> skills) {
        String sqlDelete = "DELETE FROM autofarm_settings WHERE player_id = ? AND skill_type = ?";
        String sqlInsert = "INSERT INTO autofarm_settings (player_id, skill_type, skill_id) VALUES (?, ?, ?)";
        try (Connection con = DatabaseFactory.getInstance().getConnection();
             PreparedStatement deleteStmt = con.prepareStatement(sqlDelete);
             PreparedStatement insertStmt = con.prepareStatement(sqlInsert)) {

            // Nejprve smažeme stávající záznamy
            deleteStmt.setInt(1, playerId);
            deleteStmt.setString(2, skillType);
            deleteStmt.executeUpdate();

            // Poté vložíme nové záznamy
            for (int skillId : skills) {
                insertStmt.setInt(1, playerId);
                insertStmt.setString(2, skillType);
                insertStmt.setInt(3, skillId);
                insertStmt.addBatch();
            }
            insertStmt.executeBatch();

            _log.info("AutoFarm settings saved for player ID: {}", playerId);
        } catch (Exception e) {
            _log.error("Error saving AutoFarm settings for player ID: {}", playerId, e);
        }
    }

    /**
     * Načte nastavení z databáze
     */
    public void load() {
        String sql = "SELECT skill_type, skill_id FROM autofarm_settings WHERE player_id = ?";
        try (Connection con = DatabaseFactory.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, playerId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String skillType = rs.getString("skill_type");
                    int skillId = rs.getInt("skill_id");

                    switch (skillType.toLowerCase()) {
                        case "attack" -> attackSkills.add(skillId);
                        case "chance" -> chanceSkills.add(skillId);
                        case "life" -> lifeSkills.add(skillId);
                        case "self" -> selfSkills.add(skillId);
                    }
                }
            }

            _log.info("AutoFarm settings loaded for player ID: {}", playerId);
        } catch (Exception e) {
            _log.error("Error loading AutoFarm settings for player ID: {}", playerId, e);
        }
    }
}
