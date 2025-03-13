package provodd.simplePlayerStats.placeholders.actions;

import java.util.List;
import java.util.Map;

public class TopPlaytimeAction {

    // Метод для получения имени игрока по индексу
    public static String getTopPlaytimeName(List<Map.Entry<String, Integer>> sortedStats, String params) {
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
    public static String getTopPlaytimeValue(List<Map.Entry<String, Integer>> sortedStats, String params) {
        try {
            int index = Integer.parseInt(params.replaceAll("\\D+", "")) - 1;
            if (index >= 0 && index < sortedStats.size()) {
                int ticks = sortedStats.get(index).getValue();
                int totalMinutes = ticks / 20 / 60;
                int days = totalMinutes / 1440;
                int hours = (totalMinutes % 1440) / 60;
                return days + " дн. " + hours + " ч.";
            }
        } catch (NumberFormatException ignored) {
        }
        return "0 дн. 0 ч.";
    }
}
