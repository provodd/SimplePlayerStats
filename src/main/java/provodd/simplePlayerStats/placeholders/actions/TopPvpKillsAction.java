package provodd.simplePlayerStats.placeholders.actions;

import java.util.List;
import java.util.Map;

public class TopPvpKillsAction {

    // Метод для получения имени игрока по индексу
    public static String getTopPvPKillsName(List<Map.Entry<String, Integer>> sortedStats, String params) {
        try {
            int index = Integer.parseInt(params.replaceAll("\\D+", "")) - 1;
            if (index >= 0 && index < sortedStats.size()) {
                return sortedStats.get(index).getKey();  // Возвращаем имя игрока
            }
        } catch (NumberFormatException ignored) {
        }
        return "Неизвестно";
    }

    // Метод для получения значения статистики по индексу
    public static String getTopPvPKillsValue(List<Map.Entry<String, Integer>> sortedStats, String params) {
        try {
            int index = Integer.parseInt(params.replaceAll("\\D+", "")) - 1;
            if (index >= 0 && index < sortedStats.size()) {
                int value = sortedStats.get(index).getValue();
                return String.valueOf(value); // Возвращаем количество убийств
            }
        } catch (NumberFormatException ignored) {
        }
        return "0";
    }
}
