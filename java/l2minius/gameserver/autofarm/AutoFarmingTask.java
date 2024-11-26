package l2minius.gameserver.autofarm;

import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.Skill;
import l2minius.gameserver.model.instances.NpcInstance;
import l2minius.gameserver.model.instances.MonsterInstance;
import l2minius.gameserver.model.GameObjectsStorage;
import l2minius.gameserver.ai.CtrlIntention;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class AutoFarmingTask {

    private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
    private static final ConcurrentHashMap<Player, ScheduledFuture<?>> farmingTasks = new ConcurrentHashMap<>();
    private static final int MAX_DISTANCE = 2000; // Maximální vzdálenost pro hledání nepřátel
    private static final int INACTIVITY_LIMIT = 60; // Čas (v sekundách) po kterém se najde nový cíl

    private final Player player;
    private NpcInstance currentTarget;
    private long lastAttackTime; // Ukládá čas posledního pokusu o útok na cíl

    public AutoFarmingTask(Player player) {
        this.player = player;
        this.currentTarget = null;
        this.lastAttackTime = System.currentTimeMillis();
    }

    public void performFarming() {
        long currentTime = System.currentTimeMillis();

        // Pokud hráč déle než INACTIVITY_LIMIT sekund nezaútočil na aktuální cíl, najde se nový cíl
        if (currentTarget != null && (currentTime - lastAttackTime) / 1000 > INACTIVITY_LIMIT) {
            player.sendMessage("No attack on target for 60 seconds. Searching for a new target...");
            currentTarget = findNearestEnemy();
        }

        // Pokud cíl neexistuje nebo je mrtvý, najdi nový cíl
        if (currentTarget == null || currentTarget.isDead()) {
            currentTarget = findNearestEnemy();
        }

        if (currentTarget != null) {
            setTargetAndAttack(currentTarget);
            useSkillsFromBar();
        } else {
            player.sendMessage("No enemies nearby within " + MAX_DISTANCE + " range.");
        }
    }

    private NpcInstance findNearestEnemy() {
        return GameObjectsStorage.getAllNpcs().stream()
                .filter(npc -> npc instanceof MonsterInstance)
                .filter(monster -> !monster.isDead() && monster.getCurrentHp() > 0)
                .filter(monster -> player.getDistance(monster) <= MAX_DISTANCE)
                .filter(this::isSameHeight)
                .min((npc1, npc2) -> Double.compare(player.getDistance(npc1), player.getDistance(npc2)))
                .orElse(null);
    }

    private boolean isSameHeight(NpcInstance monster) {
        return Math.abs(player.getZ() - monster.getZ()) < 50; // Kontrola, zda je nepřítel na podobné výšce
    }

    private void setTargetAndAttack(NpcInstance target) {
        if (player.getTarget() != target) {
            player.setTarget(target); // Nastavení nového cíle
            player.sendMessage("Targeting: " + target.getName());
        }

        // Pokud hráč neútočí, nastav AI na útok
        if (player.getAI().getIntention() != CtrlIntention.AI_INTENTION_ATTACK) {
            player.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, target);
            player.sendMessage("Attacking: " + target.getName());
        }

        // Aktualizace času posledního pokusu o útok
        lastAttackTime = System.currentTimeMillis();
    }

    private void useSkillsFromBar() {
        if (player.getSkillBar() != null) {
            // Attack skills (Slot 1-4)
            for (int slot = 1; slot <= 4; slot++) {
                Skill skill = player.getSkillBar().getSkill(slot);
                if (skill != null) {
                    player.useSkill(skill);
                }
            }

            // Chance-based skills (Slot 5-6)
            for (int slot = 5; slot <= 6; slot++) {
                Skill skill = player.getSkillBar().getSkill(slot);
                if (skill != null && Math.random() > 0.5) { // Náhodná šance
                    player.useSkill(skill);
                }
            }

            // Self-buff skills (Slot 7-8)
            for (int slot = 7; slot <= 8; slot++) {
                Skill skill = player.getSkillBar().getSkill(slot);
                if (skill != null && !player.isSkillActive(skill)) { // Pokud buff ještě není aktivní
                    player.useSkill(skill);
                }
            }

            // Emergency skills (Slot 9-10)
            for (int slot = 9; slot <= 10; slot++) {
                Skill skill = player.getSkillBar().getSkill(slot);
                if (skill != null) {
                    if (slot == 9 && player.getCurrentHp() < player.getMaxHp() * 0.2) { // Low HP skill
                        player.useSkill(skill);
                    }
                    if (slot == 10 && player.getCurrentMp() < player.getMaxMp() * 0.2) { // Low MP skill
                        player.useSkill(skill);
                    }
                }
            }
        }
    }

    public static void startForPlayer(Player player) {
        if (farmingTasks.containsKey(player)) {
            player.sendMessage("Farming is already running.");
            return;
        }

        AutoFarmingTask task = new AutoFarmingTask(player);
        ScheduledFuture<?> future = executor.scheduleAtFixedRate(
                task::performFarming,
                0, 2, TimeUnit.SECONDS
        );
        farmingTasks.put(player, future);
        player.sendMessage("Auto-farming has started.");
    }

    public static void stopForPlayer(Player player) {
        ScheduledFuture<?> future = farmingTasks.remove(player);
        if (future != null) {
            future.cancel(true);
            player.sendMessage("Farming has been stopped.");
        } else {
            player.sendMessage("No farming task is running.");
        }
    }
}
