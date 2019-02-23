
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MemberRepository;
import security.Authority;
import security.UserAccount;
import domain.Box;
import domain.Member;
import domain.SocialProfile;

@Service
@Transactional
public class MemberService {

	//Managed repository ---------------------------------

	@Autowired
	private MemberRepository	memberRepository;

	//Supporting services --------------------------------

	@Autowired
	private BoxService			boxService;

	@Autowired
	private ActorService		actorService;


	//Simple CRUD Methods --------------------------------

	public Member create() {
		final Authority a = new Authority();
		a.setAuthority(Authority.MEMBER);
		final UserAccount account = new UserAccount();
		account.setAuthorities(Arrays.asList(a));
		account.setBanned(false);

		final Member member = new Member();
		member.setSpammer(false);
		member.setSocialProfiles(new ArrayList<SocialProfile>());
		member.setUserAccount(account);
		member.setBoxes(new ArrayList<Box>());

		return member;
	}

	public Collection<Member> findAll() {
		return this.memberRepository.findAll();
	}

	public Member findOne(final int id) {
		Assert.notNull(id);

		return this.memberRepository.findOne(id);
	}

	public Member save(final Member member) {
		Assert.notNull(member);
		Member saved2;

		//TODO sobra un assert? este o el de checkAddress? Comprobar cuando esté en local todo ya.
		//Assertion to make sure the address is either null or written but not blank spaces.
		Assert.isTrue(!"\\s".equals(member.getAddress()) || member.getAddress() == null);

		//Assertion that the email is valid according to the checkAdminEmail method.
		Assert.isTrue(this.actorService.checkAdminEmail(member.getEmail()));

		//TODO checkear que si creamos un actor SIN address y luego creamos un objeto con ese actor, no peta al llamar a este save.
		//Assertion to check that the address isn't just a white space.
		Assert.isTrue(this.actorService.checkAddress(member.getAddress()));

		//Assertion that the phone is valid according to the checkPhone method.
		Assert.isTrue(this.actorService.checkPhone(member.getPhone()));

		//Assertion that the user modifying this member has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == member.getId());

		if (member.getId() != 0) {
			Assert.isTrue(this.actorService.findByPrincipal().getId() == member.getId());
			saved2 = this.memberRepository.save(member);
		} else {
			final Member saved = this.memberRepository.save(member);
			this.actorService.hashPassword(saved);
			saved.setBoxes(this.boxService.generateDefaultFolders(saved));
			saved2 = this.memberRepository.save(saved);
		}
		//TODO Crear método checkSpam
		//		this.actorService.checkSpam(saved2.getUserAccount().getUsername());
		//		this.actorService.checkSpam(saved2.getName());
		//		this.actorService.checkSpam(saved2.getMiddleName());
		//		this.actorService.checkSpam(saved2.getSurname());
		//		this.actorService.checkSpam(saved2.getAddress());
		//		this.actorService.checkSpam(saved2.getPhoto());
		//		this.actorService.checkSpam(saved2.getEmail());

		return saved2;
	}

	public void delete(final Member member) {
		Assert.notNull(member);

		//Assertion that the user deleting this member has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == member.getId());

		this.memberRepository.delete(member);
	}

	//Other methods

	//Returns the collection of spammer members.
	public Collection<Member> spammerMembers() {
		return this.memberRepository.spammerMembers();
	}

	//Returns the member related with a certain finder.
	public Member memberByFinder(final int id) {
		return this.memberRepository.memberByFinder(id);
	}

	//The average, the minimum, the maximum, and the standard deviation of the number of members per brotherhood.
	public Double[] minMaxAvgStddevMemberPerBrotherhood() {
		return this.memberRepository.minMaxAvgStddevMemberPerBrotherhood();
	}

	//The listing of members who have got at least 10% the maximum number of request to march accepted
	public Collection<Member> membersWithApprovedRequestsThanAvg() {
		return this.memberRepository.membersWithApprovedRequestsThanAvg();
	}

	//The listing of active members of a certain brotherhood
	public Collection<Member> activeMembersOfBrotherhood(final int id) {
		return this.memberRepository.activeMembersOfBrotherhood(id);
	}
}
