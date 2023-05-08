import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.util.ArrayList;

public class Account{
    String Pan;
    String Name;
    String Place;
    ArrayList<Account> accounts;
    ArrayList<Account> find_accounts;

    Account(String pan, String name, String place){
        Pan   = pan;
        Name  = name;
        Place = place;
    }
    Account(){
        ArrayList<Account> accounts = new ArrayList<Account>();
        ArrayList<Account> find_accounts = new ArrayList<Account>();
    }

    ArrayList<Account> initializeAccount(String filename){
        ArrayList<Account> accounts = new ArrayList<Account>();
        try{
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            while(myReader.hasNextLine()){
                ArrayList<String> accountDetail = new ArrayList<String>();
                String data = myReader.nextLine();
                //remember to handle the case where the empty lines are present after the data
                String part = "";
                for(int i=0 ; i<data.length() ; i++){
                    if(data.charAt(i) == ','){
                        accountDetail.add(part);
                        part = "";
                        continue;
                    }
                    part = part + data.charAt(i);
                }
                accountDetail.add(part);
                Account account = new Account(accountDetail.get(0), accountDetail.get(1), accountDetail.get(2));
                accounts.add(account);
            }
            myReader.close();
        }catch(FileNotFoundException e){
            System.out.println("Error with opening the file");
            e.printStackTrace();
        }
        return accounts;
    }

    void accountDetailsPrint(ArrayList<Account> accounts){
        for(Account account : accounts){
            System.out.println(account.Pan + "  " + account.Name + "  " + account.Place);
        }

        System.out.println();
        System.out.println();
        System.out.println();
    }

}
