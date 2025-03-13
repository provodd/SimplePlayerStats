package provodd.simplePlayerStats.placeholders.actions;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;

import java.util.*;

public class TopMobDeathsAction {

    // Метод для получения имени игрока по индексу
    public static String getTopMobDeathsName(List<Map.Entry<String, Integer>> sortedStats, String params) {
        try {
            int index = Integer.parseInt(params.replaceAll("\\D+", "")) - 1;
            if (index >= 0 && index < sortedStats.size()) {
                return sortedStats.get(index).getKey(); // Возвращаем имя игрока
            }
        } catch (NumberFormatException ignored) {
        }
        return "Неизвестно";
    }

    // Метод для получения значения статистики по индексу
    public static String getTopMobDeathsValue(List<Map.Entry<String, Integer>> sortedStats, String params) {
        try {
            int index = Integer.parseInt(params.replaceAll("\\D+", "")) - 1;
            if (index >= 0 && index < sortedStats.size()) {
                int value = sortedStats.get(index).getValue();
                return String.valueOf(value); // Возвращаем количество смертей
            }
        } catch (NumberFormatException ignored) {
        }
        return "0";
    }

    public static String getTopMobDeaths(List<Map.Entry<String, Integer>> sortedStats, String params) {
        // Пробегаем по всем игрокам и их статистике смертей от мобов
        Map<String, Integer> stats = new HashMap<>();

        for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
            int totalDeaths = 0;

            if (p.hasPlayedBefore() || p.isOnline()) {
                // Для каждой сущности суммируем число смертей
                for (EntityType entity : EntityType.values()) {
                    try {
                        if (entity != EntityType.UNKNOWN && entity.isAlive()) {
                            totalDeaths += p.getStatistic(Statistic.ENTITY_KILLED_BY, entity);
                        }
                    } catch (IllegalArgumentException e) {
                        Bukkit.getLogger().warning("Не удалось получить статистику для игрока " + p.getName() +
                                " для сущности " + entity.name() + ": " + e.getMessage());
                    }
                }
            }

            stats.put(p.getName(), totalDeaths);
        }

        // Сортируем статистику по убыванию
        List<Map.Entry<String, Integer>> sorted = new ArrayList<>(stats.entrySet());
        sorted.sort((a, b) -> Integer.compare(b.getValue(), a.getValue()));

        try {
            // Извлекаем индекс из параметра и возвращаем соответствующий элемент
            int index = Integer.parseInt(params.replaceAll("\\D+", "")) - 1;
            if (index >= 0 && index < sorted.size()) {
                return sorted.get(index).getKey() + " - " + sorted.get(index).getValue();
            }
        } catch (NumberFormatException ignored) {
        }

        return "Нет данных";
    }
}
