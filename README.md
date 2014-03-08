# What is Whisper?
Have you ever had something go wrong in your production server and had your Logback email appender immediately send you an email alert? You probably felt like a DevOps guru because you knew exactly what caused the error before anyone else. But then you realize that you've got a database issue and every user request from the app results in an error email (possibly more than one). Right at that time a backend process launches and tries to connect to the database and all hell breaks loose because your email server is now straining under the error email deluge - all 50,000 of them saying the same thing - some variation of "could not connect to database"!

Perhaps you weren't so fortunate to see this happen in real-time. Perhaps the first time you noticed it was when you got back into work and saw the 50,000 new emails awaiting your attention. And as you sit and delete page after page of email, you have this suspicion that perhaps somewhere in those 50,000 emails (all looking alike) there is a one-off error email that may be important and you are going to accidentally delete it.

Whisper is your solution to controlling error email spam. Whisper acts as a pass through appender to your default SMTP appender for emails.
When the frequency of a message exceeds the configured threshold, Whisper starts to suppress it. While suppression is on, Whisper will even send you periodic digests letting you know which messages were suppressed and how many of them were suppressed. When things gets resolved and your error message frequency drops, suppression is stopped and things resume as normal. All of this happens on a per log message basis (not including the timestamp and factoring away parameters if you used the parameterized SLF4j log format).

### What Logging frameworks does Whisper support?
Whisper currently supports Logback. We hope to add support for log4j and log4jv2 in the future.

### How do I get the JAR?
Whisper is available via Maven Central. 

```
<groupId>com.eclecticlogic</groupId>
<artifactId>whisper</artifactId>
<packaging>jar</packaging>
<version>1.0.0</version>
```	

### How do I configure Whisper?
You can refer to the whisper-logback-sample.xml under src/sample/resources. Here is the gist of how to configure Whisper for use with Logback to 
handle the most common case of suppressing ERROR messages that are sent via the email appender. 

To configure the Whisper appender, first you must configure two other appenders - the regular email appender for ERROR level logs and a second
email appender for sending the suppression Digests when suppression is actually in effect. 

```
<appender name="errorEmail" class="ch.qos.logback.classic.net.SMTPAppender">
	<filter class="ch.qos.logback.classic.filter.LevelFilter">
		<level>ERROR</level>
		<onMatch>ACCEPT</onMatch>
		<onMismatch>DENY</onMismatch>
	</filter>
	<smtpHost>ADDRESS-OF-YOUR-SMTP-HOST</smtpHost>
	<to>EMAIL-DESTINATION</to>
	<from>SENDER-EMAIL</from>
	<subject>TESTING: %logger{20} - %m</subject>
	<layout class="ch.qos.logback.classic.PatternLayout">
		<pattern>%date %-5level %logger{35} - %message%n</pattern>
	</layout>
</appender>

<appender name="errorDigest" class="ch.qos.logback.classic.net.SMTPAppender">
	<smtpHost>ADDRESS-OF-YOUR-SMTP-HOST</smtpHost>
	<to>EMAIL-DESTINATION</to>
	<from>SENDER-EMAIL</from>
	<subject>%X{whisper.digest.subject}</subject>
	<layout class="ch.qos.logback.classic.PatternLayout">
		<pattern>%date %-5level %logger{35} - %message%n</pattern>
	</layout>
</appender>
```

Note the use of `%X{whisper.digest.subject}` for the subject of the digest appender.


We now configure the Whisper appender as shown below:

```
<appender name="whisper"
	class="com.eclecticlogic.whisper.logback.WhisperAppender">
	<!-- Filter out non error logs -->
	<filter class="ch.qos.logback.classic.filter.LevelFilter">
		<level>ERROR</level>
		<onMatch>ACCEPT</onMatch>
		<onMismatch>DENY</onMismatch>
	</filter>
	<!-- This is the name of the logging category to use to send out error digests. This is associated with the 
	errorDigest appender. -->
	<digestLoggerName>digest.appender.logger</digestLoggerName>
	<!--  suppressAfter specifies the criteria to enter suppression. The example below says that if 3 errors of the same kind
	are encountered within a 5 minute window, then suppression should kick in. -->
	<suppressAfter>3 in 5 minutes</suppressAfter>
	<!-- expireAfter specifies how much of silence the logger between messages before stopping suppression. --> 
	<expireAfter>4 minutes</expireAfter>
	<!-- digestFrequency specifies how often error email digests should be sent containing statistics on messages 
	suppressed -->
	<digestFrequency>20 minutes</digestFrequency>
	
	<!-- The pass-through appender for the normal case when suppression is not in-force. -->
	<appender-ref ref="errorEmail" />
</appender>
```

The digest logger name is then associated with the digestAppender and the whisper appender is included in the 
list of default appenders:

```
<logger name="digest.appender.logger" level="error" additivity="false">
	<appender-ref ref="errorDigest" />
</logger>

<root level="debug">
	<appender-ref ref="whisper" />
	<appender-ref ref="fileAppender" />
</root>
```

### What is the license?
Good old [Apache License](http://apache.org/licenses/LICENSE-2.0.html).

# Release Notes

### v1.0.1

- Fixed issue (NPE on digest run) described [here](https://github.com/eclecticlogic/whisper/issues/1).


