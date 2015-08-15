package com.schedfox.dashboard.bootstrapper.util;

public class DashboardConstants {

	public enum MaxPercentageType {
		ACCOUNT ("account"),
		EMPLOYEE ("employee");
		
		private String type;
		
		private MaxPercentageType(String type) {
			
			this.type = type;
		}

		public String getType() {
			return type;
		}
	}
}
