package instances;

import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.entity.Reflection;
import l2minius.gameserver.model.instances.NpcInstance;
import l2minius.gameserver.utils.Location;
import l2minius.gameserver.templates.npc.NpcTemplate;

public class MiniusBossInstance extends Reflection {

    private static final int INSTANCE_ID = 8003; // Unikátní ID pro instanci
    private static final int MIN_PARTY_MEMBERS = 4; // Minimální počet hráčů
    private static final int MAX_PARTY_MEMBERS = 9; // Maximální počet hráčů
    private static final long INSTANCE_TIME_LIMIT = 70L; // Časový limit pro instanci v minutách
    private static final int MINIUS_BOSS_ID = 90001; // ID pro MiniusBoss

    public MiniusBossInstance() {
        super();
        setName("MiniusBossInstance");
    }

    @Override
    public void onPlayerEnter(Player player) {
        if (player.getParty() == null) {
            player.sendMessage("Musíte být ve skupině, abyste mohli vstoupit do instance.");
            return;
        }

        int partySize = player.getParty().getMemberCount();
        if (partySize < MIN_PARTY_MEMBERS || partySize > MAX_PARTY_MEMBERS) {
            player.sendMessage("Tato instance vyžaduje skupinu o velikosti 4 až 9 hráčů.");
            return;
        }

        player.getParty().getMembers().forEach(member -> {
            member.teleToLocation(getStartLoc(), this);
            getPlayers().add(member); // Přidání hráče do instance
        });

        player.sendMessage("Instance byla spuštěna pro vaši skupinu.");
        spawnMiniusBoss();
    }

    private Location getStartLoc() {
        return new Location(82760, 147960, 0); // Příklad souřadnic pro startovací lokaci
    }

    private void spawnMiniusBoss() {
        Location bossLocation = new Location(82760, 147960, 0); // Příklad souřadnic pro bosse

        NpcInstance miniusBoss = addSpawnWithoutRespawn(MINIUS_BOSS_ID, bossLocation, 0);
        if (miniusBoss != null) {
            miniusBoss.setReflection(this); // Připojení NPC k instanci
            miniusBoss.spawnMe(bossLocation); // Spawn bosse na určené lokaci
        } else {
            System.out.println("Failed to spawn MiniusBoss with ID " + MINIUS_BOSS_ID);
        }
    }
}
