package org.html2har;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.io.IOException;
import org.apache.commons.io.FileUtils;

/**
 * Add plugins and preferences to firefox.
 * 
 * @author romain
 */
public class FirefoxHtml2HarDriver extends FirefoxDriver {

    private static File tempDir = null;

    public FirefoxHtml2HarDriver(FirefoxBinary binary) {
        super(binary, tweakProfile(new FirefoxProfile()));
    }

    private static FirefoxProfile tweakProfile(FirefoxProfile profile) {
        try {
            /* Load FileFox plugins */
            profile.addExtension(FirefoxProfile.class, "/firebug-1.6X.0a7.xpi");
            profile.addExtension(FirefoxProfile.class, "/netExport-0.7b13-mob.xpi");
            profile.addExtension(FirefoxProfile.class, "/fireStarter-0.1.a5.xpi");

            /* Disable the cache */
            profile.setPreference("browser.cache.disk.enable", false);
            profile.setPreference("browser.cache.memory.enable", false);
            profile.setPreference("browser.cache.offline.enable", false);
            profile.setPreference("network.http.use-cache", false);

            /* Set preferences for firebug */
            profile.setPreference("extensions.firebug.DBG_NETEXPORT", true);
            profile.setPreference("extensions.firebug.onByDefault", true);
            profile.setPreference("extensions.firebug.defaultPanelName", "net");
            profile.setPreference("extensions.firebug.net.enableSites", true);
            profile.setPreference("extensions.firebug.previousPlacement", 1);

            /* Set preferences for netExport*/
            profile.setPreference("extensions.firebug.netexport.autoExportActive", true);
            profile.setPreference("extensions.firebug.netexport.defaultLogDir", getTempDir().getAbsolutePath());

        } catch (IOException e) {
            throw new RuntimeException("Could not load required extensions, something is worng", e);
        }
        return profile;
    }

    public synchronized static File getTempDir() throws IOException {
        if (tempDir == null) {
            tempDir = File.createTempFile("har2pdf", "files");
            if (tempDir.exists()) {
                if (tempDir.isFile()) {
                    tempDir.delete();
                } else {
                    deleteTempDir();
                }
            }
            tempDir.mkdirs();
        }
        return tempDir;
    }

    public synchronized static void deleteTempDir() {
        try {
            FileUtils.deleteDirectory(tempDir);
        } catch (IOException ex) {
            //Not really a problem.
            ex.printStackTrace();
        }
    }
}
