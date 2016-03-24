
package com.google.api.ads.adwords.jaxws.v201603.cm;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * 
 *       Service used to get and mutate Feeds that contain ad customizer data.
 *       
 *       <p>This service is a convenience for creating and modifying ad customizer Feeds, but such Feeds
 *       can also be managed using a combination of the Feed, FeedMapping, and CustomerFeed services.
 *     
 * 
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.1
 * 
 */
@WebService(name = "AdCustomizerFeedServiceInterface", targetNamespace = "https://adwords.google.com/api/adwords/cm/v201603")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface AdCustomizerFeedServiceInterface {


    /**
     * 
     *         Returns a list of AdCustomizerFeeds that meet the selector criteria.
     *         
     *         @param selector Determines which AdCustomizerFeeds to return. If empty, all AdCustomizerFeeds
     *         are returned.
     *         @return The list of AdCustomizerFeeds.
     *         @throws ApiException Indicates a problem with the request.
     *       
     * 
     * @param selector
     * @return
     *     returns com.google.api.ads.adwords.jaxws.v201603.cm.AdCustomizerFeedPage
     * @throws ApiException_Exception
     */
    @WebMethod
    @WebResult(name = "rval", targetNamespace = "https://adwords.google.com/api/adwords/cm/v201603")
    @RequestWrapper(localName = "get", targetNamespace = "https://adwords.google.com/api/adwords/cm/v201603", className = "com.google.api.ads.adwords.jaxws.v201603.cm.AdCustomizerFeedServiceInterfaceget")
    @ResponseWrapper(localName = "getResponse", targetNamespace = "https://adwords.google.com/api/adwords/cm/v201603", className = "com.google.api.ads.adwords.jaxws.v201603.cm.AdCustomizerFeedServiceInterfacegetResponse")
    public AdCustomizerFeedPage get(
        @WebParam(name = "selector", targetNamespace = "https://adwords.google.com/api/adwords/cm/v201603")
        Selector selector)
        throws ApiException_Exception
    ;

    /**
     * 
     *         Adds, removes, or modifies AdCustomizerFeeds.
     *         
     *         @param operations The operations to apply.
     *         @return The resulting AdCustomizerFeeds.
     *         @throws ApiException Indicates a problem with the request.
     *       
     * 
     * @param operations
     * @return
     *     returns com.google.api.ads.adwords.jaxws.v201603.cm.AdCustomizerFeedReturnValue
     * @throws ApiException_Exception
     */
    @WebMethod
    @WebResult(name = "rval", targetNamespace = "https://adwords.google.com/api/adwords/cm/v201603")
    @RequestWrapper(localName = "mutate", targetNamespace = "https://adwords.google.com/api/adwords/cm/v201603", className = "com.google.api.ads.adwords.jaxws.v201603.cm.AdCustomizerFeedServiceInterfacemutate")
    @ResponseWrapper(localName = "mutateResponse", targetNamespace = "https://adwords.google.com/api/adwords/cm/v201603", className = "com.google.api.ads.adwords.jaxws.v201603.cm.AdCustomizerFeedServiceInterfacemutateResponse")
    public AdCustomizerFeedReturnValue mutate(
        @WebParam(name = "operations", targetNamespace = "https://adwords.google.com/api/adwords/cm/v201603")
        List<AdCustomizerFeedOperation> operations)
        throws ApiException_Exception
    ;

}
