package com.thinkbiganalytics.nifi.security;

import org.apache.hadoop.security.UserGroupInformation;
import org.apache.nifi.logging.ComponentLog;

import java.io.IOException;

/**
 * Periodically attempts to renew the Kerberos user's ticket for the given UGI.
 *
 * This class will attempt to call ugi.checkTGTAndReloginFromKeytab() which
 * will re-login the user if the ticket expired or is close to expiry. Between
 * relogin attempts this thread will sleep for the provided amount of time.
 *
 */
public class KerberosTicketRenewer implements Runnable {

    private final UserGroupInformation ugi;
    private final long renewalPeriod;
    private final ComponentLog logger;

    private volatile boolean stopped = false;

    /**
     * @param ugi
     *          the user to renew the ticket for
     * @param renewalPeriod
     *          the amount of time in milliseconds to wait between renewal attempts
     * @param logger
     *          the logger from the component that started the renewer
     */
    public KerberosTicketRenewer(final UserGroupInformation ugi, final long renewalPeriod, final ComponentLog logger) {
        this.ugi = ugi;
        this.renewalPeriod = renewalPeriod;
        this.logger = logger;
    }

    @Override
    public void run() {
        stopped = false;
        while (!stopped) {
            try {
                logger.debug("Invoking renewal attempt for Kerberos ticket");
                // While we run this "frequently", the Hadoop implementation will only perform the login at 80% of ticket lifetime.
                ugi.checkTGTAndReloginFromKeytab();
            } catch (IOException e) {
                logger.error("Failed to renew Kerberos ticket", e);
            }

            // Wait for a bit before checking again.
            try {
                Thread.sleep(renewalPeriod);
            } catch (InterruptedException e) {
                logger.error("Renewal thread interrupted", e);
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    public void stop() {
        stopped = true;
    }

}
