package org.html2har;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Utility based on Firefox + Firebug + NetExport that print the HAR
 * i.e. the Http Archive Specification of an URL.
 * 
 * You can fond more information about HAR :
 * @see http://groups.google.com/group/http-archive-specification/web/har-1-2-spec?pli=1
 * @see http://www.softwareishard.com/blog/firebug/http-archive-specification/
 *
 * @author romain
 */
public class Html2Har {

    private File firefoxBinary = null;

    /**
     * Use default Firefox Binary Path.
     */
    public Html2Har() {
    }

    /**
     * Use a custom Firefox binary Path.
     * @param firefoxBinaryPath the binary path of Firefox.
     * @see FirefoxBinary from Selenium API
     */
    public Html2Har(File firefoxBinary) {
        if (!firefoxBinary.exists()) {
            throw new RuntimeException("Firefox binary path is not valid : " + firefoxBinary.getAbsolutePath());
        }
        this.firefoxBinary = firefoxBinary;
    }

    /**
     * Return the Http Archive Specification of a web page.
     *
     * @param url an abslute URL : http://google.fr
     * @return the HAR in JSON format.
     */
    public String toHar(String url) {
        String har = null;
        FirefoxDriver driver = new FirefoxHtml2HarDriver(new FirefoxBinary(firefoxBinary));
        try {
            driver.get(url);
            driver.get(url);
            Thread.sleep(1000);
            File[] files = FirefoxHtml2HarDriver.getTempDir().listFiles();
            if (files.length < 1) {
                throw new RuntimeException("Error happens in directory, there is not enough files." + files);
            }
            har = FileUtils.readFileToString(files[0], "utf-8");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            driver.close();
            FirefoxHtml2HarDriver.deleteTempDir();
        }
        return har;
    }

    /**
     * Usage
     * ./html2har URL
     *
     * Exemple
     * ./html2har http://google.fr
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        String url;
        if (args.length != 1) {
            //throw new RuntimeException("Argument not specified. Exemple ./html2har http://google.fr");
            url = "http://google.fr";
        } else {
            url = args[0];
        }
        System.out.println(new Html2Har().toHar(url));
    }
}
