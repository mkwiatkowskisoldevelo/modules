package org.motechproject.callflow.osgi;

import org.motechproject.callflow.service.CallFlowServer;
import org.motechproject.callflow.service.FlowSessionService;
import org.motechproject.decisiontree.core.model.CallStatus;
import org.motechproject.testing.osgi.BaseOsgiIT;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

public class CallFlowBundleIT extends BaseOsgiIT {

    public void testCallFlowServer() {
        CallFlowServer decisionTreeServer = getService(CallFlowServer.class);
        FlowSessionService flowSessionService = getService(FlowSessionService.class);

        String sessionId = "123a";
        String phoneNumber = "1234567890";
        String provider = "freeivr";

        try {
            ModelAndView mnv = decisionTreeServer.getResponse(sessionId, phoneNumber, provider, "sometree", CallStatus.Disconnect.toString(), "en");
            assertNotNull(mnv);
            assertNotNull(mnv.getViewName());
            assertTrue(mnv.getViewName().contains(provider));
        } finally {
            flowSessionService.removeCallSession(sessionId);
        }
    }

    @Override
    protected List<String> getImports() {
        return Arrays.asList("org.motechproject.callflow.service");
    }
}
