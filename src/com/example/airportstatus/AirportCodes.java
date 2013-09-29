package com.example.airportstatus;

import java.util.ArrayList;
import java.util.HashMap;

public final class AirportCodes {
	
	//need to add more airport codes
	//http://www.tsa.gov/data/apcp.xml
	//if you add an airport code to IATA_CODES you MUST
	//add its website address to WEBSITES at the same index
	public static final HashMap<String, String> IATA_CODES = new HashMap<String, String>() {
	{
	    put("Hartsfield-Jackson Atlanta International - ATL", "ATL");
	    put("Ted Stevens Anchorage International Airport - ANC", "ANC");
	    put("Austin-Bergstrom International - AUS", "AUS");
	    put("Baltimore/Washington International - BWI", "BWI");
	    put("Logan International - BOS", "BOS");
	    put("Charlotte Douglas International - CLT", "CLT");
	    put("Chicago Midway Airport - MDW", "MDW");
	    put("Chicago O'Hare International - ORD", "ORD");
	    put("Cincinnati/Northern Kentucky International - CVG", "CVG");
		put("Cleveland Hopkins International - CLE", "CLE");
		put("Port Columbus International - CMH", "CMH");
		put("Dallas/Ft. Worth International - DFW Airport",	"DFW");
		put("Denver International Airport - DEN", "DEN");
		put("Detroit Metropolitan Wayne County Airport - DTW", "DTW");
		put("Fort Lauderdale/Hollywood International - FLL", "FLL");
		put("Southwest Florida International - RSW", "RSW");
		put("Bradley International - BDL", "BDL");
		put("Hawaii Honolulu International - HNL", "HNL");
		put("George Bush Intercontinental - IAH", "IAH");
		put("William P. Hobby Airport - HOU", "HOU");
		put("Indianapolis International - IND", "IND");
		put("Kansas City International - MCI", "MCI");
		put("McCarran International - LAS", "LAS");
		put("Los Angeles International - LAX Airport", "LAX");
		put("Memphis International - MEM", "MEM");
		put("Miami International Airport - MIA", "MIA");
		put("Minneapolis/St. Paul International - MSP", "MSP");
		put("Nashville International - BNA", "BNA");
		put("Louis Armstrong International - MSY", "MSY");
		put("John F. Kennedy International - JFK", "JFK");
		put("LaGuardia International - LGA", "LGA");
		put("Newark Liberty International - EWR", "EWR");
		put("Metropolitan Oakland International - OAK", "OAK");
		put("Ontario International - ONT", "ONT");
		put("Orlando International - MCO", "MCO");
		put("Philadelphia International - PHL", "PHL");
		put("Sky Harbor International - PHX", "PHX");
		put("Pittsburgh International - PIT", "PIT");
		put("Portland International - PDX", "PDX");
		put("Raleigh-Durham International - RDU", "RDU");
		put("Sacramento International - SMF", "SMF");
		put("Salt Lake City International - SLC", "SLC");
		put("San Antonio International - SAT", "SAT");
		put("Lindbergh Field International - SAN", "SAN");
		put("San Francisco International - SFO", "SFO");
		put("Mineta San José International - SJC", "SJC");
		put("John Wayne Airport, Orange County - SNA", "SNA");
		put("Seattle-Tacoma International - Seatac Airport - SEA", "SEA");
		put("Lambert-St. Louis International - STL", "STL");
		put("Tampa International - TPA", "TPA");
		put("Dulles International Airport - IAD", "IAD");
		put("Ronald Reagan Washington National - DCA", "DCA");
	    
	}};;
	
	public static final ArrayList<String> WEBSITES = new ArrayList<String>() {
		{
		    add("atlanta-airport.com");
		    add("dot.alaska.gov/anc");
		    add("austintexas.gov/airport");
		    add("bwiairport.com");
		    add("massport.com");
		    add("charlotteairport.com");
		    add("flychicago.com/midway‎");
		    add("flychicago.com/ohare");
		    add("cvgairport.com");
			add("clevelandairport.com");
			add("flycolumbus.com");
			add("dfwairport.com");
			add("flydenver.com");
			add("metroairport.com");
			add("fll.net");
			add("flylcpa.com");
			add("bradleyairport.com");
			add("hawaii.gov/hnl‎");
			add("fly2houston.com");
			add("fly2houston.com");
			add("indianapolisairport.com");
			add("flykci.com");
			add("mccarran.com");
			add("awa.org/welcomeLAX.aspx‎");
			add("mscaa.com");
			add("miami-airport.com");
			add("mspairport.com");
			add("flynashville.com");
			add("flymsy.com");
			add("panynj.gov/airports/jfk.html‎");
			add("panynj.gov/airports/laguardia.html‎");
			add("panynj.gov/airports/newark-liberty.html‎");
			add("flyoakland.com");
			add("lawa.org/welcomeont.aspx‎");
			add("orlandoairports.net");
			add("phl.org");
			add("skyharbor.com");
			add("flypittsburgh.com");
			add("pdx.com");
			add("rdu.com");
			add("sacramento.aero/smf");
			add("slcairport.com");
			add("sanantonio.gov");
			add("san.org");
			add("flysfo.com");
			add("flysanjose.com");
			add("ocair.com");
			add("portseattle.org/Sea-Tac");
			add("flystl.com");
			add("tampaairport.com");
			add("metwashairports.com/dulles‎");
			add("metwashairports.com/reagan");
		    
		}};;
	
	

}
