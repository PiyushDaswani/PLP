package com.cg.tripplanner.view;
import java.io.FileOutputStream;
/*
 * Author - Piyush
 * Description - Class for formatting of the result in pdf
 */
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cg.tripplanner.dto.Booking;
import com.cg.tripplanner.dto.Hotel;
import com.cg.tripplanner.dto.HotelBooking;
import com.cg.tripplanner.dto.Transport;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFView{

	private static final Logger logger = LoggerFactory.getLogger(PDFView.class);

	public String GetPdf(Map<String, Object> model) throws Exception {
		try{
			Booking booking = (Booking) model.get("booking");
			Transport transport = booking.getBookingTransport();
			String FILE=System.getProperty("catalina.base")+ booking.getBookingTransport().getTransportName() +".pdf";
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(FILE));
			document.open();
			Paragraph header = new Paragraph(
					new Chunk("Ticket", FontFactory.getFont(FontFactory.HELVETICA, 30)));
			document.add(header);
			Paragraph transportName = new Paragraph(new Chunk(transport.getTransportMode() + " Number: " + transport.getTransportName()));
			Paragraph transportDetails = new Paragraph(new Chunk("Departure: "+ transport.getDepartureFrom() + "\t Arrival: " + transport.getArrivalAt()));
			Paragraph transportTiming = new Paragraph(new Chunk("Departure Date: " +booking.getBookingDate() + "\t Departure Timing: " + transport.getDepartureTime()));
			Paragraph transportCost = new Paragraph(new Chunk("Travel Duration: "+ transport.getTravelDuration() + "\t Travel Cost: " + transport.getTravelCost() ));
			Paragraph seats = new Paragraph(new Chunk("Number of Booked Seats: "+ booking.getBookingSeats() + "\t Travellers: " + booking.getBookedUserList()[0] ));
			
			document.add(transportName);
			document.add(transportDetails);
			document.add(transportTiming);
			document.add(transportCost);
			document.add(seats);
			document.close();
			return(FILE);
		}catch(ClassCastException e){
			logger.error(e.getMessage());
			return null;
		}
	}
	
	public String GetHotelPdf(Map<String, Object> model) throws Exception {
		try{
			Booking booking = (Booking) model.get("booking");
			HotelBooking hotelBooking = booking.getHotelBooking();
			Hotel hotel = hotelBooking.getBookHotel();
			String FILE=System.getProperty("catalina.base")+ booking.getBookingTransport().getTransportName() +".pdf";
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(FILE));
			document.open();
			Paragraph header = new Paragraph(
					new Chunk("Hote Booking", FontFactory.getFont(FontFactory.HELVETICA, 30)));
			document.add(header);
			Paragraph hotelName = new Paragraph(new Chunk("Hotel Name: " + hotel.getHotelName()));
			Paragraph transportDetails = new Paragraph(new Chunk("Check In: "+ hotelBooking.getCheckIn() + " Check Out: " + hotelBooking.getCheckOut()));
			Paragraph transportTiming = new Paragraph(new Chunk("Rooms Booked: " + hotelBooking.getRooms() + "\t Number of days stay " + hotelBooking.getDuration()));
			
			document.add(hotelName);
			document.add(transportDetails);
			document.add(transportTiming);
			document.close();
			return(FILE);
		}catch(ClassCastException e){
			logger.error(e.getMessage());
			return null;
		}
		
		
	}

}