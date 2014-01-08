### What is Whisper?
Have you ever had something go wrong in your production server and had your Logback email appender immediately send you an email alert? You probably felt like a genius that you knew what caused the error before anyone else. But then you realize that you've have a database issue and every request results in an error. And just then a backend process launches and tries to connect to the database and starts spewing all kinds of errors. And all hell breaks loose because your email server is straining under the error email server - all 50,000 of them saying the same thing - some variation of "could not connect to database" from Hibernate, your DAO and service layer!

You wish you could stop the madness and that's where Whisper comes in. Whisper is an Appender funnel if you will. You configure a frequency above which error messages should be suppressed and Whisper will do just that. While suppression is on, Whisper will send you periodic emails letting you know which messages were suppressed and how many of them since the last digest. When things gets resolved and your error message frequency drops below your configured threshold, suppression is stopped and things resume as normal.

### What Logging frameworks does Whisper support?
Whisper currently supports Logback. We hope to release support for log4j and log4jv2 soon. With logback, if your messages are parameterized, Whisper will suppress messages that are the same but only differ based on parameters. This is one more reason to take advantage of sl4j's parameterized log messages.

### How do I get the JAR?
Whisper is available via Maven Central. TBD Details.

### How do I configure Whisper?
You can refer to the whisper-logback-sample.xml under src/sample/resources. Here is the gist of how to configure Whisper for use with Logback to 
handle the most common case of suppressing ERROR logs that are sent via the email appender. 

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
	<!-- expireAfter specifies how much of silence the logger should see for the error message being suppressed 
	before stopping suppression. --> 
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

