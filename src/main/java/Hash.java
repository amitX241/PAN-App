import java.util.ArrayList;
import java.util.Formatter;
import java.util.Optional;

class FirstHash {
	int size;
	int occupied;
	Account[] hashTable;

	public FirstHash() {
		size = 13;
		hashTable = new Account[size];
		occupied = 0;
	}
}

public class Hash {
	static int ftable_size;
	final double DEFAULT_LOAD_FACTOR;
	FirstHash firstHash[];
	char fourthChar[] = { 'C', 'P', 'H', 'F', 'A', 'T', 'B', 'L', 'J', 'G' };

	Hash() {
		ftable_size = 10;
		DEFAULT_LOAD_FACTOR = 0.75;
		firstHash = new FirstHash[ftable_size];
		for (int i = 0; i < ftable_size; i++) {
			firstHash[i] = new FirstHash();
		}
	}

	void initializeHash(int h1) {

		firstHash[h1].occupied = 0;

		for (int j = 0; j < firstHash[h1].size; j++) {
			firstHash[h1].hashTable[j] = null;
		}
	}

	void initializeHash() {

		for (int i = 0; i < ftable_size; i++) {
			firstHash[i].occupied = 0;
		}
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < firstHash[i].size; j++) {
				firstHash[i].hashTable[j] = null;
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
		}
		return val;
	}

	int hash2(int h1, String pan) {
		int h2 = 0;
		for (int i = 0; i < pan.length(); i++) {
			h2 = h2 + (int) pan.charAt(i);
		}
		return h2 % firstHash[h1].size;
	}

	void insert(Account account) {
		if (account == null) {
			return;
		}
		int h1, h2;
		h1 = hash1(account.Pan.charAt(3));
		h2 = hash2(h1, account.Pan);

		double loadFactor = (1.0 * firstHash[h1].occupied) / firstHash[h1].size;
		if (loadFactor > DEFAULT_LOAD_FACTOR) {
			System.out.println();
			System.out.println(loadFactor + " is greater than " + DEFAULT_LOAD_FACTOR);
			System.out.println("Therefore Rehashing will be done for " + fourthChar[h1]);
			extend_rehash(h1);
		}

		if (firstHash[h1].hashTable[h2] == null) {
			firstHash[h1].hashTable[h2] = account;
		} else {
			for (int j = 0; j < firstHash[h1].size; j++) {
				int index = (h2 + j * j) % firstHash[h1].size;
				if (firstHash[h1].hashTable[index] == null) {
					firstHash[h1].hashTable[index] = account;
					break;
				} else if ((firstHash[h1].hashTable[index].Pan).equals(account.Pan)) {
					System.out.println(
							"Pan number and its details are already Present " + firstHash[h1].hashTable[index].Pan);
					return;
				}
			}
		}
		firstHash[h1].occupied++;
	}

	void addEntry(ArrayList<Account> accounts) {
		for (Account account : accounts) {
			insert(account);
		}

	}

	void extend_rehash(int i) {
		int old_table_size = firstHash[i].size;
		firstHash[i].size = firstHash[i].size * 2;
		Account hashtable_temp[] = new Account[old_table_size];

		for (int j = 0; j < old_table_size; j++) {
			hashtable_temp[j] = firstHash[i].hashTable[j];
		}

		firstHash[i].hashTable = new Account[firstHash[i].size];

		initializeHash(i);

		System.out.println("Size is changed from " + old_table_size + " to " + firstHash[i].size + ".\n");

		for (int j = 0; j < old_table_size; j++) {
			insert(hashtable_temp[j]);
		}

	}

	void searchDetails(ArrayList<Account> find_accounts) {

		Formatter fmt = new Formatter();
		fmt.format("%15s %15s %15s %15s\n", "PAN", "Name", "Address", "Status");
		fmt.format("%15s %15s %15s %15s\n", "---------------", "---------------", "---------------", "---------------");
		for (Account find_account : find_accounts) {
			boolean found = false;
			int h1 = hash1(find_account.Pan.charAt(3));
			int h2 = hash2(h1, find_account.Pan);
			if ((firstHash[h1].hashTable[h2] != null) && (firstHash[h1].hashTable[h2].Pan.equals(find_account.Pan))) {
				fmt = printAccountDetails(find_account, "Found", fmt);
				found = true;
			} else {
				for (int j = 0; j < firstHash[h1].size; j++) {
					int index = (h2 + j * j) % firstHash[h1].size;
					if ((firstHash[h1].hashTable[index] != null)
							&& (firstHash[h1].hashTable[index].Pan.equals(find_account.Pan))) {
						fmt = printAccountDetails(find_account, "Found", fmt);
						found = true;
						break;
					}
				}
			}
			if (found == false) {
				fmt = printAccountDetails(find_account, "Not Found", fmt);
			}
		}
		System.out.println(fmt);

	}

	Formatter printAccountDetails(Account account, String status, Formatter fmt) {
		fmt.format("%15s %15s %15s %15s\n", account.Pan, account.Name, account.Place, status);
		return fmt;
	}

	void hashtablePrint() {
		int elementNo = 1;
		Formatter fmt = new Formatter();
		System.out.println();
		System.out.println();
		fmt.format("%15s %15s %15s\n", "PAN", "Name", "Address");
		fmt.format("%15s %15s %15s\n", "---------------", "---------------", "---------------");
		for (int k = 0; k < ftable_size; k++) {
			for (int l = 0; l < firstHash[k].size; l++) {
				if (firstHash[k].hashTable[l] != null) {
					fmt.format(elementNo++ + "%15s %15s %15s\n", firstHash[k].hashTable[l].Pan,
							firstHash[k].hashTable[l].Name, firstHash[k].hashTable[l].Place);
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
