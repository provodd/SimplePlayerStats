package provodd.simplePlayerStats.placeholders.actions;

import java.util.List;
import java.util.Map;

public class TopFishAction {

    // Метод для получения имени рыбака по индексу
    public static String getTopFishersName(List<Map.Entry<String, Integer>> sortedStats, String params) {
        try {
            int index = Integer.parseInt(params.replaceAll("\\D+", "")) - 1;
            if (index >= 0 && index < sortedStats.size()) {
                return sortedStats.get(index).getKey(); // Возвращаем имя игрока
            }
        } catch (NumberFormatException ignored) {
        }
        return "Неизвестно";
    }

    // Метод для получения значения статистики по индексу (количество пойманной рыбы)
    public static String getTopFishersValue(List<Map.Entry<String, Integer>> sortedStats, String params) {
        try {
            int index = Integer.parseInt(params.replaceAll("\\D+", "")) - 1;
            if (index >= 0 && index < sortedStats.size()) {
                int value = sortedStats.get(index).getValue();
                return String.valueOf(value); // Возвращаем количество пойманной рыбы
            }
        } catch (NumberFormatException ignored) {
        }
        return "0";
    }

    public static String getTopFishers(List<Map.Entry<String, Integer>> sortedStats, String params) {
        try {
            int index = Integer.parseInt(params.replaceAll("\\D+", "")) - 1;
            if (index >= 0 && index < sortedStats.size()) {
                return sortedStats.get(index).getKey() + " - " + sortedStats.get(index).getValue();
            }
        } catch (NumberFormatException ignored) {
        }
        return "Нет данных";
    }
}
