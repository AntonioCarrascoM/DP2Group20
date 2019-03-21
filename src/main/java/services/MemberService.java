
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MemberRepository;
import security.Authority;
import security.UserAccount;
import domain.Box;
import domain.Enrolment;
import domain.Member;
import domain.Request;
import domain.SocialProfile;
import forms.FormObjectMember;

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

	@Autowired
	private Validator			validator;


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
		member.setEnrolments(new ArrayList<Enrolment>());
		member.setRequests(new ArrayList<Request>());
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

		//Assertion that the email is valid according to the checkAdminEmail method.
		Assert.isTrue(this.actorService.checkUserEmail(member.getEmail()));

		//Assertion to check that the address isn't just a white space.
		Assert.isTrue(this.actorService.checkAddress(member.getAddress()));

		//Assertion that the phone is valid according to the checkPhone method.
		Assert.isTrue(this.actorService.checkPhone(member.getPhone()));

		//Checking if the actor is bannable according to the "bannableActors" query.
		if (this.actorService.isBannable(member) == true)
			member.setSpammer(true);

		if (member.getId() != 0) {
			Assert.isTrue(this.actorService.findByPrincipal().getId() == member.getId());
			saved2 = this.memberRepository.save(member);
		} else {
			final Member saved = this.memberRepository.save(member);
			this.actorService.hashPassword(saved);
			saved.setBoxes(this.boxService.generateDefaultFolders(saved));
			saved2 = this.memberRepository.save(saved);
		}

		return saved2;
	}

	public void delete(final Member member) {
		Assert.notNull(member);

		//Assertion that the user deleting this member has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == member.getId());

		this.memberRepository.delete(member);
	}

	//Reconstruct

	public Member reconstruct(final FormObjectMember fom, final BindingResult binding) {
		final Member result = this.create();

		Assert.isTrue(fom.getAcceptedTerms());
		Assert.isTrue(fom.getPassword().equals(fom.getSecondPassword()));

		result.setName(fom.getName());
		result.setMiddleName(fom.getMiddleName());
		result.setSurname(fom.getSurname());
		result.setPhoto(fom.getPhoto());
		result.setEmail(fom.getEmail());
		result.setPhone(fom.getPhone());
		result.setAddress(fom.getAddress());
		result.getUserAccount().setUsername(fom.getUsername());
		result.getUserAccount().setPassword(fom.getPassword());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Assertion that the email is valid according to the checkAdminEmail method.
		Assert.isTrue(this.actorService.checkUserEmail(result.getEmail()));

		//Assertion to check that the address isn't just a white space.
		Assert.isTrue(this.actorService.checkAddress(result.getAddress()));

		//Assertion that the phone is valid according to the checkPhone method.
		Assert.isTrue(this.actorService.checkPhone(result.getPhone()));

		return result;

	}

	public Member reconstructPruned(final Member member, final BindingResult binding) {
		Member result;

		result = this.memberRepository.findOne(member.getId());

		result.setName(member.getName());
		result.setMiddleName(member.getMiddleName());
		result.setSurname(member.getSurname());
		result.setPhoto(member.getPhoto());
		result.setEmail(member.getEmail());
		result.setPhone(member.getPhone());
		result.setAddress(member.getAddress());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getId());

		//Assertion that the email is valid according to the checkAdminEmail method.
		Assert.isTrue(this.actorService.checkUserEmail(result.getEmail()));

		//Assertion to check that the address isn't just a white space.
		Assert.isTrue(this.actorService.checkAddress(result.getAddress()));

		//Assertion that the phone is valid according to the checkPhone method.
		Assert.isTrue(this.actorService.checkPhone(result.getPhone()));

		return result;

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

	public Collection<Member> enrolableMembersOfBrotherhood(final int id) {
		final Collection<Member> activeMembers = this.activeMembersOfBrotherhood(id), enrolableMembers = new ArrayList<>();
		for (final Member m : this.findAll())
			if (!activeMembers.contains(m))
				enrolableMembers.add(m);
		return enrolableMembers;

	}
}
