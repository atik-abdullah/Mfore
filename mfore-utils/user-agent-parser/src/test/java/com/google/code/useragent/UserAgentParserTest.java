package com.google.code.useragent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * Basic test cases for common browser strings.
 *
 * @author Glen Smith (glen@bytecode.com.au)
 */
public class UserAgentParserTest {

    @Test
    @Ignore
    public void testCanonicalBrowserList() throws IOException {
        // From http://www.pgts.com.au/download/data/browser_list.txt
        BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/browser_list.txt")));
        String nextLine;
        int success = 0, fail = 0;
        while ((nextLine = br.readLine()) != null) {
            String[] components = nextLine.split("\\t");
            String browser = components[0];
            String version = components[1];
            String header = components[2];
            try {
                UserAgentParser uap = new UserAgentParser(header);
                assertEquals(browser, uap.getBrowserName());
                //assertEquals(version, uap.getBrowserVersion());
                success++;
            } catch (Exception e) {
                System.err.println("Failed to parse agent: [" + header + "] which " +
                        "should be browser [" + browser + "] version: [" + version + "]");
                fail++;
            }
        }
        System.err.println("Success: " + success + " Fail: " + fail);
    }

    @Test
    public void testStandardIE6() {
        String header = "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0; T312461; .NET CLR 1.1.4322)";
        UserAgentParser uap = new UserAgentParser(header);
        assertEquals("Windows NT 5.0", uap.getBrowserOperatingSystem());
        assertEquals("MSIE", uap.getBrowserName());
        assertEquals("6.0", uap.getBrowserVersion());     
    }
    
     @Test
    public void testKonqueror() {
        String header = "Mozilla/5.0 (compatible; Konqueror/3.1; Linux 2.4.22-10mdk; X11; i686; fr, fr_FR)";
        UserAgentParser uap = new UserAgentParser(header);
        assertEquals("Linux 2.4.22-10mdk", uap.getBrowserOperatingSystem());
        assertEquals("Konqueror", uap.getBrowserName());
        assertEquals("3.1", uap.getBrowserVersion());     
    }

    @Test
    public void testIPhone() {
        String header = "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_1_3 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7E18 Safari/528.16";
        UserAgentParser uap = new UserAgentParser(header);
        assertEquals("iPhone", uap.getBrowserOperatingSystem());
        assertEquals("Safari", uap.getBrowserName());
        assertEquals("528.16", uap.getBrowserVersion());
    }

    @Test
    public void testChrome() {
        String header = "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/532.5 (KHTML, like Gecko) Chrome/4.0.249.89 Safari/532.5";
        UserAgentParser uap = new UserAgentParser(header);
        assertEquals("Windows", uap.getBrowserOperatingSystem());
        assertEquals("Chrome", uap.getBrowserName());
        assertEquals("4.0.249.89", uap.getBrowserVersion());
    }

    @Test
    public void testGoogleBots() {
        String header = "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot)";
        UserAgentParser uap = new UserAgentParser(header);

        assertEquals("Googlebot", uap.getBrowserName());
        assertEquals("2.1", uap.getBrowserVersion());
        assertNull(uap.getBrowserOperatingSystem());

        header = "Feedfetcher-Google; (+http://www.google.com/feedfetcher.html; 1 subscribers; feed-id=1368044458445856733)";
        uap = new UserAgentParser(header);
        assertEquals("Feedfetcher-Google;", uap.getBrowserName());
        assertNull(uap.getBrowserVersion());
        assertNull(uap.getBrowserOperatingSystem());

    }


}

