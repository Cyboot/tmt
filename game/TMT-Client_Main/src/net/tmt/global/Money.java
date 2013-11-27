package net.tmt.global;

public class Money {
	private static long	money;

	public static void addMoney(final long money) {
		Money.money += money;
	}

	public static void removeMoney(final long money) {
		Money.money -= money;
	}

	public static boolean hasMoney(final long money) {
		return Money.money >= money;
	}

	/**
	 * get the money as String. <br>
	 * user {@link Money#hasMoney(long)} to check for money
	 * 
	 * @return
	 */
	public static String getMoney() {
		return String.valueOf(money);
	}
}
