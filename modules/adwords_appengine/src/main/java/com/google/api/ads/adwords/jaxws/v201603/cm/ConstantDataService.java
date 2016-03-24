
package com.google.api.ads.adwords.jaxws.v201603.cm;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "ConstantDataService", targetNamespace = "https://adwords.google.com/api/adwords/cm/v201603", wsdlLocation = "https://adwords.google.com/api/adwords/cm/v201603/ConstantDataService?wsdl")
public class ConstantDataService
    extends Service
{

    private final static URL CONSTANTDATASERVICE_WSDL_LOCATION;
    private final static WebServiceException CONSTANTDATASERVICE_EXCEPTION;
    private final static QName CONSTANTDATASERVICE_QNAME = new QName("https://adwords.google.com/api/adwords/cm/v201603", "ConstantDataService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("https://adwords.google.com/api/adwords/cm/v201603/ConstantDataService?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        CONSTANTDATASERVICE_WSDL_LOCATION = url;
        CONSTANTDATASERVICE_EXCEPTION = e;
    }

    public ConstantDataService() {
        super(__getWsdlLocation(), CONSTANTDATASERVICE_QNAME);
    }

    public ConstantDataService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    /**
     * 
     * @return
     *     returns ConstantDataServiceInterface
     */
    @WebEndpoint(name = "ConstantDataServiceInterfacePort")
    public ConstantDataServiceInterface getConstantDataServiceInterfacePort() {
        return super.getPort(new QName("https://adwords.google.com/api/adwords/cm/v201603", "ConstantDataServiceInterfacePort"), ConstantDataServiceInterface.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ConstantDataServiceInterface
     */
    @WebEndpoint(name = "ConstantDataServiceInterfacePort")
    public ConstantDataServiceInterface getConstantDataServiceInterfacePort(WebServiceFeature... features) {
        return super.getPort(new QName("https://adwords.google.com/api/adwords/cm/v201603", "ConstantDataServiceInterfacePort"), ConstantDataServiceInterface.class, features);
    }

    private static URL __getWsdlLocation() {
        if (CONSTANTDATASERVICE_EXCEPTION!= null) {
            throw CONSTANTDATASERVICE_EXCEPTION;
        }
        return CONSTANTDATASERVICE_WSDL_LOCATION;
    }

}
