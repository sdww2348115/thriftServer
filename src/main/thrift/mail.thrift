namespace java com.sdww8591.service.mail
struct Mail {
    1:string title,
    2:string text,
    3:string recipient
}

service MailService {
    bool smtpMail(1:Mail mail)
}