import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        var scanner = new Scanner(System.in);
        var tasks = new Tasks(Parser.CreateDatabase());
        while (true) {
            System.out.println("Введите команду: (FIRST, SECOND, THIRD, HELP, STOP)");
            switch (scanner.nextLine()) {
                case ("HELP"):
                    System.out.println("Введите команды для того, чтобы получить нужный вам результат");
                    System.out.println("FIRST => если хотите получить результат решения первого задания;");
                    System.out.println("SECOND => если хотите получить результат решения второго задания;");
                    System.out.println("THIRD => если хотите получить результат решения третьего задания;");
                    System.out.println("STOP => если хотите завершить работу программы.");
                    break;
                case ("FIRST"):
                    tasks.ExecuteFirstTask();
                    break;
                case ("SECOND"):
                    tasks.ExecuteSecondTask();
                    break;
                case ("THIRD"):
                    tasks.ExecuteThirdTask();
                    break;
                case ("STOP"):
                    System.out.println("Работа завершена!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Введена неверная команда! Попробуйте снова.");
                    break;
            }
        }
    }
}
