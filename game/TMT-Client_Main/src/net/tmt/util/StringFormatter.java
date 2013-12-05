package net.tmt.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class StringFormatter {
	private static DecimalFormat	df_standart;
	private static DecimalFormat	df_extra;

	static {
		DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
		symbols.setDecimalSeparator('.');
		symbols.setGroupingSeparator('"');

		df_standart = new DecimalFormat("#0.00", symbols);
		df_extra = new DecimalFormat("#0.00", symbols);
		df_standart.setGroupingUsed(true);
		df_standart.setGroupingSize(3);
		df_extra.setGroupingUsed(true);
		df_extra.setGroupingSize(3);
	}

	public static String format(final double value) {
		return df_standart.format(value);
	}

	public static String format(final double value, final int integerDigit, final int fractionDigit) {
		df_extra.setMaximumFractionDigits(fractionDigit);
		df_extra.setMinimumFractionDigits(fractionDigit);

		df_extra.setMinimumIntegerDigits(integerDigit);
		return df_extra.format(value);
	}
}