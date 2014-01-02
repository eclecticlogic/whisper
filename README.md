### What is Whisper?
Have you ever had something go wrong in your production server and had your Logback email appender immediately send you an email alert? You probably felt like a genius that you knew what caused the error before anyone else. But then you realize that you've have a database issue and every request results in an error. And just then a backend process launches and tries to connect to the database and starts spewing all kinds of errors. And all hell breaks loose because your email server is straining under the error email server - all 50,000 of them saying the same thing - some variation of "could not connect to database" from Hibernate, your DAO and service layer!

You wish you could stop the madness and that's where Whisper comes in. Whisper is an Appender funnel if you will. You configure a frequency above which error messages should be suppressed and Whisper will do just that. While suppression is on, Whisper will send you periodic emails letting you know which messages were suppressed and how many of them since the last digest. When things gets resolved and your error message frequency drops below your configured threshold, suppression is stopped and things resume as normal.

### What Logging frameworks does Whisper support?
Whisper currently supports Logback. We hope to release support for log4j and log4jv2 soon. With logback, if your messages are parameterized, Whisper will suppress messages that are the same but only differ based on parameters. This is one more reason to take advantage of sl4j's parameterized log messages.

### How do I get the JAR?
Whisper is available via Maven Central. TBD Details.

### How do I configure Whisper?
You can refer to the whisper-logback-sample.xml under src/sample/resources. Here is the gist of how to configure Whisper for use with Logback:
TBD configuration information

### What is the license?
Good old [Apache License](http://apache.org/licenses/LICENSE-2.0.html).

