using S22.Imap;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Net;
using System.Net.Mail;
using System.Text;
using System.IO;

namespace EmailReplyCs
{
    class Program
    {

        private const string imapHost = "imap.gmail.com"; // e.g. imap.gmail.com
        private const string imapUser = "ProjZesp2017@gmail.com";
        private const string imapPassword = "ProjZesp2017!";

        private const string smtpHost = "smtp.gmail.com"; // e.g. smpt.gmail.com
        private const string smtpUser = "ProjZesp2017@gmail.com";
        private const string smtpPassword = "ProjZesp2017!";

        private const string senderName = "Generator";

        private static List<String> data = new List<String>(); //user data
        private static List<String> pass = new List<String>(); //generated passwords
        private static Random rn = new Random();
        private static String generated = "";
        


        private static bool contains( int[] array, int v) {         //czy element jest w array
            if(array.Contains(v))
            {
                return true;
            }
            return false;
        }

        

        //isInList = intList.IndexOf(intVariable)


        private static String upper(String generated, int count)       //konwertowanie małych na duże
        {
            char[] generatedChars = new char[10];
            for (int i = 0; i < generated.Length; i++ )
            {
                generatedChars[i] = generated[i];
            }


            //char[] generatedChars = generated.toCharArray();        //konwertuj string na char
            int[] used = new int[count];
            int ch;
            //while array cant be filled with 0, so I putted any unused big integer here
            for (int j = 0; j < count; j++)
                used[j] = 999;
            for (int i = 0; i < count; i++)
            {
                ch = rn.Next(generatedChars.Length - 1 - 0 + 1) + 0;     //losuje liczbę z zakresu od 0 do długości char
                while (contains(used, ch))
                {
                    ch = rn.Next(generatedChars.Length - 1 - 0 + 1) + 0;     //sprawdzenie czy dana liczba jest duża
                }
                used[i] = ch;
                generatedChars[ch] = Char.ToUpper(generatedChars[ch]);     //zaznaczenie że znak użyty, powiększenie
            }
            //generated = String.valueOf(generatedChars);                     //konwert na string
            generated = "";
            //for (int i = 0; i < generatedChars.Length; i++)
            for (int i = 0; i < 10; i++)
            {
                generated = generated + generatedChars[i];
            }
            
            return generated;
        }


        private static String specchar(String generated, char c){
        int counter=0;
        int[] num=new int[50];
        char[] generatedChars = new char[50];
            for (int i = 0; i < generated.Length; i++ )
            {
                generatedChars[i] = generated[i];
            }        //konwert na char
        for(int i=0; i<generatedChars.Length; i++){             
            if (generatedChars[i]==c){
                num[counter]=i;                                 //przejście po liście, zapisuje index liter do zamiany
                counter++;
            }
        }
        if(counter>0) {
            int sel=0;
            if (counter > 1) {
                sel = rn.Next(counter - 1 - 0 + 1) + 0;      //rand zakres 0 do counter
            }
            switch (c) {
                case 'a':
                    generatedChars[num[sel]] = '@';             //zamiana znaku na specjalny
                    break;
                case 's':
                    generatedChars[num[sel]] = '$';             //^^
                    break;
            }
            generated = "";
            //for (int i = 0; i < generatedChars.Length; i++)
            for (int i = 0; i < 10; i++) 
            {
                generated = generated + generatedChars[i];
            }           //konwert na string
        }
        return generated;
        }



        private static void generate()
        {
            
            //String specialChars ="";
            String allData = "";                                         //przepisanie z daty do tego stringa
            int upperCount;
            bool ignore=true;
            bool specCase=true;
            int passLen;

            for (int i = 0; i < data.Count; i++ )
            {
                allData = allData + data[i];
            }
            allData = allData.Replace("\n", "");
            allData = allData.Replace("\r", "");
            allData = allData.Replace("\0", "");
            passLen=10;
            int c=0;
        
            //allData = allData.replaceAll("\\s+", "");           //usunięcie spacji
            allData = allData.ToLower();                      //na małe znaki
            
            //password generation
            while (generated.Length < 10) {
                //beginning of the string
                int start = rn.Next(allData.Length - 1 - 0 + 1) + 0;       //pobranie losowej liczby od 0 do długości stringa danych
                passLen = rn.Next(7 - 3 + 1) + 3;
                //check if data length from beginning point is enough to fill preferred length
                if (start + passLen < allData.Length) {                       //sprawdzenie długości hasła
                    for (int k = start; k <= start + passLen; k++) {
                        generated += allData[k];
                        if (generated.Length == 10)
                            break;
                    }
                } else {
                    //if not enough filling as much as possible and selecting beginning point again
                    for (int k = start; k < allData.Length; k++) {
                        generated += allData[k];
                        //if length is already ok
                        if (generated.Length == 10)
                            break;
                    }
                }
            }
        

        }

        
        private static IEnumerable<MailMessage> GetMessages()
        {
            using (ImapClient client = new ImapClient(imapHost, 993, true))
            {
                Console.WriteLine("Connected to " + imapHost + '.');

                // Login
                client.Login(imapUser, imapPassword, AuthMethod.Auto);
                Console.WriteLine("Authenticated.");

                // Get a collection of all unseen messages in the INBOX folder
                client.DefaultMailbox = "INBOX";
                IEnumerable<uint> uids = client.Search(
                    SearchCondition.Unseen().And(
                    SearchCondition.Subject("GeneratePassword"))
                    );

                if (uids.Count() == 0)
                    return null;

                return client.GetMessages(uids);
            }
        }
        
        private static MailMessage CreateReply(MailMessage source)
        {
            MailMessage reply = new MailMessage(new MailAddress(imapUser, "GeneratorHaseł2.0.11"), source.From);

            // Get message id and add 'In-Reply-To' header
            string id = source.Headers["Message-ID"];
            reply.Headers.Add("In-Reply-To", id);

            // Try to get 'References' header from the source and add it to the reply
            string references = source.Headers["References"];

            if (!string.IsNullOrEmpty(references))
                references += ' ';

            reply.Headers.Add("References", references + id);

            // Add subject
            if (!source.Subject.StartsWith("Re:", StringComparison.OrdinalIgnoreCase))
                reply.Subject = "Re: ";

            reply.Subject += source.Subject;

            // Add body
            StringBuilder body = new StringBuilder();
            if (source.IsBodyHtml)
            {

                if (!string.IsNullOrEmpty(source.Body))
                {
                    //Console.WriteLine(source.Body);
                    data.Add(source.Body);
                }
                generate();
                generated = specchar(generated, 'a');
                generated = specchar(generated, 's');
                generated = upper(generated, 3);



                body.Append("<p>Thank you for your email!</p>");
                body.Append("<p>Here is generated password.</p>");
                body.Append(generated);
                body.Append(senderName);
                body.Append("</p>");
                body.Append("<br>");
                body.Append("<div>");
               // if (source.Date().HasValue)
                if(true)
                    body.AppendFormat("On {0}, ", source.Date().Value.ToString(CultureInfo.InvariantCulture));

                if (!string.IsNullOrEmpty(source.From.DisplayName))
                    body.Append(source.From.DisplayName + ' ');



                //body.AppendFormat("<<a href=\"mailto:{0}\">{0}</a>> wrote:<br/>", source.From.Address);



               
            }
            else
            {
                //List<String> message = new List<String>();
                if (!string.IsNullOrEmpty(source.Body))
                {
                    data.Add(source.Body);
                }
                generate();

                
                generated = specchar(generated, 'a');
                generated = specchar(generated, 's');
                generated = upper(generated, 3);
                Console.WriteLine(generated);


                body.AppendLine("Thank you for your email!");
                body.AppendLine();
                body.AppendLine("Here is generated password.");
                body.AppendLine();
                body.AppendLine(generated);
                body.AppendLine();
                body.AppendLine("Best regards,");
                body.AppendLine(senderName);
                body.AppendLine();

                /*if (source.Date().HasValue)
                    body.AppendFormat("On {0}, ", source.Date().Value.ToString(CultureInfo.InvariantCulture));

                body.Append(source.From);
                body.AppendLine(" wrote:");*/

                
            }

            reply.Body = body.ToString();
            reply.IsBodyHtml = source.IsBodyHtml;

            return reply;
        }

        private static void SendReplies(IEnumerable<MailMessage> replies)
        {
            using (SmtpClient client = new SmtpClient(smtpHost, 587))
            {
                // Set SMTP client properties
                client.EnableSsl = true;
                client.UseDefaultCredentials = false;
                client.Credentials = new NetworkCredential(smtpUser, smtpPassword);

                // Send
                bool retry = true;
                foreach (MailMessage msg in replies)
                {
                    try
                    {
                        client.Send(msg);
                        retry = true;
                    }
                    catch (Exception ex)
                    {
                        if (!retry)
                        {
                            Console.WriteLine("Failed to send email reply to " + msg.To.ToString() + '.');
                            Console.WriteLine("Exception: " + ex.Message);
                            return;
                        }

                        retry = false;
                    }
                    finally
                    {
                        msg.Dispose();
                    }
                }

                Console.WriteLine("All email replies successfully sent.");
            }
        }

        static void Main(string[] args)
        {
            while (true)
            {
                // Download unread messages from the server
                IEnumerable<MailMessage> messages = GetMessages();
                if (messages != null)
                {
                    Console.WriteLine(messages.Count().ToString() + " new email message(s).");

                    // Create message replies
                    List<MailMessage> replies = new List<MailMessage>();
                    foreach (MailMessage msg in messages)
                    {
                        replies.Add(CreateReply(msg));
                        msg.Dispose();
                    }

                    // Send replies
                    SendReplies(replies);
                }
                else
                {
                    Console.WriteLine("No new email messages.");
                }
            }
            Console.WriteLine("Press any key to exit...");
            Console.ReadKey();
        }
    }
}
