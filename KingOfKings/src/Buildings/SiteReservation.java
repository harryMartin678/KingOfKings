package Buildings;

public class SiteReservation extends Building {

	public SiteReservation(int buildingNo) {
		super(buildingNo);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return Names.RESERVATION;
	}

}
