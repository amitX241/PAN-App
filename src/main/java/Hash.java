import java.util.ArrayList;
import java.util.Formatter;
import java.util.Optional;


public class Hash {
	static int ftable_size;
	final double DEFAULT_LOAD_FACTOR;
	FirstLevelHash FL_HashTable[];
	char fourthChar[] = { 'C', 'P', 'H', 'F', 'A', 'T', 'B', 'L', 'J', 'G' };

	Hash() {
		ftable_size = 10;
		DEFAULT_LOAD_FACTOR = 0.75;
		FL_HashTable = new FirstLevelHash[ftable_size];
		for (int i = 0; i < ftable_size; i++) {
			FL_HashTable[i] = new FirstLevelHash();
		}
	}

	void initializeHash(int h1) {

		FL_HashTable[h1].occupied = 0;

		for (int j = 0; j < FL_HashTable[h1].size; j++) {
			FL_HashTable[h1].SL_HashTable[j] = null;
		}
	}

	void initializeHash() {

		for (int i = 0; i < ftable_size; i++) {
			FL_HashTable[i].occupied = 0;
		}
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < FL_HashTable[i].size; j++) {
				FL_HashTable[i].SL_HashTable[j] = null;
			}
		}
	}

	int hash1(char ch) {
		int val = -1;
		switch (ch) {
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
		default:
			System.err.println("Invalid Pan number");
			System.exit(0);
		}
		return val;
	}

	int hash2(int h1, String pan) {
		int h2 = 0;
		for (int i = 0; i < pan.length(); i++) {
			h2 = h2 + (int) pan.charAt(i);
		}
		return h2 % FL_HashTable[h1].size;
	}

	void insert(Account account) {
		if (account == null) {
			return;
		}
		int h1, h2;
		h1 = hash1(account.Pan.charAt(3));
		h2 = hash2(h1, account.Pan);

		double loadFactor = (1.0 * FL_HashTable[h1].occupied) / FL_HashTable[h1].size;
		if (loadFactor > DEFAULT_LOAD_FACTOR) {
			System.out.println();
			System.out.println(loadFactor + " is greater than " + DEFAULT_LOAD_FACTOR);
			System.out.println("Therefore Rehashing will be done for " + fourthChar[h1]);
			extend_rehash(h1);
		}

		if (FL_HashTable[h1].SL_HashTable[h2] == null) {
			FL_HashTable[h1].SL_HashTable[h2] = account;
		} else {
			for (int j = 0; j < FL_HashTable[h1].size; j++) {
				int index = (h2 + j * j) % FL_HashTable[h1].size;
				if (FL_HashTable[h1].SL_HashTable[index] == null) {
					FL_HashTable[h1].SL_HashTable[index] = account;
					break;
				} else if ((FL_HashTable[h1].SL_HashTable[index].Pan).equals(account.Pan)) {
					if(!FL_HashTable[h1].SL_HashTable[index].Name.equals(account.Name)||
					   !FL_HashTable[h1].SL_HashTable[index].Place.equals(account.Place)) {
						System.err.println("Invalid Input file, file consist of duplicate pan numbers but under different Name or Place");
	                	System.exit(0);
					}
					System.out.println("Pan number and its details are already Present "
							+ FL_HashTable[h1].SL_HashTable[index].Pan);
					return;
				}
			}
		}
		FL_HashTable[h1].occupied++;
	}

	void addEntry(ArrayList<Account> accounts) {
		for (Account account : accounts) {
			insert(account);
		}

	}

	void extend_rehash(int i) {
		int old_table_size = FL_HashTable[i].size;
		FL_HashTable[i].size = FL_HashTable[i].size * 2;
		Account hashtable_temp[] = new Account[old_table_size];

		for (int j = 0; j < old_table_size; j++) {
			hashtable_temp[j] = FL_HashTable[i].SL_HashTable[j];
		}

		FL_HashTable[i].SL_HashTable = new Account[FL_HashTable[i].size];

		initializeHash(i);

		System.out.println("Size is changed from " + old_table_size + " to " + FL_HashTable[i].size + ".\n");

		for (int j = 0; j < old_table_size; j++) {
			insert(hashtable_temp[j]);
		}

	}

	void searchDetails(ArrayList<String> find_pans) {
		String fmt = "";
		for (String pan : find_pans) {
			boolean found = false;
			int h1 = hash1(pan.charAt(3));
			int h2 = hash2(h1, pan);
			if (FL_HashTable[h1].SL_HashTable[h2] == null){
				found = false;
			}
			else if (FL_HashTable[h1].SL_HashTable[h2].Pan.equals(pan)) {
				printPanFound(FL_HashTable[h1].SL_HashTable[h2], "");
				found = true;
			} 
			else {
				for (int j = 0; j < FL_HashTable[h1].size; j++) {
					int index = (h2 + j * j) % FL_HashTable[h1].size;
					if ((FL_HashTable[h1].SL_HashTable[index] != null)
							&& (FL_HashTable[h1].SL_HashTable[index].Pan.equals(pan))) {
						printPanFound(FL_HashTable[h1].SL_HashTable[index], "");
						found = true;
						break;
					}
				}
			}
			if (found == false) {
				printPanFound(new Account(pan, "", ""), "n't");
			}
		}

	}

	void printPanFound(Account account, String status) {
		String stm = "";
		if (status == "") {
			stm = "The entry " + account.Pan + " does" + status + " exists - " + account.Name + " " + account.Place
					+ "\n";
		} else {
			stm = "The entry " + account.Pan + " does" + status + " exists\n";
		}
		System.out.println(stm);
	}

	void hashtablePrint() {
		int elementNo = 1;
		Formatter fmt = new Formatter();
		System.out.println();
		System.out.println();
		fmt.format("%15s %15s %15s\n", "PAN", "Name", "Address");
		fmt.format("%15s %15s %15s\n", "---------------", "---------------", "---------------");
		for (int k = 0; k < ftable_size; k++) {
			for (int l = 0; l < FL_HashTable[k].size; l++) {
				if (FL_HashTable[k].SL_HashTable[l] != null) {
					fmt.format(elementNo++ + "%15s %15s %15s\n", FL_HashTable[k].SL_HashTable[l].Pan,
							FL_HashTable[k].SL_HashTable[l].Name, FL_HashTable[k].SL_HashTable[l].Place);
				} else {
					fmt.format("%15s %15s %15s\n", "***", "***", "***");
				}
			}
			fmt.format("%47s\n", "-----------------------------------------------");
		}
		System.out.println(fmt);
	}

	void printAccount(ArrayList<Account> accounts) {
		for (Account ac : accounts) {
			System.out.println(ac.Pan + "\n");
		}
	}
}
