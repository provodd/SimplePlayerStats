package provodd.simplePlayerStats.placeholders;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;

import java.util.*;
import java.util.stream.Collectors;

public class PlaceholderHelper {

    public static List<Map.Entry<String, Integer>> getSortedStats(Statistic statistic) {
        Map<String, Integer> stats = new HashMap<>();

        //перебираем в массиве всех игроков
        for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
            try {
                //получаем соответствующую статистику. В зависимости от типа она может по разному обрабатываться в
                //функции getPlayerStatisticValue
                int value = getPlayerStatisticValue(p, statistic);
                //переменная stats - это хэш таблица(ключ: значение). Добавляем туда имя игрока и значение
                stats.put(p.getName(), value);
            } catch (IllegalArgumentException e) {
                Bukkit.getLogger().warning("Не удалось получить статистику для игрока " + p.getName() + " " + e.getMessage());
            }
        }

        return stats.entrySet().stream()
                .sorted((a, b) -> Integer.compare(b.getValue(), a.getValue()))
                .collect(Collectors.toList());
    }

    public static int getPlayerStatisticValue(OfflinePlayer p, Statistic statistic) {
        int value = 0;
        if (p.hasPlayedBefore() || p.isOnline()) {
            if (statistic == Statistic.MINE_BLOCK) {
                value = Arrays.stream(Material.values())
                        .filter(Material::isBlock)
                        .mapToInt(material -> p.getStatistic(Statistic.MINE_BLOCK, material))
                        .sum();
            } else if (statistic == Statistic.ENTITY_KILLED_BY) {
                value = p.getStatistic(Statistic.ENTITY_KILLED_BY, EntityType.PLAYER); // Получаем только убийства от игроков
            } else {
                value = p.getStatistic(statistic);
            }
        }
        return value;
    }

    public static String getStatTypeFromParams(String params) {
        if (params.startsWith(Constants.MINE_PREFIX)) return Constants.MINE_PREFIX;
        if (params.startsWith(Constants.PLAYTIME_PREFIX)) return Constants.PLAYTIME_PREFIX;
        if (params.startsWith(Constants.PVP_KILLS_PREFIX)) return Constants.PVP_KILLS_PREFIX;
        if (params.startsWith(Constants.MOB_KILLS_PREFIX)) return Constants.MOB_KILLS_PREFIX;
        if (params.startsWith(Constants.PVP_DEATHS_PREFIX)) return Constants.PVP_DEATHS_PREFIX;
        if (params.startsWith(Constants.MOB_DEATHS_PREFIX)) return Constants.MOB_DEATHS_PREFIX;
        if (params.startsWith(Constants.FISHERS_PREFIX)) return Constants.FISHERS_PREFIX;
        return null;
    }
}
