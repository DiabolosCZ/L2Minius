package l2minius.gameserver.scripts.instances;

import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.instances.NpcInstance;
import l2minius.gameserver.model.SimpleSpawner;
import l2minius.gameserver.instancemanager.ReflectionManager;
import l2minius.gameserver.model.entity.Reflection;
import l2minius.gameserver.utils.Location; // Import pro třídu Location
import l2minius.gameserver.ThreadPoolManager; // Import pro ThreadPoolManager
import l2minius.gameserver.templates.npc.NpcTemplate; // Import pro NpcTemplate
import l2minius.gameserver.data.xml.holder.NpcHolder; // Import pro NpcHolder
import l2minius.commons.threading.RunnableImpl; // Import pro RunnableImpl
import java.util.concurrent.TimeUnit; // Import pro TimeUnit

public class PoseidonArmor extends Reflection {

    private static final int INSTANCE_ID = 8004; // Unikátní ID pro instanci
    private static final long INSTANCE_TIME_LIMIT = TimeUnit.MINUTES.toMillis(120); // Časový limit pro instanci v minutách
    private static final int MONSTER_ID = 90000; // ID pro monstra
    private static final int MONSTER_COUNT = 5; // Počet monster
    private static final long RESPAWN_DELAY = TimeUnit.MINUTES.toMillis(10); // Respawn po 10 minutách

    public PoseidonArmor() {
        super();
        setName("PoseidonArmor");
    }

    @Override
    public void onPlayerEnter(Player player) {
        // Naplánování automatického ukončení instance po 120 minutách
        ThreadPoolManager.getInstance().schedule(new RunnableImpl() {
            @Override
            public void runImpl() {
                collapse(); // Ukončení instance
                System.out.println("Instance PoseidonArmor byla automaticky ukončena po 120 minutách.");
            }
        }, INSTANCE_TIME_LIMIT);

        player.sendMessage("Vstoupili jste do instance PoseidonArmor. Časový limit je 120 minut.");

        // Teleport hráče na startovací lokaci
        player.teleToLocation(getStartLoc(), this);

        // Spawn monster
        spawnMonsters();
    }

    // Definice výchozí lokace pro teleport
    private Location getStartLoc() {
        return new Location(150000, 150000, -5000); // Příklad souřadnic pro startovací lokaci
    }

    // Metoda pro spawnování monster
    private void spawnMonsters() {
        for (int i = 0; i < MONSTER_COUNT; i++) {
            Location monsterLocation = new Location(150000 + i * 100, 150000, -5000); // Různé souřadnice pro každé monstrum

            NpcTemplate monsterTemplate = NpcHolder.getInstance().getTemplate(MONSTER_ID);
            if (monsterTemplate != null) {
                try {
                    SimpleSpawner spawner = new SimpleSpawner(monsterTemplate);
                    spawner.setLoc(monsterLocation);
                    spawner.setAmount(1);
                    spawner.setRespawnDelay((int) (RESPAWN_DELAY / 1000)); // Nastavení respawnovací doby v sekundách
                    spawner.doSpawn(true);
                    spawner.startRespawn();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("NpcTemplate for ID " + MONSTER_ID + " not found!");
            }
        }
    }
}
