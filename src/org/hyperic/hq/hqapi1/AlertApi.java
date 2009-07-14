/*
 *
 * NOTE: This copyright does *not* cover user programs that use HQ
 * program services by normal system calls through the application
 * program interfaces provided as part of the Hyperic Plug-in Development
 * Kit or the Hyperic Client Development Kit - this is merely considered
 * normal use of the program, and does *not* fall under the heading of
 * "derived work".
 *
 * Copyright (C) [2008, 2009], Hyperic, Inc.
 * This file is part of HQ.
 *
 * HQ is free software; you can redistribute it and/or modify
 * it under the terms version 2 of the GNU General Public License as
 * published by the Free Software Foundation. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA.
 *
 */

package org.hyperic.hq.hqapi1;

import org.hyperic.hq.hqapi1.types.Resource;
import org.hyperic.hq.hqapi1.types.StatusResponse;
import org.hyperic.hq.hqapi1.types.AlertsResponse;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

public class AlertApi extends BaseApi {

    AlertApi(HQConnection conn) {
        super(conn);
    }

    /**
     * Find Alerts in the system.
     *
     * @param begin The beginning of the time window in epoch-millis.
     * @param end The end of the time window in epoch-millis.
     * @param count The maximum number of Alert instances to return.
     * @param severity The minimum severity to query.  1 = LOW - 3 = HIGH
     * @param inEscalation If true, only return Alerts which are in Escalation
     * @param notFixed If true, only return Alerts which are not fixed.
     *
     * @return On {@link org.hyperic.hq.hqapi1.types.ResponseStatus#SUCCESS},
     * a list of Alerts are returned via
     * {@link org.hyperic.hq.hqapi1.types.AlertsResponse#getAlert()}.
     *
     * @throws IOException If a network error occurs while making the request.
     */
    public AlertsResponse findAlerts(long begin, long end, int count,
                                     int severity, Boolean inEscalation,
                                     Boolean notFixed)
            throws IOException
    {
        Map<String,String[]> params = new HashMap<String,String[]>();
        params.put("begin", new String[] { Long.toString(begin)});
        params.put("end", new String[] { Long.toString(end)});
        params.put("count", new String[] { Integer.toString(count)});
        params.put("severity", new String[] { Integer.toString(severity)});
        
        if (inEscalation != null) {
            params.put("inEscalation", new String[] { Boolean.toString(inEscalation)});
        }
        if (notFixed != null) {
            params.put("notFixed", new String[] { Boolean.toString(notFixed)});
        }

        return doGet("alert/find.hqu", params, AlertsResponse.class);
    }

    /**
     * Find Alerts for the given Resource.
     *
     * @param r The Resource to query for alerts.
     * @param begin The beginning of the time window in epoch-millis.
     * @param end The end of the time window in epoch-millis.
     * @param count The maximum number of Alert instances to return.
     * @param severity The minimum severity to query.  1 = LOW - 3 = HIGH
     * @param inEscalation If true, only return Alerts which are in Escalation
     * @param notFixed If true, only return Alerts which are not fixed.
     *
     * @return On {@link org.hyperic.hq.hqapi1.types.ResponseStatus#SUCCESS},
     * a list of Alerts are returned via
     * {@link org.hyperic.hq.hqapi1.types.AlertsResponse#getAlert()}.
     *
     * @throws IOException If a network error occurs while making the request.
     */
    public AlertsResponse findAlerts(Resource r, long begin, long end,
                                     int count, int severity,
                                     Boolean inEscalation,
                                     Boolean notFixed)
            throws Exception
    {
        Map<String,String[]> params = new HashMap<String,String[]>();
        params.put("resourceId", new String[] { Integer.toString(r.getId())});
        params.put("begin", new String[] { Long.toString(begin)});
        params.put("end", new String[] { Long.toString(end)});
        params.put("count", new String[] { Integer.toString(count)});
        params.put("severity", new String[] {Integer.toString(severity)});

        if (inEscalation != null) {
            params.put("inEscalation", new String[] { Boolean.toString(inEscalation)});
        }
        if (notFixed != null) {
            params.put("notFixed", new String[] { Boolean.toString(notFixed)});
        }
        
        return doGet("alert/findByResource.hqu", params, AlertsResponse.class);
    }

    /**
     * Fix an Alert
     *
     * @param alertId The id of the Alert to fix.
     *
     * @return {@link org.hyperic.hq.hqapi1.types.ResponseStatus#SUCCESS},
     * if the Alert was successfully fixed.
     *
     * @throws IOException If a network error occurs while making the request.
     */
    public StatusResponse fixAlert(Integer alertId)
        throws IOException
    {
        Map<String,String[]> params = new HashMap<String,String[]>();
        params.put("id", new String[] { Integer.toString(alertId) });

        return doGet("alert/fix.hqu", params, StatusResponse.class);
    }

    /**
     * Acknowledge an Alert
     *
     * @param alertId The id of the Alert to acknowledge.
     * @param reason The reason for acknowledgement.
     * @param pause If not null, pause the Escalation for the specified number
     * of milliseconds.
     *
     * @return {@link org.hyperic.hq.hqapi1.types.ResponseStatus#SUCCESS},
     * if the Alert was successfully acknowledged.
     *
     * @throws IOException If a network error occurs while making the request.
     */
    public StatusResponse ackAlert(Integer alertId, String reason, Long pause)
        throws IOException
    {
        Map<String,String[]> params = new HashMap<String,String[]>();
        params.put("id", new String[] { Integer.toString(alertId) });
        params.put("reason", new String[] { reason });
        params.put("pause", new String[] { Long.toString(pause)});

        return doGet("alert/ack.hqu", params, StatusResponse.class);
    }

    /**
     * Delete an Alert
     *
     * @param alertId The id of the Alert to delete.
     *
     * @return {@link org.hyperic.hq.hqapi1.types.ResponseStatus#SUCCESS},
     * if the Alert was successfully deleted.
     *
     * @throws IOException If a network error occurs while making the request.
     */
    public StatusResponse delete(Integer alertId)
        throws IOException
    {
        Map<String,String[]> params = new HashMap<String,String[]>();
        params.put("id", new String[] { Integer.toString(alertId) });

        return doGet("alert/delete.hqu", params, StatusResponse.class);
    }
}
