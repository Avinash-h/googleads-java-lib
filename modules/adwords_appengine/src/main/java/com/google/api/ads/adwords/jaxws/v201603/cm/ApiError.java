
package com.google.api.ads.adwords.jaxws.v201603.cm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import com.google.api.ads.adwords.jaxws.v201603.billing.BudgetOrderError;
import com.google.api.ads.adwords.jaxws.v201603.billing.CustomerOrderLineError;
import com.google.api.ads.adwords.jaxws.v201603.ch.CustomerSyncError;
import com.google.api.ads.adwords.jaxws.v201603.express.ExpressBusinessError;
import com.google.api.ads.adwords.jaxws.v201603.express.PromotionError;
import com.google.api.ads.adwords.jaxws.v201603.mcm.CustomerError;
import com.google.api.ads.adwords.jaxws.v201603.mcm.LabelServiceError;
import com.google.api.ads.adwords.jaxws.v201603.mcm.ManagedCustomerServiceError;
import com.google.api.ads.adwords.jaxws.v201603.o.TargetingIdeaError;
import com.google.api.ads.adwords.jaxws.v201603.o.TrafficEstimatorError;
import com.google.api.ads.adwords.jaxws.v201603.rm.MutateMembersError;
import com.google.api.ads.adwords.jaxws.v201603.rm.UserListError;


/**
 * 
 *             The API error base class that provides details about an error that occurred
 *             while processing a service request.
 *             
 *             <p>The OGNL field path is provided for parsers to identify the request data
 *             element that may have caused the error.</p>
 *           
 * 
 * <p>Java class for ApiError complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApiError">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fieldPath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="trigger" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="errorString" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ApiError.Type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApiError", propOrder = {
    "fieldPath",
    "trigger",
    "errorString",
    "apiErrorType"
})
@XmlSeeAlso({
    UserListError.class,
    MutateMembersError.class,
    com.google.api.ads.adwords.jaxws.v201603.rm.CollectionSizeError.class,
    TargetingIdeaError.class,
    TrafficEstimatorError.class,
    com.google.api.ads.adwords.jaxws.v201603.o.CurrencyCodeError.class,
    DataError.class,
    OfflineConversionError.class,
    ReportDefinitionError.class,
    MultiplierError.class,
    NotEmptyError.class,
    DistinctError.class,
    AdCustomizerFeedError.class,
    RateExceededError.class,
    LocationCriterionServiceError.class,
    FunctionError.class,
    com.google.api.ads.adwords.jaxws.v201603.cm.CurrencyCodeError.class,
    LabelError.class,
    CampaignSharedSetError.class,
    NullError.class,
    AdxError.class,
    StringLengthError.class,
    AdGroupServiceError.class,
    com.google.api.ads.adwords.jaxws.v201603.cm.CollectionSizeError.class,
    SharedSetError.class,
    SelectorError.class,
    FeedItemError.class,
    CampaignCriterionError.class,
    RejectedError.class,
    MediaBundleError.class,
    AdGroupCriterionError.class,
    SharedCriterionError.class,
    EntityNotFound.class,
    EntityAccessDenied.class,
    FunctionParsingError.class,
    RequiredError.class,
    CampaignFeedError.class,
    QueryError.class,
    CriterionError.class,
    ReadOnlyError.class,
    BiddingStrategyError.class,
    PagingError.class,
    UrlError.class,
    CampaignPreferenceError.class,
    ExperimentServiceError.class,
    MediaError.class,
    ExtensionSettingError.class,
    IdError.class,
    TrialError.class,
    StringFormatError.class,
    DateError.class,
    BiddingErrors.class,
    BudgetError.class,
    ClientTermsError.class,
    CustomerFeedError.class,
    AudioError.class,
    OperationAccessDenied.class,
    NotWhitelistedError.class,
    AdCustomizerError.class,
    SettingError.class,
    VideoError.class,
    AdError.class,
    SizeLimitError.class,
    AppPostbackUrlError.class,
    PolicyViolationError.class,
    OperatorError.class,
    RequestError.class,
    FeedMappingError.class,
    FeedError.class,
    BatchJobProcessingError.class,
    ImageError.class,
    DraftError.class,
    NewEntityCreationError.class,
    EntityCountLimitExceeded.class,
    CampaignError.class,
    StatsQueryError.class,
    AuthenticationError.class,
    ConversionTrackingError.class,
    AuthorizationError.class,
    InternalApiError.class,
    AdGroupFeedError.class,
    DateRangeError.class,
    AdGroupAdError.class,
    FeedAttributeReferenceError.class,
    ForwardCompatibilityError.class,
    QuotaCheckError.class,
    RegionCodeError.class,
    BatchJobError.class,
    ExperimentError.class,
    AdParamError.class,
    RangeError.class,
    DatabaseError.class,
    CustomerError.class,
    LabelServiceError.class,
    com.google.api.ads.adwords.jaxws.v201603.mcm.CollectionSizeError.class,
    ManagedCustomerServiceError.class,
    com.google.api.ads.adwords.jaxws.v201603.mcm.CurrencyCodeError.class,
    ExpressBusinessError.class,
    PromotionError.class,
    CustomerSyncError.class,
    BudgetOrderError.class,
    CustomerOrderLineError.class
})
public abstract class ApiError {

    protected String fieldPath;
    protected String trigger;
    protected String errorString;
    @XmlElement(name = "ApiError.Type")
    protected String apiErrorType;

    /**
     * Gets the value of the fieldPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFieldPath() {
        return fieldPath;
    }

    /**
     * Sets the value of the fieldPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFieldPath(String value) {
        this.fieldPath = value;
    }

    /**
     * Gets the value of the trigger property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrigger() {
        return trigger;
    }

    /**
     * Sets the value of the trigger property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrigger(String value) {
        this.trigger = value;
    }

    /**
     * Gets the value of the errorString property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorString() {
        return errorString;
    }

    /**
     * Sets the value of the errorString property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorString(String value) {
        this.errorString = value;
    }

    /**
     * Gets the value of the apiErrorType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApiErrorType() {
        return apiErrorType;
    }

    /**
     * Sets the value of the apiErrorType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApiErrorType(String value) {
        this.apiErrorType = value;
    }

}
