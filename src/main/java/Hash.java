import java.util.ArrayList;
import java.util.Formatter;

public class Hash {
    static int ftable_size;
    static int table_size;
    static Account hashtable[][];
    final double DEFAULT_LOAD_FACTOR;
    static int[] hash_size;

    Hash(){
        ftable_size = 10;
        table_size = 13;
        hashtable = new Account[ftable_size][table_size];
        hash_size = new int[ftable_size];
        DEFAULT_LOAD_FACTOR = 0.75;
    }

    void initializeHash(){
        for(int i=0; i<ftable_size ; i++){
            hash_size[i] = 0;
        }
        for(int i=0 ; i<10 ; i++){
            for(int j=0 ; j<table_size ; j++){
                hashtable[i][j] = null;
            }
        }
    }

    void addEntry(ArrayList<Account> accounts){

        for(Account account : accounts){
            insert(account);
        }

    }
    void insert(Account account){
        if(account == null){
            return;
        }
        int h1, h2;
        h1 = hash1(account.Pan.charAt(3));
        h2 = hash2(account.Pan);
        if(hashtable[h1][h2] == null){
            hashtable[h1][h2] = account;
        }
        else{
            for(int j=0 ; j<table_size ; j++){
                int index = (h2 + j * j) % table_size;
                if(hashtable[h1][index]  == null){
                    hashtable[h1][index] = account;
                    break;
                }
                else if((hashtable[h1][index].Pan).equals(account.Pan)){
                    System.out.println("Pan number and its details are already Present " + hashtable[h1][index].Pan);
                    return;
                }
            }
        }
        hash_size[h1]++;
        double loadFactor = ( 1.0 * hash_size[h1] )/table_size;
        if(loadFactor > DEFAULT_LOAD_FACTOR){
            System.out.println();
            System.out.println(loadFactor + " is greater than " + DEFAULT_LOAD_FACTOR);
            System.out.println("Therefore Rehashing will be done.\n");
            extend_rehash();
        }

    }
    int hash1(char ch){
        int val = -1;
        switch(ch){
            case 'C':
                val = 0;
                break;
            case 'P':
                val = 1;
                break;
            case 'H':
                val = 2;
                break;
            case 'F':
                val = 3;
                break;
            case 'A':
                val = 4;
                break;
            case 'T':
                val = 5;
                break;
            case 'B':
                val = 6;
                break;
            case 'L':
                val = 7;
                break;
            case 'J':
                val = 8;
                break;
            case 'G':
                val = 9;
                break;
        }
        return val;
    }
    int hash2(String pan){
        int h2 = 0;
        for(int i=0 ; i < pan.length() ; i++){
            h2 = h2 + (int) pan.charAt(i);
        }
        return h2 % table_size;
    }

    void searchDetails(ArrayList<Account> find_accounts){

        Formatter fmt = new Formatter();
        fmt.format("%15s %15s %15s %15s\n", "PAN", "Name", "Address", "Status");
        fmt.format("%15s %15s %15s %15s\n", "---------------", "---------------", "---------------", "---------------");
        for(Account find_account : find_accounts){
            boolean found = false;
            int h1 = hash1(find_account.Pan.charAt(3));
            int h2 = hash2(find_account.Pan);
            if((hashtable[h1][h2] != null) && (hashtable[h1][h2].Pan.equals(find_account.Pan))){
                fmt = printAccountDetails(find_account, "Found", fmt);
                found = true;
            }
            else{
                for(int j=0 ; j<table_size ; j++){
                    int index = (h2 + j * j) % table_size;
                    if((hashtable[h1][index]  != null) && (hashtable[h1][index].Pan.equals(find_account.Pan))){
                        fmt = printAccountDetails(find_account, "Found", fmt);
                        found = true;
                        break;
                    }
                }
            }
            if(found == false){
                fmt = printAccountDetails(find_account, "Not Found", fmt);
            }
        }
        System.out.println(fmt);

    }
    void extend_rehash(){
        int old_table_size = table_size;
        table_size = table_size * 2;
        Account hashtable_temp[][] = new Account[ftable_size][old_table_size];
        for(int i=0; i<ftable_size ; i++){
            for(int j=0 ; j<old_table_size ; j++){
                hashtable_temp[i][j] = hashtable[i][j];
            }
        }
        hashtable = new Account[ftable_size][table_size];
        initializeHash();
        for(int i=0; i<ftable_size ; i++){
            for(int j=0 ; j<old_table_size ; j++){
                insert(hashtable_temp[i][j]);
            }
        }
    }
//    void print_found(Account account){
//        System.out.println(account.Name + "  " + account.Pan + "  " + account.Place + "  - Found");
//    }
//
//    void not_print_found(Account account){
//        System.out.println(account.Name + "  " + account.Pan + "  " + account.Place + "  - Not Found");
//    }
    
    Formatter printAccountDetails(Account account, String status, Formatter fmt) {
        fmt.format("%15s %15s %15s %15s\n", account.Pan, account.Name, account.Place, status);
        return fmt;
    }

    void hashtablePrint() {
        Formatter fmt = new Formatter();
        System.out.println();
        System.out.println();
        fmt.format("%15s %15s %15s\n", "PAN", "Name", "Address");
        fmt.format("%15s %15s %15s\n", "---------------", "---------------", "---------------");
        for(int k = 0; k < ftable_size ; k++){
            for(int l = 0; l < table_size; l++){
                if(hashtable[k][l] != null){
                    fmt.format("%15s %15s %15s\n", hashtable[k][l].Pan, hashtable[k][l].Name, hashtable[k][l].Place);
                }
                else{
                    fmt.format("%15s %15s %15s\n", "***", "***", "***");
                }
            }
            fmt.format("%47s\n", "-----------------------------------------------");
        }
        System.out.println(fmt);
    }

}