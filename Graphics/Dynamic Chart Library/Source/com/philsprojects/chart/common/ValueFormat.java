package com.philsprojects.chart.common;

import java.text.DecimalFormat;

public abstract class ValueFormat
{

	public static ValueFormat Percent0 = new ValueFormat() {
		public String format(double value) {
			return (int)value + "%";
		}
	};
	public static ValueFormat Percent1 = new ValueFormat() {
		private DecimalFormat df = new DecimalFormat("0.0\\%");
		public String format(double value) {
			return df.format(value);
		}
	};
	public static ValueFormat Percent2 = new ValueFormat() {
		private DecimalFormat df = new DecimalFormat("0.00%");
		public String format(double value) {
			return df.format(value * 0.01);
		}
	};
	public static ValueFormat Money = new ValueFormat() {
		private DecimalFormat df = new DecimalFormat("0.00$");
		public String format(double value) {
			return df.format(value * 0.01);
		}
	};

	public static ValueFormat Raw = new ValueFormat() {
		public String format(double value) {
			return String.valueOf(value);
		}
	};

	public static ValueFormat Decimal0 = new ValueFormat() {
		public String format(double value) {
			return String.valueOf((int)value);
		}
	};
	public static ValueFormat Decimal1 = new ValueFormat() {
		private DecimalFormat df = new DecimalFormat("0.0");
		public String format(double value) {
			return df.format(value);
		}
	};
	public static ValueFormat Decimal2 = new ValueFormat() {
		private DecimalFormat df = new DecimalFormat("0.00");
		public String format(double value) {
			return df.format(value);
		}
	};
	public static ValueFormat Decimal3 = new ValueFormat() {
		private DecimalFormat df = new DecimalFormat("0.000");
		public String format(double value) {
			return df.format(value);
		}
	};
	public static ValueFormat Decimal4 = new ValueFormat() {
		private DecimalFormat df = new DecimalFormat("0.0000");
		public String format(double value) {
			return df.format(value);
		}
	};
	public static ValueFormat Decimal5 = new ValueFormat() {
		private DecimalFormat df = new DecimalFormat("0.00000");
		public String format(double value) {
			return df.format(value);
		}
	};


	public abstract String format(double value);


}
