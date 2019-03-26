
package controllers.actor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import security.Authority;
import services.ActorService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import domain.Actor;
import domain.Administrator;
import domain.Brotherhood;
import domain.Chapter;
import domain.Member;
import domain.Sponsor;

@Controller
@RequestMapping("/download")
public class DownloadController {

	@Autowired
	ActorService				actorService;

	private static final String	APPLICATION_PDF	= "application/pdf";


	@RequestMapping(value = "/myPersonalData", method = RequestMethod.GET, produces = DownloadController.APPLICATION_PDF)
	public @ResponseBody
	void downloadMyPersonalData(final HttpServletResponse response) throws IOException, DocumentException {

		final File file = new File("MyPersonalData.pdf");
		final FileOutputStream fileout = new FileOutputStream(file);
		final Document document = new Document();
		PdfWriter.getInstance(document, fileout);
		document.addAuthor("Actor");
		document.addTitle("My personal data");
		document.open();

		final Actor principal = this.actorService.findByPrincipal();

		final Brotherhood brotherhood = new Brotherhood();
		final Authority authBrotherhood = new Authority();
		authBrotherhood.setAuthority(Authority.BROTHERHOOD);

		final Chapter chapter = new Chapter();
		final Authority authChapter = new Authority();
		authChapter.setAuthority(Authority.CHAPTER);

		final Sponsor sponsor = new Sponsor();
		final Authority authSponsor = new Authority();
		authSponsor.setAuthority(Authority.SPONSOR);

		final Administrator admin = new Administrator();
		final Authority authAdmin = new Authority();
		authAdmin.setAuthority(Authority.ADMIN);

		final Member member = new Member();
		final Authority authMember = new Authority();
		authMember.setAuthority(Authority.MEMBER);

		final ObjectMapper mapper = new ObjectMapper();

		final Paragraph paragraph = new Paragraph();
		paragraph.add(principal.toString());
		paragraph.setAlignment(Element.ALIGN_LEFT);
		document.add(paragraph);

		if (principal != null)
			if (principal.getUserAccount().getAuthorities().contains(authBrotherhood)) {
				final Brotherhood brotherhoodPrincipal = (Brotherhood) this.actorService.findByPrincipal();
				brotherhood.setName(brotherhoodPrincipal.getName());
				brotherhood.setMiddleName(brotherhoodPrincipal.getMiddleName());
				brotherhood.setSurname(brotherhoodPrincipal.getSurname());
				brotherhood.setPhoto(brotherhoodPrincipal.getPhoto());
				brotherhood.setEmail(brotherhoodPrincipal.getEmail());
				brotherhood.setPhone(brotherhoodPrincipal.getPhone());
				brotherhood.setAddress(brotherhoodPrincipal.getAddress());
				brotherhood.setTitle(brotherhoodPrincipal.getTitle());
				brotherhood.setEstablishmentDate(brotherhoodPrincipal.getEstablishmentDate());
				brotherhood.setPictures(brotherhoodPrincipal.getPictures());
				final String json = mapper.writeValueAsString(brotherhood);
				paragraph.add(json);
			} else if (principal.getUserAccount().getAuthorities().contains(authChapter)) {
				final Chapter chapterPrincipal = (Chapter) this.actorService.findByPrincipal();
				chapter.setName(chapterPrincipal.getName());
				chapter.setMiddleName(chapterPrincipal.getMiddleName());
				chapter.setSurname(chapterPrincipal.getSurname());
				chapter.setPhoto(chapterPrincipal.getPhoto());
				chapter.setEmail(chapterPrincipal.getEmail());
				chapter.setPhone(chapterPrincipal.getPhone());
				chapter.setAddress(chapterPrincipal.getAddress());
				chapter.setTitle(chapterPrincipal.getTitle());
				final String json = mapper.writeValueAsString(chapter);
				paragraph.add(json);
			} else if (principal.getUserAccount().getAuthorities().contains(authSponsor)) {
				final Sponsor sponsorPrincipal = (Sponsor) this.actorService.findByPrincipal();
				sponsor.setName(sponsorPrincipal.getName());
				sponsor.setMiddleName(sponsorPrincipal.getMiddleName());
				sponsor.setSurname(sponsorPrincipal.getSurname());
				sponsor.setPhoto(sponsorPrincipal.getPhoto());
				sponsor.setEmail(sponsorPrincipal.getEmail());
				sponsor.setPhone(sponsorPrincipal.getPhone());
				sponsor.setAddress(sponsorPrincipal.getAddress());
				final String json = mapper.writeValueAsString(sponsor);
				paragraph.add(json);
			} else if (principal.getUserAccount().getAuthorities().contains(authAdmin)) {
				final Administrator adminPrincipal = (Administrator) this.actorService.findByPrincipal();
				admin.setName(adminPrincipal.getName());
				admin.setMiddleName(adminPrincipal.getMiddleName());
				admin.setSurname(adminPrincipal.getSurname());
				admin.setPhoto(adminPrincipal.getPhoto());
				admin.setEmail(adminPrincipal.getEmail());
				admin.setPhone(adminPrincipal.getPhone());
				admin.setAddress(adminPrincipal.getAddress());
				final String json = mapper.writeValueAsString(admin);
				paragraph.add(json);
			} else if (principal.getUserAccount().getAuthorities().contains(authMember)) {
				final Member memberPrincipal = (Member) this.actorService.findByPrincipal();
				member.setName(memberPrincipal.getName());
				member.setMiddleName(memberPrincipal.getMiddleName());
				member.setSurname(memberPrincipal.getSurname());
				member.setPhoto(memberPrincipal.getPhoto());
				member.setEmail(memberPrincipal.getEmail());
				member.setPhone(memberPrincipal.getPhone());
				member.setAddress(memberPrincipal.getAddress());
				final String json = mapper.writeValueAsString(member);
				paragraph.add(json);
			}

		document.add(paragraph);
		document.close();

		final InputStream in = new FileInputStream(file);

		response.setContentType(DownloadController.APPLICATION_PDF);
		response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
		response.setHeader("Content-Length", String.valueOf(file.length()));

		FileCopyUtils.copy(in, response.getOutputStream());

	}

}
