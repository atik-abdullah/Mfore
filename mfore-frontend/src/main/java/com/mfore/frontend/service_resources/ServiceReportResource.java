package com.mfore.frontend.service_resources;

import io.dropwizard.jersey.sessions.Session;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.PathIterator;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSFloat;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationMarkup;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationRubberStamp;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationTextMarkup;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDCheckbox;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextbox;
import org.apache.pdfbox.pdmodel.interactive.form.PDVariableText;
import org.skife.jdbi.v2.DBI;

import com.mfore.core.dao.EnrollmentDAO;
import com.mfore.core.dao.MnyrsSpecificDetailDAO;
import com.mfore.core.dao.PersonContactDetailDAO;
import com.mfore.core.dao.PersonDAO;
import com.mfore.core.dao.UserDAO;
import com.mfore.core.representations.Enrollment;
import com.mfore.core.representations.MnyrsSpecificDetail;
import com.mfore.core.representations.Person;
import com.mfore.core.representations.PersonContactDetail;
import com.mfore.core.representations.User;
import com.mfore.frontend.utilities.Footer;
import com.mfore.frontend.utilities.HexPDF;

@Path("/report")
@Produces(MediaType.APPLICATION_JSON)
public class ServiceReportResource {

	private PersonContactDetailDAO person_contact_detail_dao = null;
	private UserDAO userDao= null;
	private PersonDAO personDao = null;
	private MnyrsSpecificDetailDAO mnyrsSpecificDetailDao = null;
	private EnrollmentDAO enrollmentDao = null;
	private PDPageContentStream contentStream;
	private PDFont font;
	PDRectangle rect;
	int line = 0;

	public ServiceReportResource(DBI jdbi) {
		this.person_contact_detail_dao = jdbi.onDemand(PersonContactDetailDAO.class);
		this.personDao = jdbi.onDemand(PersonDAO.class);
		this.userDao = jdbi.onDemand(UserDAO.class);		
		this.mnyrsSpecificDetailDao = jdbi.onDemand(MnyrsSpecificDetailDAO.class);
		this.enrollmentDao = jdbi.onDemand(EnrollmentDAO.class);

	}

	@GET
	@Path("persondetail")
	public Response personDetail (@Session HttpSession session)  {

		// Get the user record that matches user_name
		User user = userDao.getUserByName((String) session.getAttribute("user_name"));

		// Get the person record where there user_id field matches with the retrieved user's pk "id"
		Person person = personDao.getPersonByUserId(user.getUser_id());

		return Response
				.ok(person)
				.build();
	}

	@GET
	@Path("contactdetail")
	public Response contactDetail (@Session HttpSession session)  {

		// Get the user record that matches user_name
		User user = userDao.getUserByName((String) session.getAttribute("user_name"));

		// Get the person record where there user_id field matches with the retrieved user's pk "id"
		Person person = personDao.getPersonByUserId(user.getUser_id());

		PersonContactDetail person_contact_detail = person_contact_detail_dao.getPersonContactDetailById(person.getFk_person_contact_detail_id());
		return Response
				.ok(person_contact_detail)
				.build();
	}

	@GET
	@Path("mnyrsdetail")
	public Response mnyrsDetail (@Session HttpSession session)  {

		// Get the user record that matches user_name
		User user = userDao.getUserByName((String) session.getAttribute("user_name"));

		// Get the person record where there user_id field matches with the retrieved user's pk "id"
		Person person = personDao.getPersonByUserId(user.getUser_id());

		MnyrsSpecificDetail mnyrs_specific_detail = mnyrsSpecificDetailDao.getMnyrsSpecificDetailByPersonId(person.getPerson_id());
		return Response
				.ok(mnyrs_specific_detail)
				.build();
	}

	@GET
	@Path("enrolledservices")
	public Response enrolledServices (@Session HttpSession session)  {

		// Get the user record that matches user_name
		User user = userDao.getUserByName((String) session.getAttribute("user_name"));

		// Get the person record where there user_id field matches with the retrieved user's pk "id"
		Person person = personDao.getPersonByUserId(user.getUser_id());

		List<Enrollment> enrollment= enrollmentDao.getEnrollmentById(person.getPerson_id());
		return Response
				.ok(enrollment)
				.build();
	}


	@GET
	@Path("downloadreport")
	@Produces("application/pdf")
	public Response downloadReport(@Session HttpSession session) throws IOException, COSVisitorException { 

		// Get the user record that matches user_name
		User user = userDao.getUserByName((String) session.getAttribute("user_name"));

		// Get the person record where there user_id field matches with the retrieved user's pk "id"
		Person person = personDao.getPersonByUserId(user.getUser_id());

		PersonContactDetail person_contact_detail = person_contact_detail_dao.getPersonContactDetailById(person.getFk_person_contact_detail_id());

		MnyrsSpecificDetail mnyrs_specific_detail = mnyrsSpecificDetailDao.getMnyrsSpecificDetailByPersonId(person.getPerson_id());
		/*
		// Create a document and add a page to it
    	PDDocument document = new PDDocument();
    	PDPage page = new PDPage(PDPage.PAGE_SIZE_A4);
        this.rect = page.getMediaBox();
    	document.addPage( page );

    	// Create a new font object selecting one of the PDF base fonts
    	this.font = PDType1Font.HELVETICA_BOLD;

    	// Start a new content stream which will "hold" the to be created content
    	this.contentStream = new PDPageContentStream(document, page);

    	// Define a text content stream using the selected font, moving the cursor and drawing the text "Hello World"

    	this.doIt("Person Contact Detail");
    	this.doIt("First Name :" + person.getFirst_name());
    	this.doIt( "Last Name :" + person.getLast_name());
    	this.doIt("Email :" + person_contact_detail.getEmail());
    	this.doIt("Phone :" + person_contact_detail.getPhone());
    	this.doIt("Street Address :" + person_contact_detail.getStreet_address());
    	this.doIt("City :" + person_contact_detail.getCity());
    	this.doIt("Postal Code :" + person_contact_detail.getPostal_code());
    	this.doIt("Country : "+ person_contact_detail.getCountry());
    	this.doIt("Mnyrs Specific Detail");
    	this.doIt("Blood Group :" + mnyrs_specific_detail.getBlood_group());
    	this.doIt("Gender :" + mnyrs_specific_detail.getGender());
    	this.doIt("Height :" + mnyrs_specific_detail.getHeight());
    	this.doIt("Weight :" + mnyrs_specific_detail.getWeight());

    	// Make sure that the content stream is closed:
    	this.contentStream.close();

		ByteArrayOutputStream tempZipOutstream = new ByteArrayOutputStream();

    	// Save the results and ensure that the document is properly closed:
    	document.save(tempZipOutstream);
    	document.close();
		ResponseBuilder response = Response.ok(tempZipOutstream.toByteArray());
		response.header("Content-Disposition", "attachment; filename=\"" + "Mnyrs Report.pdf" + "\"");
		return response.build();*/


		// Create a new document and include a default footer
		HexPDF doc = new HexPDF();
		doc.setFooter(Footer.defaultFooter);
		ByteArrayOutputStream tempZipOutstream = new ByteArrayOutputStream();



		// Add a main title, centered in shiny colours
		doc.title1Style();
		doc.setTextColor(new Color(0x2020ff));
		doc.drawText("Person Contact Detail" + "\n\n", HexPDF.CENTER);
		doc.setTextColor(Color.black);
		doc.title2Style();
		doc.drawText("First Name :\t" + person.getFirst_name() + "\n\n", HexPDF.LEFT);
		doc.drawText("Last Name :\t" + person.getLast_name() + "\n\n", HexPDF.LEFT);
		doc.drawText("Email :\t" + person_contact_detail.getEmail() + "\n\n", HexPDF.LEFT);
		doc.drawText("Phone :\t" + person_contact_detail.getPhone() + "\n\n", HexPDF.LEFT);
		doc.drawText("Street Address :\t" + person_contact_detail.getStreet_address() + "\n\n", HexPDF.LEFT);
		doc.drawText("City :\t" + person_contact_detail.getCity() + "\n\n", HexPDF.LEFT);
		doc.drawText("Postal Code :\t" + person_contact_detail.getPostal_code() + "\n\n", HexPDF.LEFT);
		doc.drawText("Country : \t"+ person_contact_detail.getCountry() + "\n\n", HexPDF.LEFT);
		doc.title1Style();
		doc.setTextColor(new Color(0x2020ff));
		doc.drawText("Mnyrs Specific Detail \t"+ "\n\n", HexPDF.CENTER);
		doc.setTextColor(Color.black);
		doc.title2Style();
		doc.drawText("Blood Group : \t" + mnyrs_specific_detail.getBlood_group() + "\n\n", HexPDF.LEFT);
		doc.drawText("Gender : \t" + mnyrs_specific_detail.getGender() + "\n\n", HexPDF.LEFT);
		doc.drawText("Height :\t" + mnyrs_specific_detail.getHeight() + "\n\n", HexPDF.LEFT);
		doc.drawText("Weight : \t" + mnyrs_specific_detail.getWeight() + "\n\n", HexPDF.LEFT);

		doc.finish(tempZipOutstream);

		ResponseBuilder response = Response.ok(tempZipOutstream.toByteArray());
		response.header("Content-Disposition", "attachment; filename=\"" + "Mnyrs Report.pdf" + "\"");
		return response.build();
	}

	void doIt(String message) throws IOException, COSVisitorException{

		this.contentStream.beginText();
		this.contentStream.setFont( font, 12 );
		this.contentStream.moveTextPositionByAmount( 100,  rect.getHeight() - 50*(++this.line) );
		this.contentStream.drawString( message );
		this.contentStream.endText();
	}


	@GET
	@Path("testpdf")
	@Produces("application/pdf")
	public void testpdf(@Session HttpSession session) throws IOException, COSVisitorException {

				  	PDDocument document = new PDDocument();
		  	PDFont font = PDType1Font.HELVETICA_BOLD;
		  	PDPage page = new PDPage();
		    document.addPage( page );



		    // create simple text
		   PDPageContentStream contentStream = new PDPageContentStream( document, page );
		    contentStream.beginText();
		    contentStream.setFont( font, 12 );
		    contentStream.moveTextPositionByAmount( 100, 700 );
		    contentStream.drawString( "Hello World" );
		    contentStream.endText();
		    contentStream.close();

		    PDAcroForm acroForm = new PDAcroForm( document );


		    document.getDocumentCatalog().setAcroForm( acroForm );

		    COSDictionary dic = new COSDictionary();
		    PDCheckbox textField = new PDCheckbox( acroForm, dic );
		    COSDictionary textFieldDict = textField.getDictionary();
		    textFieldDict.setName( COSName.FT, "Tx" );
		    PDRectangle rect = new PDRectangle();
		    rect.setLowerLeftX( ( float ) 88.14579772949219 );
		    rect.setLowerLeftY( ( float ) 394.781005859375 );
		    rect.setUpperRightX( ( float ) 232.9340057373047 );
		    rect.setUpperRightY( ( float ) 416.5929870605469 );
		    textField.getWidget().setRectangle( rect );
		    textField.setPartialName( "Text1" );

		    PDAnnotationRubberStamp rs = new PDAnnotationRubberStamp();
		    rs.setName(PDAnnotationRubberStamp.NAME_TOP_SECRET);
		    rs.setRectangle(new PDRectangle(100,100));
		    rs.setContents("A top secret note");
		    //page.getAnnotations().add( rs );

		    
		    PDAnnotationWidget aw = new PDAnnotationWidget();
		    aw.setAnnotationName("Weird");		    
		    aw.setRectangle(new PDRectangle(100,100));
		    aw.setContents("A top secret note");
		    //page.getAnnotations().add( aw );


		    PDAnnotationTextMarkup atm = new PDAnnotationTextMarkup(PDAnnotationTextMarkup.SUB_TYPE_POLYGON);
		    atm.setAnnotationName("Weird");		    
		    atm.setRectangle(new PDRectangle(100,100));
		    atm.setContents("A top secret note");
		    atm.setQuadPoints( new float[0] );
		    page.getAnnotations().add( atm );
		    
		    //page.getAnnotations().add( textField.getWidget() );

		    
		    /*PDRectangle rectangle = new PDRectangle();
	        rectangle.setLowerLeftX(88);
	        rectangle.setLowerLeftY(394);
	        rectangle.setUpperRightX(232);
	        rectangle.setUpperRightY(416);
	    page.setMediaBox(rectangle);
	    page.setCropBox(rectangle);*/
	    
		    //acroForm.getFields().add( textField );

		    COSString fieldValue = new COSString("Awesome field value");


		    		/*PDDocument doc = new PDDocument();
		PDPage page = new PDPage();
		doc.addPage(page);

		PDPageContentStream stream = new PDPageContentStream(doc,page);
		stream.setStrokingColor(Color.BLACK);
		stream.setNonStrokingColor(Color.RED);
		stream.addRect(100, 100, 300, 300);
		stream.addRect(150, 150, 200, 200);
		stream.fill(PathIterator.WIND_EVEN_ODD);
		stream.close();");
		    textField.getDictionary().setItem(COSName.V, fieldValue);*/
		
		document.save( "/Users/abdullahatik/Desktop/test.pdf" );
		document.close();
	}




	private Map< String, PDType1Font > createFontMap()
	{
		Map< String, PDType1Font > fonts = new HashMap< String, PDType1Font >();

		fonts.put( PDType1Font.TIMES_ROMAN.getBaseFont(), PDType1Font.TIMES_ROMAN );
		fonts.put( PDType1Font.TIMES_BOLD.getBaseFont(), PDType1Font.TIMES_BOLD );
		fonts.put( PDType1Font.TIMES_ITALIC.getBaseFont(), PDType1Font.TIMES_ITALIC );
		fonts.put( PDType1Font.TIMES_BOLD_ITALIC.getBaseFont(), PDType1Font.TIMES_BOLD_ITALIC
				);
		fonts.put( PDType1Font.HELVETICA.getBaseFont(), PDType1Font.HELVETICA );
		fonts.put( PDType1Font.HELVETICA_BOLD.getBaseFont(), PDType1Font.HELVETICA_BOLD );
		fonts.put( PDType1Font.HELVETICA_OBLIQUE.getBaseFont(), PDType1Font.HELVETICA_OBLIQUE
				);
		fonts.put( PDType1Font.HELVETICA_BOLD_OBLIQUE.getBaseFont(), PDType1Font.HELVETICA_BOLD_OBLIQUE
				);
		fonts.put( PDType1Font.COURIER.getBaseFont(), PDType1Font.COURIER );
		fonts.put( PDType1Font.COURIER_BOLD.getBaseFont(), PDType1Font.COURIER_BOLD );
		fonts.put( PDType1Font.COURIER_OBLIQUE.getBaseFont(), PDType1Font.COURIER_OBLIQUE );
		fonts.put( PDType1Font.COURIER_BOLD_OBLIQUE.getBaseFont(), PDType1Font.COURIER_BOLD_OBLIQUE
				);
		fonts.put( PDType1Font.SYMBOL.getBaseFont(), PDType1Font.SYMBOL );
		fonts.put( PDType1Font.ZAPF_DINGBATS.getBaseFont(), PDType1Font.ZAPF_DINGBATS );

		return fonts;
	}
}
