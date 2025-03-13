package provodd.simplePlayerStats.placeholders.actions;
import java.util.List;
import java.util.Map;

public class TopBalanceAction {
    public static String getTopRichest(List<Map.Entry<String, Integer>> sortedStats, String params) {
        try {
            int index = Integer.parseInt(params.replaceAll("\\D+", "")) - 1;
            if (index >= 0 && index < sortedStats.size()) {
                return sortedStats.get(index).getKey() + " - " + sortedStats.get(index).getValue() + "";
            }
        } catch (NumberFormatException ignored) {
        }
        return "Нет данных";
    }
}
