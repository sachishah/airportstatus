package com.example.airportstatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Airport {
	String code;
	int index;
	
	public Airport(String iataCode, int index) {
		this.code = iataCode;
		this.index = index;
	}
	
	// need to add more airport codes
	// http://www.tsa.gov/data/apcp.xml
	// if you add an airport code to IATA_CODES you MUST
	// add its web site address to WEBSITES at the same index
	public static final LinkedHashMap<String, String> IATA_CODES = new LinkedHashMap<String, String>() {
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
		put("Mineta San Jose International - SJC", "SJC");
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
		    add("flychicago.com/midway/");
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
			add("hawaii.gov/hnl");
			add("fly2houston.com");
			add("fly2houston.com");
			add("indianapolisairport.com");
			add("flykci.com");
			add("mccarran.com");
			add("awa.org/welcomeLAX.aspx");
			add("mscaa.com");
			add("miami-airport.com");
			add("mspairport.com");
			add("flynashville.com");
			add("flymsy.com");
			add("panynj.gov/airports/jfk.html");
			add("panynj.gov/airports/laguardia.html");
			add("panynj.gov/airports/newark-liberty.html");
			add("flyoakland.com");
			add("lawa.org/welcomeont.aspx");
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
			add("metwashairports.com/dulles");
			add("metwashairports.com/reagan");
		    
		}};;
		
		public static final ArrayList<String[]> LOCATIONS = new ArrayList<String[]>() {
			{
			    String[] atl ={"33.63985","-84.4439"};
			    add(atl);
				String[] anc = {"61.1735","-149.98115"};
			    add(anc);
			    String[] aus = {"30.202568","-97.66808"};
			    add(aus);
			    String[] bwi = {"39.17688","-76.67002"};
			    add(bwi);
			    String[] bos = {"42.36657","-71.01696"};
			    add(bos);
			    String[] clt = {"35.22073","-80.94396"};
			    add(clt);
			    String[] mdw = {"41.78811","-87.74122"};
			    add(mdw);
			    String[] ord = {"41.976172","-87.903266"};
			    add(ord);
			    String[] cvg = {"39.0488367","-84.6678222"};
			    add(cvg);
			    String[] cle = {"41.4094167","-81.8549804"};
			    add(cle);
			    String[] cmh = {"39.9979722","-82.88464"};
			    add(cmh);
			    String[] dfw = {"32.896615","-97.0377"};
			    add(dfw);
			    String[] den = {"39.8494","-104.67372"};
			    add(den);
			    String[] dtw = {"42.2124444","-83.3533889"};
			    add(dtw);
			    String[] fll = {"26.0725833","-80.15275"};
			    add(fll);
			    String[] rsw = {"26.5361667","-81.7551667"};
			    add(rsw);
			    String[] bdl = {"41.9388889","-72.6832222"};
			    add(bdl);
			    String[] hnl = {"21.3186813","-157.9224287"};
			    add(hnl);
			    String[] iah = {"29.9844336","-95.341442"};
			    add(iah);
			    String[] hou = {"29.9844336","-95.3414422"};
			    add(hou);
			    String[] ind = {"39.714351","-86.29847"};
			    add(ind);
			    String[] mci = {"39.2975","-94.7162"};
			    add(mci);
			    String[] las = {"36.0800556","-115.15225"};
			    add(las);
			    String[] lax = {"33.942452","-118.40801"};
			    add(lax);
			    String[] mem = {"35.0424167","-89.9766667"};
			    add(mem);
			    String[] mia = {"25.79498","-80.27849"};
			    add(mia);
			    String[] msp = {"44.88105","-93.20286"};
			    add(msp);
			    String[] bna = {"36.1244722","-86.6781944"};
			    add(bna);
			    String[] msy = {"29.9849","90.2568"};
			    add(msy);
			    String[] jfk = {"40.64432","-73.78261"};
			    add(jfk);
			    String[] lga = {"40.77725","-73.8726111"};
			    add(lga);
			    String[] ewr = {"40.69055","-74.17761"};
			    add(ewr);
			    String[] oak = {"37.7213056","-122.2207222"};
			    add(oak);
			    String[] ont = {"34.056","-117.6011944"};
			    add(ont);
			    String[] mco = {"28.431374","-81.30839"};
			    add(mco);
			    String[] phl = {"39.8722494","-75.2408658"};
			    add(phl);
			    String[] phx = {"33.4347","-112.0094"};
			    add(phx);
			    String[] pit = {"40.4914722","-80.2328611"};
			    add(pit);
			    String[] pdx = {"45.5884557","-122.5974516"};
			    add(pdx);
			    String[] rdu = {"35.8776389","-78.7874722"};
			    add(rdu);
			    String[] smf = {"38.6954167","-121.5907778"};
			    add(smf);
			    String[] slc = {"40.7861","-111.98099"};
			    add(slc);
			    String[] sat = {"29.5336944","-98.4697778"};
			    add(sat);
			    String[] san = {"32.7335556","-117.1896667"};
			    add(san);
			    String[] sfo = {"37.61498","-122.38971"};
			    add(sfo);
			    String[] sjc = {"37.3626667","-121.9291111"};
			    add(sjc);
			    String[] sna = {"33.6756667","-117.8682222"};
			    add(sna);
			    String[] sea = {"47.44354","-122.30176"};
			    add(sea);
			    String[] stl = {"38.743","-90.3662"};
			    add(stl);
			    String[] tpa = {"27.9754722","-82.53325"};
			    add(tpa);
			    String[] iad = {"38.95313","-77.44743"};
			    add(iad);
			    String[] dca = {"38.849","-77.04133"};
			    add(dca);
			    
			}};;
	
	

}
