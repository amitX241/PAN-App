import java.lang.module.FindException;
import java.util.Scanner;

public class PanMain {
    public static void main(String[] args) {

        Account ac = new Account();
        Hash hash = new Hash();

        while (true) {
            System.out.println("Operations Menu");
            System.out.println("1. Take Input");
            System.out.println("2. Search For Information");
            System.out.println("3. Display All PAN Information");
            System.out.println("4. Quit");
            System.out.print("Please Enter your Choice: ");

            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Taking Input...");
                    ac.accounts = ac.initializeAccount("src/main/data/accountfile.txt");

                    hash.initializeHash();
                    hash.addEntry(ac.accounts);

                    System.out.println("Data Added to Hash!");
                    System.out.println("");
                    break;

                case 2:
                    System.out.println("Searching for Information...");
                    ac.find_accounts = ac.initializeAccount("src/main/data/findaccountfile.txt");
                    hash.searchDetails(ac.find_accounts);
                    System.out.println("Search Finished!");
                    System.out.println("");
                    break;

                case 3:
                    System.out.println("Displaying Information...");
                    hash.hashtablePrint();
                    break;

                case 4:
                    System.out.println("Quiting...");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Please Enter a valid choice!");
                    continue;
            }


        }

    }
}
