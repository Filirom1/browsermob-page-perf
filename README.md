HTML to HAR
===========
Return the Http Archive Specification of an HTML WebPage.


Usage in a shell
----------------
java -jar html2har-1.0.0.jar http://google.fr > har.txt


Usage as a Java API
-------------------
String har = new Html2Har().toHar("http://google.fr");
assert(har.contains("google")==true);


Maven repository
----------------
Add the following lines in your pom.xml

    <dependencies>
        ....
        <dependency>
            <groupId>org.html2har</groupId>
            <artifactId>html2har</artifactId>
            <version>1.0.0</version>
        </dependency>
        ....
    </dependencies>

    <repositories>
        <repository>
            <id>filirom1-repo</id>
            <url>https://Filirom1@github.com/Filirom1/filirom1-mvn-repo/raw/master/releases</url>
        </repository>
    </repositories>

Usage as a Groovy API
---------------------
@GrabResolver(name='filirom1-release', root='https://github.com/Filirom1/filirom1-mvn-repo/raw/master/releases')
@Grab(group='org.html2har', module='html2har', version='1.0.0')

import org.html2har.*

println new Html2Har().toHar("http://google.fr");


More info
---------
http://www.softwareishard.com/blog/firebug/http-archive-specification/
http://groups.google.com/group/http-archive-specification/web/har-1-2-spec?pli=1
