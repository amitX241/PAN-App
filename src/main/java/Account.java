import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.util.ArrayList;

public class Account{
    String Pan;
    String Name;
    String Place;
    ArrayList<Account> accounts;
    ArrayList<String> find_pans;

    Account(String pan, String name, String place){
        Pan   = pan;
        Name  = name;
        Place = place;
    }
    Account(){
        ArrayList<Account> accounts = new ArrayList<Account>();
        ArrayList<Account> find_accounts = new ArrayList<Account>();
    }
    
    boolean checkInput(ArrayList<String> accountDetails) {
    	if(accountDetails.size() != 3) {
    		return false;
    	}
    	if(accountDetails.get(0).length() != 10 || 
    	   accountDetails.get(1).length() > 30 ||
    	   accountDetails.get(2).length() > 50 ) {
    		return false;
    	}
    	for (char c : accountDetails.get(0).toCharArray()) {
    		if (!Character.isLetterOrDigit(c)) {
    			return false;
    		}
    	}
    	if(accountDetails.get(1).length() == 0 ||
    	   accountDetails.get(2).length() == 0 ) {
    	    System.out.println("Warning: Please donot leave Name or Place blank");
    	}
      
    	return true;
    }

    ArrayList<Account> initializeAccount(String filename){
        ArrayList<Account> accounts = new ArrayList<Account>();
        try{
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            while(myReader.hasNextLine()){
                ArrayList<String> accountDetail = new ArrayList<String>();
                String data = myReader.nextLine();
//                for handleing blank cases
                if(data.length() == 0) {
                	continue;
                }
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
                if(checkInput(accountDetail)) {
                	Account account = new Account(accountDetail.get(0), accountDetail.get(1), accountDetail.get(2));
                	accounts.add(account);
                }
                else {
                	System.err.println("The Input file consist of invalid entries please verify the file");
                	System.exit(0);
                }
                
            }
            myReader.close();
        }catch(FileNotFoundException e){
            System.out.println("Error with opening the file");
            e.printStackTrace();
        }
        return accounts;
    }

    ArrayList<String> initializeSearch(String filename){
    	ArrayList<String> pans = new ArrayList<String>();
        try{
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            while(myReader.hasNextLine()){
                String data = myReader.nextLine();
//              for handleing blank cases
                if(data.length() == 0) {
                	continue;
                }
                if(data.length() != 10) {
                	System.err.println("The Search file consist of invalid entries please verify the file " + data);
                	System.exit(0);
                }
                for (char c : data.toCharArray()) {
            		if (!Character.isLetterOrDigit(c)) {
            			System.err.println("The Search file consist of invalid entries please verify the file " + data);
                    	System.exit(0);
            		}
            	}
                pans.add(data);                
            }
            myReader.close();
        }catch(FileNotFoundException e){
            System.out.println("Error with opening the file");
            e.printStackTrace();
        }
        return pans;
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
