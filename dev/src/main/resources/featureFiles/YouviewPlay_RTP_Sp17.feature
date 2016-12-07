Feature: YouviewPlay_Sprint17 - RequestToPlay

  Background: 
    Given YouviewPlay host "http://10.101.47.56:50100"

  Scenario: Verifying the response from MPX ProductFeedService during a YouviewPlay request
    Given Product Id is "BBJ711388180", device token is "f4HGUAPXPHiNOvE69%2F67Grp%2B3l4b7BEVLXyoc31aQSQ%2FbrR7E6W4IFGwpDM9l%2BOytOndLD4YMkThePR2Mp3PcAvGi67HKL%2Bb3CbcBeP8XVvmDx2zoMuxd6sW6niK5wXlmKdlTO3ZKFU0S2aGVlBW9mRQkikGuHJbsfRnKl8%2BUnM%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP17_0001"
    And placementId "7ad1c930-cb20-4b21-a72c-f5604b39093a", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.40.55"
    And X-BTAppVersion "2.1.26"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B0; CDS/21.9.0; API/2.9.4; PS/2.9.24) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries for a play
    Then YouviewPlay should log the response from ProductFeedService

  Scenario: Verifying the response from MPX MediaFeedService during a YouviewPlay request
    Given Product Id is "BBJ711388180", device token is "f4HGUAPXPHiNOvE69%2F67Grp%2B3l4b7BEVLXyoc31aQSQ%2FbrR7E6W4IFGwpDM9l%2BOytOndLD4YMkThePR2Mp3PcAvGi67HKL%2Bb3CbcBeP8XVvmDx2zoMuxd6sW6niK5wXlmKdlTO3ZKFU0S2aGVlBW9mRQkikGuHJbsfRnKl8%2BUnM%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP17_0002"
    And placementId "7ad1c930-cb20-4b21-a72c-f5604b39093a", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.40.55"
    And X-BTAppVersion "2.1.26"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B0; CDS/21.9.0; API/2.9.4; PS/2.9.24) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries for a play
    Then YouviewPlay should log the response from MediaFeedService

  Scenario: Verifying the YouviewPlay request process when the content meta data information retrieval process is changed from Portal XMLs to MPX FeedServices
    Given Product Id is "BBJ711388180", device token is "f4HGUAPXPHiNOvE69%2F67Grp%2B3l4b7BEVLXyoc31aQSQ%2FbrR7E6W4IFGwpDM9l%2BOytOndLD4YMkThePR2Mp3PcAvGi67HKL%2Bb3CbcBeP8XVvmDx2zoMuxd6sW6niK5wXlmKdlTO3ZKFU0S2aGVlBW9mRQkikGuHJbsfRnKl8%2BUnM%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP17_0003"
    And placementId "7ad1c930-cb20-4b21-a72c-f5604b39093a", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.40.55"
    And X-BTAppVersion "2.1.26"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B0; CDS/21.9.0; API/2.9.4; PS/2.9.24) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play should success with error code as zero

  Scenario: Verifying the log responses during a YouviewPlay request
    Given Product Id is "BBJ711388180", device token is "f4HGUAPXPHiNOvE69%2F67Grp%2B3l4b7BEVLXyoc31aQSQ%2FbrR7E6W4IFGwpDM9l%2BOytOndLD4YMkThePR2Mp3PcAvGi67HKL%2Bb3CbcBeP8XVvmDx2zoMuxd6sW6niK5wXlmKdlTO3ZKFU0S2aGVlBW9mRQkikGuHJbsfRnKl8%2BUnM%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP17_0004"
    And placementId "7ad1c930-cb20-4b21-a72c-f5604b39093a", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.40.55"
    And X-BTAppVersion "2.1.26"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B0; CDS/21.9.0; API/2.9.4; PS/2.9.24) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries for a play
    Then YouviewPlay should log all the responses

  Scenario: To Check the Error response from YouviewPlay when the MPX ProductFeedServices are not available or content metadata could not be fetched
    Given Product Id is "BBJ711388186", device token is "f4HGUAPXPHiNOvE69%2F67GhtH1QbUOXlGcCx8kUI4E3U%2FbrR7E6W4IFGwpDM9l%2BOyGcNSoFB33AEs18jyU8sVTq%2FKnAxqKoVY%2FWZYfY8NHdQ58EtmWav2AA5ViD7R%2FiXqF7mEx7LKttXWkjfE6ctI2PPyg0Fo1fkoSScWKHBhWfk%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP17_0005"
    And placementId "7ad1c930-cb20-4b21-a72c-f5604b39093a", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.86.01"
    And X-BTAppVersion "2.1.26"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B0; CDS/21.9.0; API/2.9.4; PS/2.9.24) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play should fail with error code "8001" and with error Message as "BadParameterException:Invalid Product"

  Scenario: To Check the Error response from YouviewPlay when the MPX MediaFeedService are not available or content metadata could not be fetched
    Given Product Id is "BBJ711281651", device token is "f4HGUAPXPHiNOvE69%2F67Gt2pWy3SXlVw7CbheoEWcHY%2FbrR7E6W4IFGwpDM9l%2BOyGcNSoFB33AEs18jyU8sVTnabq4T3eXPMkCbIxrbUynlsyVsdn9dy8bJ3ouHEeo4oF7mEx7LKttXWkjfE6ctI2PPyg0Fo1fkoSScWKHBhWfk%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP17_0006"
    And placementId "7ad1c930-cb20-4b21-a72c-f5604b39093a", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.86.02"
    And X-BTAppVersion "2.1.26"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B0; CDS/21.9.0; API/2.9.4; PS/2.9.24) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
     Then play should fail with error code "8001" and with error Message as "BadParameterException:Media not available"

  Scenario: Verifying the log requests and responses from MPX FeedService during a YouviewPlay request
    Given Product Id is "BBJ711388180", device token is "f4HGUAPXPHiNOvE69%2F67Grp%2B3l4b7BEVLXyoc31aQSQ%2FbrR7E6W4IFGwpDM9l%2BOytOndLD4YMkThePR2Mp3PcAvGi67HKL%2Bb3CbcBeP8XVvmDx2zoMuxd6sW6niK5wXlmKdlTO3ZKFU0S2aGVlBW9mRQkikGuHJbsfRnKl8%2BUnM%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP17_0007"
    And placementId "7ad1c930-cb20-4b21-a72c-f5604b39093a", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.40.55"
    And X-BTAppVersion "2.1.26"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B0; CDS/21.9.0; API/2.9.4; PS/2.9.24) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries for a play
    Then YouviewPlay should log all the requests and responses from MPX FeedServices
    
      Scenario: To Check the Error response from YouviewPlay when the MPX is down
    Given Product Id is "BBJ711388180", device token is "f4HGUAPXPHiNOvE69%2F67Grp%2B3l4b7BEVLXyoc31aQSQ%2FbrR7E6W4IFGwpDM9l%2BOytOndLD4YMkThePR2Mp3PcAvGi67HKL%2Bb3CbcBeP8XVvmDx2zoMuxd6sW6niK5wXlmKdlTO3ZKFU0S2aGVlBW9mRQkikGuHJbsfRnKl8%2BUnM%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP17_0008"
    And placementId "7ad1c930-cb20-4b21-a72c-f5604b39093a", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.40.5"
    And X-BTAppVersion "2.1.26"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B0; CDS/21.9.0; API/2.9.4; PS/2.9.24) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
     Then play should fail and MPX should log 500 error.

     
     Scenario: Verifying the response from YouviewPlay while playing SVOD-SD content
    Given Product Id is "BBJ711388180", device token is "f4HGUAPXPHiNOvE69%2F67Grp%2B3l4b7BEVLXyoc31aQSQ%2FbrR7E6W4IFGwpDM9l%2BOytOndLD4YMkThePR2Mp3PcAvGi67HKL%2Bb3CbcBeP8XVvmDx2zoMuxd6sW6niK5wXlmKdlTO3ZKFU0S2aGVlBW9mRQkikGuHJbsfRnKl8%2BUnM%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP17_0009"
    And placementId "7ad1c930-cb20-4b21-a72c-f5604b39093a", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.40.55"
    And X-BTAppVersion "2.1.26"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B0; CDS/21.9.0; API/2.9.4; PS/2.9.24) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play should success with error code as zero
    
    Scenario: Verifying the response from YouviewPlay while playing SVOD-HD content
    Given Product Id is "BBJ711388179", device token is "f4HGUAPXPHiNOvE69%2F67GtaCGTKyGVLUgk%2Bx15BUviE%2FbrR7E6W4IFGwpDM9l%2BOyGcNSoFB33AEs18jyU8sVTurwsd2Hfr%2BCtrzB9oBjY7EHokgRI9TMXIU6EcKkWRefF7mEx7LKttXWkjfE6ctI2PPyg0Fo1fkoSScWKHBhWfk%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP17_0010"
    And placementId "7ad1c930-cb20-4b21-a72c-f5604b39093a", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.86.03"
    And X-BTAppVersion "2.1.26"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B0; CDS/21.9.0; API/2.9.4; PS/2.9.24) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play should success with error code as zero

  Scenario: Verifying the response from YouviewPlay while playing SVOD-UHD content
    Given Product Id is "BBJ711281719", device token is "f4HGUAPXPHiNOvE69%2F67GlrkMJOeJg%2F4ZJSUiMkyEuM%2FbrR7E6W4IFGwpDM9l%2BOyGcNSoFB33AEs18jyU8sVTuTbs%2F2%2Fc5iJiQdMC%2BcY3fuoRKT1p9rI9w418qASB4wdF7mEx7LKttXWkjfE6ctI2PPyg0Fo1fkoSScWKHBhWfk%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP17_0011"
    And placementId "7ad1c930-cb20-4b21-a72c-f5604b39093a", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.86.04"
    And X-BTAppVersion "2.1.26"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B0; 8500; CDS/21.10.30; API/2.9.40; PS/2.9.24) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play should success with error code as zero

  Scenario: Verifying the response from YouviewPlay while playing TVOD-SD content
    Given Product Id is "BBJ711281056", device token is "f4HGUAPXPHiNOvE69%2F67Grj0NQml%2FYxWJfANcqzNScw%2FbrR7E6W4IFGwpDM9l%2BOyGcNSoFB33AEs18jyU8sVTpDKAopcDDJgcwqB9mBOFcTosxMfSBJpor4ai0lNPe1pF7mEx7LKttXWkjfE6ctI2PPyg0Fo1fkoSScWKHBhWfk%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP17_0012"
    And placementId "7ad1c930-cb20-4b21-a72c-f5604b39093a", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.86.05"
    And X-BTAppVersion "2.1.26"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B0; CDS/21.9.0; API/2.9.4; PS/2.9.24) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play should success with error code as zero

  Scenario: Verifying the response from YouviewPlay while playing TVOD-HD content
    Given Product Id is "BBJ711280570", device token is "f4HGUAPXPHiNOvE69%2F67GjCT0%2BmWA3fDwEy2MOQfsxY%2FbrR7E6W4IFGwpDM9l%2BOyGcNSoFB33AEs18jyU8sVTiGWOCnkGcmblxvosSEe%2F9k%2FVfkNLh4DOshGFAvj5NBoF7mEx7LKttXWkjfE6ctI2PPyg0Fo1fkoSScWKHBhWfk%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP17_0013"
    And placementId "7ad1c930-cb20-4b21-a72c-f5604b39093a", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.86.06"
    And X-BTAppVersion "2.1.26"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B0; CDS/21.9.0; API/2.9.4; PS/2.9.24) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play should success with error code as zero

  Scenario: Verifying the response from YouviewPlay while playing TVOD-UHD content
    Given Product Id is "BBJ711280570", device token is "f4HGUAPXPHiNOvE69%2F67GjCT0%2BmWA3fDwEy2MOQfsxY%2FbrR7E6W4IFGwpDM9l%2BOyGcNSoFB33AEs18jyU8sVTiGWOCnkGcmblxvosSEe%2F9k%2FVfkNLh4DOshGFAvj5NBoF7mEx7LKttXWkjfE6ctI2PPyg0Fo1fkoSScWKHBhWfk%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP17_0014"
    And placementId "7ad1c930-cb20-4b21-a72c-f5604b39093a", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.86.06"
    And X-BTAppVersion "2.1.26"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B0; 8500; CDS/21.10.30; API/2.9.40; PS/2.9.24) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play should success with error code as zero
    
    
      Scenario: Verifying the response from YouviewPlay while playing TSVOD-SD content
    Given Product Id is "BBJ711281614", device token is "f4HGUAPXPHiNOvE69%2F67GuKkNnsG7gvmvK3HY8RiqT8%2FbrR7E6W4IFGwpDM9l%2BOyGcNSoFB33AEs18jyU8sVToLilKp6li6VsZxOF2vvpBcXDmmJhbN%2Bn4ORvHP8gy6zF7mEx7LKttXWkjfE6ctI2PPyg0Fo1fkoSScWKHBhWfk%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP17_0015"
    And placementId "7ad1c930-cb20-4b21-a72c-f5604b39093a", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.86.07"
    And X-BTAppVersion "2.1.26"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B0; CDS/21.9.0; API/2.9.4; PS/2.9.24) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play should success with error code as zero

  Scenario: Verifying the response from YouviewPlay while playing TSVOD-HD content
    Given Product Id is "BBJ711281702", device token is "f4HGUAPXPHiNOvE69%2F67GvBzwWL%2BEW9yVT%2FBtxU2cOE%2FbrR7E6W4IFGwpDM9l%2BOyGcNSoFB33AEs18jyU8sVTux%2FldzCXzoS7HwDkcrPvJrqYmqyiv7%2F9HVmLY0uW%2FQsF7mEx7LKttXWkjfE6ctI2PPyg0Fo1fkoSScWKHBhWfk%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP17_0016"
    And placementId "7ad1c930-cb20-4b21-a72c-f5604b39093a", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.86.08"
    And X-BTAppVersion "2.1.26"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B0; CDS/21.9.0; API/2.9.4; PS/2.9.24) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play should success with error code as zero

  Scenario: Verifying the response from YouviewPlay while playing TSVOD-UHD content
    Given Product Id is "BBJ711281766", device token is "f4HGUAPXPHiNOvE69%2F67GqM8h0k0q8DDyAA1wYPh9Mo%2FbrR7E6W4IFGwpDM9l%2BOyGcNSoFB33AEs18jyU8sVTph6%2BA0TjYfESxtKmMBnGy4bYVUZIeGKWxWl%2FblKX97lF7mEx7LKttXWkjfE6ctI2PPyg0Fo1fkoSScWKHBhWfk%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP17_0017"
    And placementId "7ad1c930-cb20-4b21-a72c-f5604b39093a", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.86.09"
    And X-BTAppVersion "2.1.26"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B0; 8500; CDS/21.10.30; API/2.9.40; PS/2.9.24) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play should success with error code as zero
    
    
   Scenario: Verifying the response from YouviewPlay while playing series content
    Given Product Id is "BBJ711281588", device token is "f4HGUAPXPHiNOvE69%2F67Gv4iTDuj4HjMBXPhjU29DTE%2FbrR7E6W4IFGwpDM9l%2BOyGcNSoFB33AEs18jyU8sVTvYSAOO3PadVj2OjAShqyyo7nYv%2FpaBhe5gRMHzZwk%2BDF7mEx7LKttXWkjfE6ctI2PPyg0Fo1fkoSScWKHBhWfk%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP17_0018"
    And placementId "7ad1c930-cb20-4b21-a72c-f5604b39093a", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.86.10"
    And X-BTAppVersion "2.1.26"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B0; 8500; CDS/21.10.30; API/2.9.40; PS/2.9.24) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play should success with error code as zero
    
      Scenario: Verifying the response from YouviewPlay while playing a trailer content
    Given Product Id is "14364229408", device token is "f4HGUAPXPHiNOvE69%2F67Gur%2Ft7Fvcc2dkFmj17kBzEo%2FbrR7E6W4IFGwpDM9l%2BOyGcNSoFB33AEs18jyU8sVTv3ulitZx8lEUneDGoqA6%2B5r7AjGnqX3uyVskj7s0YEkF7mEx7LKttXWkjfE6ctI2PPyg0Fo1fkoSScWKHBhWfk%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "true", IsContentURLRequired "true"
    And SlotType "trailer" ,CorrelationId "youviewP17_0019"
    And placementId "Search-Rule", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.86.00"
    And X-BTAppVersion "2.1.2556982"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B08500; CDS/20.9.30; API/2.8.1; PS/2.8.18) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play should success with error code as zero
    
    Scenario: Verifying whether the MPlay keyword is removed from YouviewPlay.properties file
    When we verify the YouviewPlay properties file
    Then MPlay keyword should not be available