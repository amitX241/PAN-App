public class FirstLevelHash {
	int size;
	int occupied;
	Account[] SL_HashTable;

	public FirstLevelHash() {
		size = 13;
		SL_HashTable = new Account[size];
		occupied = 0;
	}
}