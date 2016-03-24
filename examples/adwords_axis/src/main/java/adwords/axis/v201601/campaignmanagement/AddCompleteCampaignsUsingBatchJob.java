// Copyright 2016 Google Inc. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package adwords.axis.v201601.campaignmanagement;

import com.google.api.ads.adwords.axis.factory.AdWordsServices;
import com.google.api.ads.adwords.axis.utils.v201601.SelectorBuilder;
import com.google.api.ads.adwords.axis.utils.v201601.batchjob.BatchJobHelper;
import com.google.api.ads.adwords.axis.utils.v201601.batchjob.BatchJobMutateResponse;
import com.google.api.ads.adwords.axis.utils.v201601.batchjob.MutateResult;
import com.google.api.ads.adwords.axis.v201601.cm.AdGroup;
import com.google.api.ads.adwords.axis.v201601.cm.AdGroupAd;
import com.google.api.ads.adwords.axis.v201601.cm.AdGroupAdOperation;
import com.google.api.ads.adwords.axis.v201601.cm.AdGroupCriterionOperation;
import com.google.api.ads.adwords.axis.v201601.cm.AdGroupOperation;
import com.google.api.ads.adwords.axis.v201601.cm.AdvertisingChannelType;
import com.google.api.ads.adwords.axis.v201601.cm.BatchJob;
import com.google.api.ads.adwords.axis.v201601.cm.BatchJobOperation;
import com.google.api.ads.adwords.axis.v201601.cm.BatchJobProcessingError;
import com.google.api.ads.adwords.axis.v201601.cm.BatchJobServiceInterface;
import com.google.api.ads.adwords.axis.v201601.cm.BatchJobStatus;
import com.google.api.ads.adwords.axis.v201601.cm.BiddableAdGroupCriterion;
import com.google.api.ads.adwords.axis.v201601.cm.BiddingStrategyConfiguration;
import com.google.api.ads.adwords.axis.v201601.cm.BiddingStrategyType;
import com.google.api.ads.adwords.axis.v201601.cm.Bids;
import com.google.api.ads.adwords.axis.v201601.cm.Budget;
import com.google.api.ads.adwords.axis.v201601.cm.BudgetBudgetDeliveryMethod;
import com.google.api.ads.adwords.axis.v201601.cm.BudgetBudgetPeriod;
import com.google.api.ads.adwords.axis.v201601.cm.BudgetOperation;
import com.google.api.ads.adwords.axis.v201601.cm.Campaign;
import com.google.api.ads.adwords.axis.v201601.cm.CampaignCriterionOperation;
import com.google.api.ads.adwords.axis.v201601.cm.CampaignOperation;
import com.google.api.ads.adwords.axis.v201601.cm.CampaignStatus;
import com.google.api.ads.adwords.axis.v201601.cm.CpcBid;
import com.google.api.ads.adwords.axis.v201601.cm.Keyword;
import com.google.api.ads.adwords.axis.v201601.cm.KeywordMatchType;
import com.google.api.ads.adwords.axis.v201601.cm.ManualCpcBiddingScheme;
import com.google.api.ads.adwords.axis.v201601.cm.Money;
import com.google.api.ads.adwords.axis.v201601.cm.NegativeCampaignCriterion;
import com.google.api.ads.adwords.axis.v201601.cm.Operation;
import com.google.api.ads.adwords.axis.v201601.cm.Operator;
import com.google.api.ads.adwords.axis.v201601.cm.Selector;
import com.google.api.ads.adwords.axis.v201601.cm.TextAd;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.ads.adwords.lib.selectorfields.v201601.cm.BatchJobField;
import com.google.api.ads.common.lib.auth.OfflineCredentials;
import com.google.api.ads.common.lib.auth.OfflineCredentials.Api;
import com.google.api.client.auth.oauth2.Credential;
import com.google.common.collect.AbstractSequentialIterator;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * This code sample illustrates how to use BatchJobService to create a complete
 * campaign, including ad groups and keywords.
 *
 * <p>Credentials and properties in {@code fromFile()} are pulled from the
 * "ads.properties" file. See README for more info.
 */
public class AddCompleteCampaignsUsingBatchJob {
  private static final long NUMBER_OF_CAMPAIGNS_TO_ADD = 2;
  private static final long NUMBER_OF_ADGROUPS_TO_ADD = 2;
  private static final long NUMBER_OF_KEYWORDS_TO_ADD = 5;
  private static final int MAX_POLL_ATTEMPTS = 5;
  private static final Set<BatchJobStatus> PENDING_STATUSES =
      Sets.newHashSet(
          BatchJobStatus.ACTIVE, BatchJobStatus.AWAITING_FILE, BatchJobStatus.CANCELING);

  public static void main(String[] args) throws Exception {
    // Generate a refreshable OAuth2 credential.
    Credential oAuth2Credential =
        new OfflineCredentials.Builder()
            .forApi(Api.ADWORDS)
            .fromFile()
            .build()
            .generateCredential();

    // Construct an AdWordsSession.
    AdWordsSession session =
        new AdWordsSession.Builder().fromFile().withOAuth2Credential(oAuth2Credential).build();

    AdWordsServices adWordsServices = new AdWordsServices();

    runExample(adWordsServices, session);
  }

  public static void runExample(AdWordsServices adWordsServices, AdWordsSession session)
      throws Exception {
    // Get the MutateJobService.
    BatchJobServiceInterface batchJobService =
        adWordsServices.get(session, BatchJobServiceInterface.class);

    // Create a BatchJob.
    BatchJobOperation addOp = new BatchJobOperation();
    addOp.setOperator(Operator.ADD);
    addOp.setOperand(new BatchJob());

    BatchJob batchJob = batchJobService.mutate(new BatchJobOperation[] {addOp}).getValue(0);

    // Get the upload URL from the new job.
    String uploadUrl = batchJob.getUploadUrl().getUrl();

    System.out.printf("Created BatchJob with ID %d, status '%s' and upload URL %s.%n",
        batchJob.getId(), batchJob.getStatus(), uploadUrl);

    // Create a temporary ID generator that will produce a sequence of descending negative numbers.
    Iterator<Long> tempIdGenerator = new AbstractSequentialIterator<Long>(-1L) {
      @Override
      protected Long computeNext(Long previous) {
        return Long.MIN_VALUE == previous.longValue() ? null : previous - 1;
      }
    };

    // Use a random UUID name prefix to avoid name collisions.
    String namePrefix = UUID.randomUUID().toString();

    // Create the mutate request that will be sent to the upload URL.
    List<Operation> operations = Lists.newArrayList();

    // Create and add an operation to create a new budget.
    BudgetOperation budgetOperation = buildBudgetOperation(tempIdGenerator, namePrefix);
    operations.add(budgetOperation);

    // Create and add operations to create new campaigns.
    List<CampaignOperation> campaignOperations =
        buildCampaignOperations(tempIdGenerator, namePrefix, budgetOperation);
    operations.addAll(campaignOperations);

    // Create and add operations to create new negative keyword criteria for each campaign.
    operations.addAll(buildCampaignCriterionOperations(campaignOperations));

    // Create and add operations to create new ad groups.
    List<AdGroupOperation> adGroupOperations =
        Lists.newArrayList(buildAdGroupOperations(tempIdGenerator, namePrefix, campaignOperations));
    operations.addAll(adGroupOperations);

    // Create and add operations to create new ad group criteria (keywords).
    operations.addAll(buildAdGroupCriterionOperations(adGroupOperations));

    // Create and add operations to create new ad group ads (text ads).
    operations.addAll(buildAdGroupAdOperations(adGroupOperations));

    // Use a BatchJobHelper to upload all operations.
    BatchJobHelper batchJobHelper = new BatchJobHelper(session);

    batchJobHelper.uploadBatchJobOperations(operations, uploadUrl);
    System.out.printf("Uploaded %d operations for batch job with ID %d.%n",
        operations.size(), batchJob.getId());

    // Poll for completion of the batch job using an exponential back off.
    int pollAttempts = 0;
    boolean isPending = true;
    Selector selector =
        new SelectorBuilder()
            .fields(BatchJobField.Id, BatchJobField.Status, BatchJobField.DownloadUrl,
                BatchJobField.ProcessingErrors, BatchJobField.ProgressStats)
            .equalsId(batchJob.getId())
            .build();
    do {
      long sleepSeconds = (long) Math.scalb(30, pollAttempts);
      System.out.printf("Sleeping %d seconds...%n", sleepSeconds);
      Thread.sleep(sleepSeconds * 1000);

      batchJob = batchJobService.get(selector).getEntries(0);
      System.out.printf(
          "Batch job ID %d has status '%s'.%n", batchJob.getId(), batchJob.getStatus());

      pollAttempts++;
      isPending = PENDING_STATUSES.contains(batchJob.getStatus());
    } while (isPending && pollAttempts < MAX_POLL_ATTEMPTS);

    if (isPending) {
      throw new TimeoutException(
          "Job is still in pending state after polling " + MAX_POLL_ATTEMPTS + " times.");
    }

    if (batchJob.getProcessingErrors() != null) {
      int i = 0;
      for (BatchJobProcessingError processingError : batchJob.getProcessingErrors()) {
        System.out.printf(
            "  Processing error [%d]: errorType=%s, trigger=%s, errorString=%s, fieldPath=%s"
            + ", reason=%s%n",
            i++, processingError.getApiErrorType(), processingError.getTrigger(),
            processingError.getErrorString(), processingError.getFieldPath(),
            processingError.getReason());
      }
    } else {
      System.out.println("No processing errors found.");
    }

    if (batchJob.getDownloadUrl() != null && batchJob.getDownloadUrl().getUrl() != null) {
      BatchJobMutateResponse mutateResponse =
          batchJobHelper.downloadBatchJobMutateResponse(batchJob.getDownloadUrl().getUrl());
      System.out.printf("Downloaded results from %s:%n", batchJob.getDownloadUrl().getUrl());
      for (MutateResult mutateResult : mutateResponse.getMutateResults()) {
        String outcome = mutateResult.getErrorList() == null ? "SUCCESS" : "FAILURE";
        System.out.printf("  Operation [%d] - %s%n", mutateResult.getIndex(), outcome);
      }
    } else {
      System.out.println("No results available for download.");
    }
  }

  private static List<AdGroupAdOperation> buildAdGroupAdOperations(
      List<AdGroupOperation> adGroupOperations) {
    List<AdGroupAdOperation> operations = Lists.newArrayList();
    for (AdGroupOperation adGroupOperation : adGroupOperations) {
      long adGroupId = adGroupOperation.getOperand().getId();
      AdGroupAd adGroupAd = new AdGroupAd();
      adGroupAd.setAdGroupId(adGroupId);

      TextAd textAd = new TextAd();
      textAd.setHeadline("Luxury Cruise to Mars");
      textAd.setDescription1("Visit the Red Planet in style.");
      textAd.setDescription2("Low-gravity fun for everyone!");
      textAd.setDisplayUrl("www.example.com");
      textAd.setFinalUrls(new String[] {"http://www.example.com/1"});

      adGroupAd.setAd(textAd);

      AdGroupAdOperation operation = new AdGroupAdOperation();
      operation.setOperator(Operator.ADD);
      operation.setOperand(adGroupAd);

      operations.add(operation);
    }
    return operations;
  }

  private static List<AdGroupCriterionOperation> buildAdGroupCriterionOperations(
      List<AdGroupOperation> adGroupOperations) {
    List<AdGroupCriterionOperation> adGroupCriteriaOperations = Lists.newArrayList();

    // Create AdGroupCriterionOperations to add keywords.
    for (AdGroupOperation adGroupOperation : adGroupOperations) {
      long newAdGroupId = adGroupOperation.getOperand().getId();
      for (int i = 0; i < NUMBER_OF_KEYWORDS_TO_ADD; i++) {
        // Create Keyword.
        String text = String.format("mars%d", i);

        // Make 50% of keywords invalid to demonstrate error handling.
        if (i % 2 == 0) {
          text = text + "!!!";
        }
        Keyword keyword = new Keyword();
        keyword.setText(text);
        keyword.setMatchType(KeywordMatchType.BROAD);

        // Create BiddableAdGroupCriterion.
        BiddableAdGroupCriterion biddableAdGroupCriterion = new BiddableAdGroupCriterion();
        biddableAdGroupCriterion.setAdGroupId(newAdGroupId);
        biddableAdGroupCriterion.setCriterion(keyword);

        // Create AdGroupCriterionOperation.
        AdGroupCriterionOperation operation = new AdGroupCriterionOperation();
        operation.setOperand(biddableAdGroupCriterion);
        operation.setOperator(Operator.ADD);

        // Add to list.
        adGroupCriteriaOperations.add(operation);
      }
    }
    return adGroupCriteriaOperations;
  }

  private static List<AdGroupOperation> buildAdGroupOperations(Iterator<Long> tempIdGenerator,
      String namePrefix, Iterable<CampaignOperation> campaignOperations) {
    List<AdGroupOperation> operations = Lists.newArrayList();
    for (CampaignOperation campaignOperation : campaignOperations) {
      for (int i = 0; i < NUMBER_OF_ADGROUPS_TO_ADD; i++) {
        AdGroup adGroup = new AdGroup();
        adGroup.setCampaignId(campaignOperation.getOperand().getId());
        adGroup.setId(tempIdGenerator.next());
        adGroup.setName(String.format("Batch Ad Group %s.%s", namePrefix, i));

        BiddingStrategyConfiguration biddingStrategyConfiguration =
            new BiddingStrategyConfiguration();
        CpcBid bid = new CpcBid();
        bid.setBid(new Money(null, 10000000L));
        biddingStrategyConfiguration.setBids(new Bids[] {bid});

        adGroup.setBiddingStrategyConfiguration(biddingStrategyConfiguration);

        AdGroupOperation operation = new AdGroupOperation();
        operation.setOperand(adGroup);
        operation.setOperator(Operator.ADD);

        operations.add(operation);
      }
    }
    return operations;
  }

  private static List<CampaignCriterionOperation> buildCampaignCriterionOperations(
      List<CampaignOperation> campaignOperations) {

    List<CampaignCriterionOperation> operations = Lists.newArrayList();
    for (CampaignOperation campaignOperation : campaignOperations) {
      Keyword keyword = new Keyword();
      keyword.setMatchType(KeywordMatchType.BROAD);
      keyword.setText("venus");

      NegativeCampaignCriterion negativeCriterion = new NegativeCampaignCriterion();
      negativeCriterion.setCampaignId(campaignOperation.getOperand().getId());
      negativeCriterion.setCriterion(keyword);

      CampaignCriterionOperation operation = new CampaignCriterionOperation();
      operation.setOperand(negativeCriterion);
      operation.setOperator(Operator.ADD);

      operations.add(operation);
    }

    return operations;
  }

  private static List<CampaignOperation> buildCampaignOperations(
      Iterator<Long> tempIdGenerator, String namePrefix, BudgetOperation budgetOperation) {
    long budgetId = budgetOperation.getOperand().getBudgetId();

    List<CampaignOperation> operations = Lists.newArrayList();
    for (int i = 0; i < NUMBER_OF_CAMPAIGNS_TO_ADD; i++) {
      Campaign campaign = new Campaign();
      campaign.setName(String.format("Batch Campaign %s.%s", namePrefix, i));
      campaign.setStatus(CampaignStatus.PAUSED);
      campaign.setId(tempIdGenerator.next());
      campaign.setAdvertisingChannelType(AdvertisingChannelType.SEARCH);
      Budget budget = new Budget();
      budget.setBudgetId(budgetId);
      campaign.setBudget(budget);
      BiddingStrategyConfiguration biddingStrategyConfiguration =
          new BiddingStrategyConfiguration();
      biddingStrategyConfiguration.setBiddingStrategyType(BiddingStrategyType.MANUAL_CPC);

      // You can optionally provide a bidding scheme in place of the type.
      ManualCpcBiddingScheme cpcBiddingScheme = new ManualCpcBiddingScheme();
      cpcBiddingScheme.setEnhancedCpcEnabled(false);
      biddingStrategyConfiguration.setBiddingScheme(cpcBiddingScheme);

      campaign.setBiddingStrategyConfiguration(biddingStrategyConfiguration);

      CampaignOperation operation = new CampaignOperation();
      operation.setOperand(campaign);
      operation.setOperator(Operator.ADD);
      operations.add(operation);
    }
    return operations;
  }

  private static BudgetOperation buildBudgetOperation(Iterator<Long> tempIdGenerator,
      String namePrefix) {
    Budget budget = new Budget();
    budget.setBudgetId(tempIdGenerator.next());
    budget.setName(String.format("Interplanetary Cruise %s", namePrefix));
    Money budgetAmount = new Money();
    budgetAmount.setMicroAmount(50000000L);
    budget.setAmount(budgetAmount);
    budget.setDeliveryMethod(BudgetBudgetDeliveryMethod.STANDARD);
    budget.setPeriod(BudgetBudgetPeriod.DAILY);

    BudgetOperation budgetOperation = new BudgetOperation();
    budgetOperation.setOperand(budget);
    budgetOperation.setOperator(Operator.ADD);
    return budgetOperation;
  }
}
