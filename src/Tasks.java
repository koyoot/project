import org.jfree.ui.RefineryUtilities;
import java.sql.*;
import java.util.ArrayList;

public class Tasks {

    private static Connection connection;

    public Tasks(Connection connection) {
        this.connection = connection;
    }

    public void ExecuteSecondTask() throws SQLException {
        try (var request = connection.createStatement()) {
            var resultSet = request.executeQuery(
            "SELECT name, region, happinesRank, happinesScore, standartError, " +
                "economy, family, health, freedom, trust, generosity, dystopiaResidual " + 
                "FROM countries WHERE economy = (SELECT MAX(economy) " +
                "FROM countries WHERE region = 'Latin America and Caribbean' OR region = 'Eastern Asia')");
            resultSet.next();
            System.out.println("=============================================");
            System.out.println("Страна с максимальными показателями экономики");
            System.out.println(new Country(
                    resultSet.getString("name"),
                    resultSet.getString("region"),
                    resultSet.getInt("happinesRank"),
                    resultSet.getFloat("happinesScore"),
                    resultSet.getFloat("standartError"),
                    resultSet.getFloat("economy"),
                    resultSet.getFloat("family"),
                    resultSet.getFloat("health"),
                    resultSet.getFloat("freedom"),
                    resultSet.getFloat("trust"),
                    resultSet.getFloat("generosity"),
                    resultSet.getFloat("dystopiaResidual")));
        }
    }

    public void ExecuteThirdTask() throws SQLException {
        try (var request = connection.createStatement()) {
            var resultSet = request.executeQuery("SELECT name, region, happinesRank, happinesScore, standartError, economy, family, health, freedom, trust, generosity, dystopiaResidual, MIN(" +
                    "ABS((happinesScore - (SELECT AVG(happinesScore) FROM countries WHERE region = 'Western Europe' OR region = 'North America')) * 100 / (SELECT AVG(happinesScore) FROM countries WHERE region = 'Western Europe' OR region = 'North America')) + " +
                    "ABS((standartError - (SELECT AVG(standartError) FROM countries WHERE region = 'Western Europe' OR region = 'North America')) * 100 / (SELECT AVG(standartError) FROM countries WHERE region = 'Western Europe' OR region = 'North America')) + " +
                    "ABS((economy - (SELECT AVG(economy) FROM countries WHERE region = 'Western Europe' OR region = 'North America')) * 100 / (SELECT AVG(economy) FROM countries WHERE region = 'Western Europe' OR region = 'North America')) + " +
                    "ABS((family - (SELECT AVG(family) FROM countries WHERE region = 'Western Europe' OR region = 'North America')) * 100 / (SELECT AVG(family) FROM countries WHERE region = 'Western Europe' OR region = 'North America')) + " +
                    "ABS((health - (SELECT AVG(health) FROM countries WHERE region = 'Western Europe' OR region = 'North America')) * 100 / (SELECT AVG(health) FROM countries WHERE region = 'Western Europe' OR region = 'North America')) + " +
                    "ABS((freedom - (SELECT AVG(freedom) FROM countries WHERE region = 'Western Europe' OR region = 'North America')) * 100 / (SELECT AVG(freedom) FROM countries WHERE region = 'Western Europe' OR region = 'North America')) + " +
                    "ABS((trust - (SELECT AVG(trust) FROM countries WHERE region = 'Western Europe' OR region = 'North America')) * 100 / (SELECT AVG(trust) FROM countries WHERE region = 'Western Europe' OR region = 'North America')) + " +
                    "ABS((generosity - (SELECT AVG(generosity) FROM countries WHERE region = 'Western Europe' OR region = 'North America')) * 100 / (SELECT AVG(generosity) FROM countries WHERE region = 'Western Europe' OR region = 'North America')) + " +
                    "ABS((dystopiaResidual - (SELECT AVG(dystopiaResidual) FROM countries WHERE region = 'Western Europe' OR region = 'North America')) * 100 / (SELECT AVG(dystopiaResidual) FROM countries WHERE region = 'Western Europe' OR region = 'North America'))) " +
                    "FROM countries WHERE region = 'Western Europe' OR region = 'North America'");
            resultSet.next();
            System.out.println("================================================================");
            System.out.println("Страна с самыми <<средними показателями>> показателями экономики");
            System.out.println(new Country(resultSet.getString("name"), 
                resultSet.getString("region"), 
                resultSet.getInt("happinesRank"), 
                resultSet.getFloat("happinesScore"), 
                resultSet.getFloat("standartError"), 
                resultSet.getFloat("economy"), 
                resultSet.getFloat("family"), 
                resultSet.getFloat("health"), 
                resultSet.getFloat("freedom"), 
                resultSet.getFloat("trust"), 
                resultSet.getFloat("generosity"), 
                resultSet.getFloat("dystopiaResidual")));
        }
    }

    private static ArrayList<Country> getAllCountries(){
        var countries = new ArrayList<Country>();
        try (var request = connection.createStatement()) {
            var resultSet = request.executeQuery("SELECT name, region, happinesRank, happinesScore, standartError, economy, family, " +
                "health, freedom, trust, generosity, dystopiaResidual " + 
                "FROM countries");
            while (resultSet.next())
                countries.add(new Country(resultSet.getString("name"), 
                    resultSet.getString("region"), 
                    resultSet.getInt("happinesRank"), 
                    resultSet.getFloat("happinesScore"), 
                    resultSet.getFloat("standartError"), 
                    resultSet.getFloat("economy"), 
                    resultSet.getFloat("family"), 
                    resultSet.getFloat("health"), 
                    resultSet.getFloat("freedom"), 
                    resultSet.getFloat("trust"), 
                    resultSet.getFloat("generosity"), 
                    resultSet.getFloat("dystopiaResidual")));
        }
        catch (SQLException e) {
            System.out.println("Произошла ошибка!");
            e.printStackTrace();
        }
        return countries;

    }

    public static void ExecuteFirstTask() {
        var countries = getAllCountries();
        var window = new ChartBuilder(getLabels(countries),
                getValues(countries),
                "Страны",
                "Показатели экономики");
        window.pack();
        RefineryUtilities.centerFrameOnScreen(window);
        window.setVisible(true);
        System.out.println("График выведен на экран. Чтобы программа не завершилась, не закрывайте его(лучше свернуть).");
    }

    private static String[] getLabels(ArrayList<Country> countries) {
        var labels = new String[countries.size()];
        for (var i = 0; i < countries.size(); i++) labels[i] = countries.get(i).getCountryName();
        return labels;
    }

    private static float[] getValues(ArrayList<Country> countries) {
        var values = new float[countries.size()];
        for (var i = 0; i < countries.size(); i++) values[i] = countries.get(i).getCountryEconomy();
        return values;
    }
}
