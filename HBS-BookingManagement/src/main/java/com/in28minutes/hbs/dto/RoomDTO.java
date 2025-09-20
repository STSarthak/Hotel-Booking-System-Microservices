package com.in28minutes.hbs.dto;

public class RoomDTO {

		private int roomId; 
		
		private String roomType;
		
		private double pricePerNight;
		
		private boolean availabilityStatus;
		
		private int hotelId;
		
		private int numberOfRoomsAvailable;


		public int getRoomId() {
			return roomId;
		}

		public void setRoomId(int roomId) {
			this.roomId = roomId;
		}

		public String getRoomType() {
			return roomType;
		}

		public void setRoomType(String roomType) {
			this.roomType = roomType;
		}

		public double getPricePerNight() {
			return pricePerNight;
		}

		public void setPricePerNight(double pricePerNight) {
			this.pricePerNight = pricePerNight;
		}

		public boolean isAvailabilityStatus() {
			return availabilityStatus;
		}

		public void setAvailabilityStatus(boolean availabilityStatus) {
			this.availabilityStatus = availabilityStatus;
		}

		public int getHotelId() {
			return hotelId;
		}

		public void setHotelId(int hotelId) {
			this.hotelId = hotelId;
		}
		
		public int getNumberOfRoomsAvailable() {
			return numberOfRoomsAvailable;
		}

		public void setNumberOfRoomsAvailable(int numberOfRoomsAvailable) {
			this.numberOfRoomsAvailable = numberOfRoomsAvailable;
		}


		@Override
		public String toString() {
			return "Room [roomId=" + roomId + ", " + (roomType != null ? "roomType=" + roomType + ", " : "")
					+ "pricePerNight=" + pricePerNight + ", availabilityStatus=" + availabilityStatus + ", hotelId="
					+ hotelId + "]";
		}
		
		
}
