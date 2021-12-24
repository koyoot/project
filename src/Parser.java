import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.sql.*;
import org.sqlite.JDBC;

public class Parser {

    public static String path = System.getProperty("user.dir") + "\\files";

    public static Connection CreateDatabase() {
        var file = new File(path + "\\countries_happines_2015.csv");
        var countries = new ArrayList<Country>();
        try (var reader = new BufferedReader(new FileReader(file))) {
            for (var line = reader.readLine();
                 (line = reader.readLine()) != null;) { addCountry(line.split(","), countries); }
            return CreateTable(countries);
        } catch (Exception e) {
            System.out.println("Произошла ошибка при чтении файла!");
            return null;
        }
    }

    private static void addCountry(String[] parameters, ArrayList<Country> countries) {
        countries.add(new Country(parameters[0], parameters[1],
                Integer.parseInt(parameters[2]), Float.parseFloat(parameters[3]), Float.parseFloat(parameters[4]),
                Float.parseFloat(parameters[5]), Float.parseFloat(parameters[6]), Float.parseFloat(parameters[7]),
                Float.parseFloat(parameters[8]), Float.parseFloat(parameters[9]), Float.parseFloat(parameters[10]),
                Float.parseFloat(parameters[11])));
    }

    private static Connection CreateTable(ArrayList<Country> countries) throws SQLException {
        DriverManager.registerDriver(new JDBC());
        if (!(new File(path + "\\database.sqlite").exists())) {
            var connect = DriverManager.getConnection("jdbc:sqlite:" + path + "\\database.sqlite");
            try (var request = connect.createStatement()) {
                request.executeUpdate(
                "CREATE TABLE countries  (name TEXT PRIMARY KEY, " +
                    " region TEXT NOT NULL,  happinesRank INTEGER NOT NULL, " +
                    " happinesScore REAL NOT NULL,  standartError REAL NOT NULL, " +
                    " economy REAL NOT NULL,  family REAL NOT NULL,  health REAL NOT NULL,  freedom REAL NOT NULL, " +
                    " trust REAL NOT NULL,  generosity REAL NOT NULL,  dystopiaResidual REAL NOT NULL)"
                );
                for (var country : countries) AddToDatabase(country, connect);
                return connect;
            }
        }
        else { 
            System.out.println("База данных была создана ранее.");
            return DriverManager.getConnection("jdbc:sqlite:" + path + "\\database.sqlite");
        }
    }

    private static void AddToDatabase(Country country, Connection connection) throws SQLException {
        try (var request = connection.prepareStatement(
            "INSERT INTO countries(`name`, `region`, `happinesRank`, `happinesScore`, " +
            "`standartError`, `economy`, `family`, `health`, `freedom`, `trust`, `generosity`, " +
            "`dystopiaResidual`) " + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
                request.setObject(1, country.getCountryName());
                request.setObject(2, country.getCountryRegion());
                request.setObject(3, country.getRank());
                request.setObject(4, country.getHappiness());
                request.setObject(5, country.getError());
                request.setObject(6, country.getCountryEconomy());
                request.setObject(7, country.getCountryFamily());
                request.setObject(8, country.getCountryHealth());
                request.setObject(9, country.getCountryFreedom());
                request.setObject(10, country.getCountryTrust());
                request.setObject(11, country.getCountryGenerosity());
                request.setObject(12, country.getDystopia());
                request.execute();
            }
        catch (SQLException e) {
            System.out.println("Произошла ошибка!");
            e.printStackTrace();
        }
    }
}
