package com.stockmanagementsystem.service;

import com.stockmanagementsystem.entity.*;
import com.stockmanagementsystem.repository.*;
import com.stockmanagementsystem.repository.EmailsRepository;
import com.stockmanagementsystem.utils.EmailConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CreateAndSendEmailServiceImp implements CreateAndSendEmailService {
	@Autowired
	private EmailsRepository emailsRepository;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmailSender emailSender;

	@Autowired
	LoginUser loginUser;
	@Autowired
	private EmailTemplateRepository emailTemplateRepository;
	@Autowired
	private EmailDocumentRepository emailDocumentRepository;

	@Autowired
	private ASNHeadRepository asnHeadRepository;

// TODO Email delivery switches According to Type of message...........->
	@Async
	@Override
	public String createEmail(List<Email> emails) {
		try {
			log.info("createSendEmail ------{}", "createSendEmail started");


			for (Email email : emails) {
				switch (email.getType()) {

					case EmailConstant.CREATE_USER:
						createUser(email.getType(), email.getToUser(), email.getSubjectLine(), email.getId(), email.getAttemptCount(),email.getSubTypeName(),email.getLoginUrl());
					break;
				case EmailConstant.ASN_POST:
					asnPost( email);
					break;
				case EmailConstant.ADD_ADMIN:
					createAdmin(email.getType(), email.getToUser(), email.getSubjectLine(), email.getId(), email.getAttemptCount(),email.getSubTypeName(),email.getLoginUrl());
					break;
//				case EmailConstant.APPROVAL_REQUEST:
//					approvalRequest(EmailConstant.APPROVAL_REQUEST,  email.getToUser(), email.getSubjectLine(),email.getId(),email.getAttemptCount(),email.getSubType(),email.getSubTypeName());
//					break;
//				case EmailConstant.PENDING_TASK:
//					pendingTasks(EmailConstant.PENDING_TASK, email.getToUser(), email.getSubjectLine(),email.getId(),email.getAttemptCount());
//					break;
//				case EmailConstant.MILESTONE_ENDED:
//					milestoneMissed(EmailConstant.MILESTONE_ENDED,  email.getToUser(), email.getSubjectLine(),email.getId(),email.getAttemptCount());
//					break;
//				case EmailConstant.MILESTONE_START:
//					milestoneEnding(EmailConstant.MILESTONE_START,  email.getToUser(), email.getSubjectLine(),email.getId(),email.getAttemptCount() );
//					break;
				default:
					break;
				}
			}
			log.info("createSendEmail ------{}", "createSendEmail executed");
		} catch (Exception e) {

			e.printStackTrace();
			log.error("createSendEmail ------{}", e.getMessage());
		}
		return "send successfully";

	}
//TODO this method send email for only pending tasks..........->
	@Override
	public Boolean generateOtp(String type, int userId, String subject, int emailId, int count, String otp) {
		try {
			log.info("pendingTasks ------{}", "pendingTasks started");
			Optional<Users> user = this.userRepository.findByIsDeletedAndId(false,userId);
			EmailTemplate emailTemplate=emailTemplateRepository.findBytemplateTitle(type);
			String html=emailTemplate.getTemplateContent();
			html=html.replaceAll("%O%",String.valueOf(otp.charAt(0)));
			html=html.replaceAll("%T%",String.valueOf(otp.charAt(1)));
			html=html.replaceAll("%P%",String.valueOf(otp.charAt(2)));
			html=html.replaceAll("%S%",String.valueOf(otp.charAt(3)));
			html=html.replaceAll("%N%",String.valueOf(otp.charAt(4)));
			html=html.replaceAll("%Q%",String.valueOf(otp.charAt(5)));
			html=html.replaceAll("%NAME%",(user.get().getFirstName()+" "+user.get().getLastName()));
			html=html.replaceAll("%USERNAME%",user.get().getUsername());
			return emailSender.sendMail(subject,html,user.get().getEmailId(),1);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("pendingTasks ------{}", e.getMessage());
			return false;
		}


	}
	public Boolean createUser(String type, int userId, String subject, int emailId, int count,String pwd,String loginUrl){
		try {
			Boolean status=false;
			log.info("CreateUserEmail ------{}", "CreateUserEmail started");
			Optional<Users> user = this.userRepository.findByIsDeletedAndId(false,userId);
			EmailTemplate emailTemplate=emailTemplateRepository.findBytemplateTitle(type);
			String html=emailTemplate.getTemplateContent();
			html=html.replaceAll("%NAME%",(user.get().getFirstName()+" "+user.get().getLastName()));
			html=html.replaceAll("%USERNAME%",user.get().getUsername());
			html=html.replaceAll("%PWD%",pwd);
			html=html.replaceAll("%LOGINURL%",loginUrl);
			status=emailSender.sendMail(subject,html,user.get().getEmailId(),1);
			if(status){
				emailsRepository.deleteById(emailId);
			}else{
				Optional<Email> email=emailsRepository.findById(emailId);
				count=count++;
				email.get().setAttemptCount(count);
				emailsRepository.save(email.get());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("CreateUserEmail ------{}", e.getMessage());
			return false;
		}
		return true;
	}


	public Boolean asnPost(Email email){
		try {
			Boolean status=false;
			Integer count=0;
			log.info("ASNPOSTEmail ------{}", "ASNPOSTEmail started");
			Optional<Users> user = this.userRepository.findByIsDeletedAndId(false,email.getToUser());
			EmailTemplate emailTemplate=emailTemplateRepository.findBytemplateTitle(email.getType());
			ASNHead asnHead=asnHeadRepository.findByIsDeletedAndId(false,email.getSubType());
			Optional<Users> buyer=this.userRepository.findByIsDeletedAndId(false,asnHead.getCreatedBy());
			String html=emailTemplate.getTemplateContent();
			html=html.replaceAll("%NAME%",(user.get().getFirstName()+" "+user.get().getLastName()));
			html=html.replaceAll("%ASN%","ASN Instrument");
			html=html.replaceAll("%ASNNUMBER%",asnHead.getAsnNumber());
			html=html.replaceAll("%DUEDATE%",asnHead.getDeliveryDate().toString());
			html=html.replaceAll("%TIME%",asnHead.getDeliveryTime().toString());
			html=html.replaceAll("%PO%",asnHead.getPurchaseOrderHead().getPurchaseOrderNumber());
			html=html.replaceAll("%DATE%",asnHead.getPurchaseOrderHead().getPurchaseOrderDate().toString());
			html=html.replaceAll("%ORGNAME%",email.getSubTypeName());
			html=html.replaceAll("%ADDRESS%",buyer.get().getSubOrganization().getDistrict());
			html=html.replaceAll("%BUYER%",buyer.get().getFirstName()+" "+buyer.get().getLastName());
			html=html.replaceAll("%BUYERMOB%",buyer.get().getMobileNo());

			html=html.replaceAll("%LOGINURL%",email.getLoginUrl());
			status=emailSender.sendMail(email.getSubjectLine(),html,user.get().getEmailId(),1);
			if(status){
				emailsRepository.deleteById(email.getId());
			}else{
				Optional<Email> emailL=emailsRepository.findById(email.getId());
				count=count++;
				emailL.get().setAttemptCount(count);
				emailsRepository.save(emailL.get());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("ASNPOSTEmail ------{}", e.getMessage());
			return false;
		}
		log.info("ASNPOSTEmail ------{}", "ASNPOSTEmail Execute");
		return true;
	}
	public Boolean createAdmin(String type, int userId, String subject, int emailId, int count,String pwd,String loginUrl){
		try {
			Boolean status=false;
			log.info("CreateUserEmail ------{}", "CreateUserEmail started");
			Optional<Users> user = this.userRepository.findByIsDeletedAndId(false,userId);
			EmailTemplate emailTemplate=emailTemplateRepository.findBytemplateTitle(type);
			String html=emailTemplate.getTemplateContent();
			html=html.replaceAll("%NAME%",(user.get().getFirstName()+" "+user.get().getLastName()));
			html=html.replaceAll("%USERNAME%",user.get().getUsername());
			html=html.replaceAll("%PWD%",pwd);
//			html=html.replaceAll("%LOGINURL%",loginUrl);
            EmailDocument emailDocument=emailDocumentRepository.findByEmailId(emailId);
			emailDocument.getData();
			status=emailSender.sendMail(subject,html,user.get().getEmailId(),1);
			if(status){
				emailsRepository.deleteById(emailId);
			}else{
				Optional<Email> email=emailsRepository.findById(emailId);
				count=count++;
				email.get().setAttemptCount(count);
				emailsRepository.save(email.get());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("CreateUserEmail ------{}", e.getMessage());
			return false;
		}
		return true;
	}

}
