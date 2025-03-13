package provodd.simplePlayerStats.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import provodd.simplePlayerStats.placeholders.actions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaceholderHandler extends PlaceholderExpansion {

    private final Plugin plugin;
    private final Map<String, List<Map.Entry<String, Integer>>> cachedStats = new HashMap<>();
    private long lastCacheTime;

    public PlaceholderHandler(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getIdentifier() {
        return "sps";
    }

    @Override
    public String getAuthor() {
        return plugin.getDescription().getAuthors().get(0);
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        // Обновляем кэш раз в минуту
        if (System.currentTimeMillis() - lastCacheTime > 60_000) {
            initializeCache();
        }

        // Обработка топ игроков
        String statType = PlaceholderHelper.getStatTypeFromParams(params);

        // если статистика самого игрока, то ищем его положение в списке
        if (params.contains("self")) {
            if (player instanceof Player) {
                String playerPosition = getPlayerPositionInStats(player, statType);
                params = params.replace("self", playerPosition);
            } else {
                return "Это не игрок!";
            }
        }

        if (statType != null) {
            if (params.contains("_name")) {
                return getTopPlayerName(statType, params);
            } else if (params.contains("_value")) {
                return getTopPlayerValue(statType, params);
            }
        }

        return "Неверный плейсхолдер!";
    }

    // Функция для поиска положения игрока в cachedStats по типу статистики
    public String getPlayerPositionInStats(OfflinePlayer player, String statType) {
        // Проверяем, что статистика существует в кэше
        if (cachedStats.containsKey(statType)) {
            List<Map.Entry<String, Integer>> statsList = cachedStats.get(statType);

            // Ищем положение игрока в списке
            for (int i = 0; i < statsList.size(); i++) {
                Map.Entry<String, Integer> entry = statsList.get(i);
                // Если имя игрока совпадает с текущим в списке, возвращаем его позицию
                if (entry.getKey().equals(player.getName())) {
                    return String.valueOf(i + 1); // Позиция начинается с 1
                }
            }
        }
        return "Игрок не найден в топе!";
    }

    private String getTopPlayerName(String statType, String params) {
        switch (statType) {
            case Constants.MINE_PREFIX:
                return TopMineAction.getTopMineName(cachedStats.get(statType), params);
            case Constants.PLAYTIME_PREFIX:
                return TopPlaytimeAction.getTopPlaytimeName(cachedStats.get(statType), params);
            case Constants.PVP_KILLS_PREFIX:
                return TopPvpKillsAction.getTopPvPKillsName(cachedStats.get(statType), params);
            case Constants.MOB_KILLS_PREFIX:
                return TopMobKillsAction.getTopMobKillsName(cachedStats.get(statType), params);
            case Constants.PVP_DEATHS_PREFIX:
                return TopPvpDeathsAction.getTopPvPDeathsName(cachedStats.get(statType), params);
            case Constants.MOB_DEATHS_PREFIX:
                return TopMobDeathsAction.getTopMobDeathsName(cachedStats.get(statType), params);
            case Constants.FISHERS_PREFIX:
                return TopFishAction.getTopFishersName(cachedStats.get(statType), params);
            default:
                return "Неизвестно";
        }
    }

    private String getTopPlayerValue(String statType, String params) {
        switch (statType) {
            case Constants.MINE_PREFIX:
                return TopMineAction.getTopMineValue(cachedStats.get(statType), params);
            case Constants.PLAYTIME_PREFIX:
                return TopPlaytimeAction.getTopPlaytimeValue(cachedStats.get(statType), params);
            case Constants.PVP_KILLS_PREFIX:
                return TopPvpKillsAction.getTopPvPKillsValue(cachedStats.get(statType), params);
            case Constants.MOB_KILLS_PREFIX:
                return TopMobKillsAction.getTopMobKillsValue(cachedStats.get(statType), params);
            case Constants.PVP_DEATHS_PREFIX:
                return TopPvpDeathsAction.getTopPvPDeathsValue(cachedStats.get(statType), params);
            case Constants.MOB_DEATHS_PREFIX:
                return TopMobDeathsAction.getTopMobDeathsValue(cachedStats.get(statType), params);
            case Constants.FISHERS_PREFIX:
                return TopFishAction.getTopFishersValue(cachedStats.get(statType), params);
            default:
                return "0";
        }
    }

    public void initializeCache() {
        cachedStats.put(Constants.MINE_PREFIX, PlaceholderHelper.getSortedStats(Statistic.MINE_BLOCK));
        cachedStats.put(Constants.PLAYTIME_PREFIX, PlaceholderHelper.getSortedStats(Statistic.PLAY_ONE_MINUTE));
        cachedStats.put(Constants.PVP_KILLS_PREFIX, PlaceholderHelper.getSortedStats(Statistic.PLAYER_KILLS));
        cachedStats.put(Constants.MOB_KILLS_PREFIX, PlaceholderHelper.getSortedStats(Statistic.MOB_KILLS));
        cachedStats.put(Constants.PVP_DEATHS_PREFIX, PlaceholderHelper.getSortedStats(Statistic.DEATHS));
        cachedStats.put(Constants.MOB_DEATHS_PREFIX, PlaceholderHelper.getSortedStats(Statistic.ENTITY_KILLED_BY));
        cachedStats.put(Constants.FISHERS_PREFIX, PlaceholderHelper.getSortedStats(Statistic.FISH_CAUGHT));
        lastCacheTime = System.currentTimeMillis();
    }
}
