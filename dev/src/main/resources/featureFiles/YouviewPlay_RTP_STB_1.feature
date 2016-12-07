Feature: YouviewPlay - RequestToPlay

  Background: 
    Given YouviewPlay host "http://10.101.47.56:50100"

  Scenario: user plays a feature on an active trusted box whose device token is BT Token
    Given Product Id is "BBJ711388180", device token is "f4HGUAPXPHiNOvE69%2F67Grp%2B3l4b7BEVLXyoc31aQSQ%2FbrR7E6W4IFGwpDM9l%2BOytOndLD4YMkThePR2Mp3PcAvGi67HKL%2Bb3CbcBeP8XVvmDx2zoMuxd6sW6niK5wXlmKdlTO3ZKFU0S2aGVlBW9mRQkikGuHJbsfRnKl8%2BUnM%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP08_0001"
    And placementId "7ad1c930-cb20-4b21-a72c-f5604b39093a", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.40.55"
    And X-BTAppVersion "2.1.26"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B0; CDS/21.9.0; API/2.9.4; PS/2.9.24) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play should success with error code as zero

  Scenario: User plays a feature on an active trusted box with an invalid BT token
    Given Product Id is "BBJ711388180", device token is "f4HGUAPXPHiNOvE69%2F67Gt7eHU%2FaRlTBk1Pq%2FOVSkys%2FbrR7E6W4IFGwpDM9l%2BOytOndLD4YMkThePR2Mp3PcAvGi67HKL%2Bb3CbcBeP8XVtZZsC6repokMrBByQC9rrRv7ekm5%2BZ3YbnogkCDm1TRhVCUKTjDYhOAyybVHLGwjI%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP08_0002"
    And placementId "7ad1c930-cb20-4b21-a72c-f5604b39093a", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.40.23"
    And X-BTAppVersion "2.1.26"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B0; CDS/21.9.0; API/2.9.4; PS/2.9.24) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play should fail with error code "8026" and with an error Message as "Invalid Device Token"

  Scenario: User plays a feature on an active trusted box with an expired BT token
    Given Product Id is "BBJ711388180", device token is "f4HGUAPXPHiNOvE69%2F67Gt7eHU%2FaRlTBk1Pq%2FOVSkys%2FbrR7E6W4IFGwpDM9l%2BOytOndLD4YMkThePR2Mp3PcAvGi67HKL%2Bb3CbcBeP8XVtZZsC6repokMrBByQC9rrRv7ekm5%2BZ3YbnogkCDm1TRhVCUKTjDYhOAyybVHLGwjI%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP08_0003"
    And placementId "7ad1c930-cb20-4b21-a72c-f5604b39093a", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.40.55"
    And X-BTAppVersion "2.1.26"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B0; CDS/21.9.0; API/2.9.4; PS/2.9.24) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play should fail with error code "8026" and with an error Message as "Invalid Device Token"

  Scenario: user plays an SVOD SD content on an active trusted box whose device token is BT Token
    Given Product Id is "BBJ711388180", device token is "f4HGUAPXPHiNOvE69%2F67Grp%2B3l4b7BEVLXyoc31aQSQ%2FbrR7E6W4IFGwpDM9l%2BOytOndLD4YMkThePR2Mp3PcAvGi67HKL%2Bb3CbcBeP8XVvmDx2zoMuxd6sW6niK5wXlmKdlTO3ZKFU0S2aGVlBW9mRQkikGuHJbsfRnKl8%2BUnM%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP08_0004"
    And placementId "7ad1c930-cb20-4b21-a72c-f5604b39093a", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.40.55"
    And X-BTAppVersion "2.1.26"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B0; CDS/21.9.0; API/2.9.4; PS/2.9.24) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play should success with error code as zero

  Scenario: user plays an SVOD HD content on an active trusted box whose device token is BT Token
    Given Product Id is "BBJ711388179", device token is "f4HGUAPXPHiNOvE69%2F67GtaCGTKyGVLUgk%2Bx15BUviE%2FbrR7E6W4IFGwpDM9l%2BOyGcNSoFB33AEs18jyU8sVTurwsd2Hfr%2BCtrzB9oBjY7EHokgRI9TMXIU6EcKkWRefF7mEx7LKttXWkjfE6ctI2PPyg0Fo1fkoSScWKHBhWfk%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP08_00005"
    And placementId "7ad1c930-cb20-4b21-a72c-f5604b39093a", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.86.03"
    And X-BTAppVersion "2.1.26"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B0; CDS/21.9.0; API/2.9.4; PS/2.9.24) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play should success with error code as zero

  Scenario: user plays an SVOD UHD content on an active trusted box whose device token is BT Token
    Given Product Id is "BBJ711281719", device token is "f4HGUAPXPHiNOvE69%2F67GlrkMJOeJg%2F4ZJSUiMkyEuM%2FbrR7E6W4IFGwpDM9l%2BOyGcNSoFB33AEs18jyU8sVTuTbs%2F2%2Fc5iJiQdMC%2BcY3fuoRKT1p9rI9w418qASB4wdF7mEx7LKttXWkjfE6ctI2PPyg0Fo1fkoSScWKHBhWfk%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP08_00006"
    And placementId "7ad1c930-cb20-4b21-a72c-f5604b39093a", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.86.04"
    And X-BTAppVersion "2.1.26"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B0; 8500; CDS/21.10.30; API/2.9.40; PS/2.9.24) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play should success with error code as zero

  Scenario: user plays an TVOD SD content on an active trusted box whose device token is BT Token
    Given Product Id is "BBJ711281056", device token is "f4HGUAPXPHiNOvE69%2F67Grj0NQml%2FYxWJfANcqzNScw%2FbrR7E6W4IFGwpDM9l%2BOyGcNSoFB33AEs18jyU8sVTpDKAopcDDJgcwqB9mBOFcTosxMfSBJpor4ai0lNPe1pF7mEx7LKttXWkjfE6ctI2PPyg0Fo1fkoSScWKHBhWfk%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP08_00007"
    And placementId "7ad1c930-cb20-4b21-a72c-f5604b39093a", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.86.05"
    And X-BTAppVersion "2.1.26"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B0; CDS/21.9.0; API/2.9.4; PS/2.9.24) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play should success with error code as zero

  Scenario: user plays an TVOD HD content on an active trusted box whose device token is BT Token
    Given Product Id is "BBJ711280570", device token is "f4HGUAPXPHiNOvE69%2F67GjCT0%2BmWA3fDwEy2MOQfsxY%2FbrR7E6W4IFGwpDM9l%2BOyGcNSoFB33AEs18jyU8sVTiGWOCnkGcmblxvosSEe%2F9k%2FVfkNLh4DOshGFAvj5NBoF7mEx7LKttXWkjfE6ctI2PPyg0Fo1fkoSScWKHBhWfk%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP08_0008"
    And placementId "7ad1c930-cb20-4b21-a72c-f5604b39093a", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.86.06"
    And X-BTAppVersion "2.1.26"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B0; CDS/21.9.0; API/2.9.4; PS/2.9.24) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play should success with error code as zero

  Scenario: user plays an TVOD UHD content on an active trusted box whose device token is BT Token
    Given Product Id is "BBJ711280570", device token is "f4HGUAPXPHiNOvE69%2F67GjCT0%2BmWA3fDwEy2MOQfsxY%2FbrR7E6W4IFGwpDM9l%2BOyGcNSoFB33AEs18jyU8sVTiGWOCnkGcmblxvosSEe%2F9k%2FVfkNLh4DOshGFAvj5NBoF7mEx7LKttXWkjfE6ctI2PPyg0Fo1fkoSScWKHBhWfk%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP08_0009"
    And placementId "7ad1c930-cb20-4b21-a72c-f5604b39093a", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.86.06"
    And X-BTAppVersion "2.1.26"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B0; 8500; CDS/21.10.30; API/2.9.40; PS/2.9.24) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play should success with error code as zero

  Scenario: user plays an TSVOD SD content on an active trusted box whose device token is BT Token
    Given Product Id is "BBJ711281614", device token is "f4HGUAPXPHiNOvE69%2F67GuKkNnsG7gvmvK3HY8RiqT8%2FbrR7E6W4IFGwpDM9l%2BOyGcNSoFB33AEs18jyU8sVToLilKp6li6VsZxOF2vvpBcXDmmJhbN%2Bn4ORvHP8gy6zF7mEx7LKttXWkjfE6ctI2PPyg0Fo1fkoSScWKHBhWfk%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP08_00010"
    And placementId "7ad1c930-cb20-4b21-a72c-f5604b39093a", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.86.07"
    And X-BTAppVersion "2.1.26"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B0; CDS/21.9.0; API/2.9.4; PS/2.9.24) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play should success with error code as zero

  Scenario: user plays an TSVOD HD content on an active trusted box whose device token is BT Token
    Given Product Id is "BBJ711281702", device token is "f4HGUAPXPHiNOvE69%2F67GvBzwWL%2BEW9yVT%2FBtxU2cOE%2FbrR7E6W4IFGwpDM9l%2BOyGcNSoFB33AEs18jyU8sVTux%2FldzCXzoS7HwDkcrPvJrqYmqyiv7%2F9HVmLY0uW%2FQsF7mEx7LKttXWkjfE6ctI2PPyg0Fo1fkoSScWKHBhWfk%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP08_00011"
    And placementId "7ad1c930-cb20-4b21-a72c-f5604b39093a", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.86.08"
    And X-BTAppVersion "2.1.26"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B0; CDS/21.9.0; API/2.9.4; PS/2.9.24) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play should success with error code as zero

  Scenario: user plays an TSVOD UHD content on an active trusted box whose device token is BT Token
    Given Product Id is "BBJ711281766", device token is "f4HGUAPXPHiNOvE69%2F67GqM8h0k0q8DDyAA1wYPh9Mo%2FbrR7E6W4IFGwpDM9l%2BOyGcNSoFB33AEs18jyU8sVTph6%2BA0TjYfESxtKmMBnGy4bYVUZIeGKWxWl%2FblKX97lF7mEx7LKttXWkjfE6ctI2PPyg0Fo1fkoSScWKHBhWfk%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP08_00012"
    And placementId "7ad1c930-cb20-4b21-a72c-f5604b39093a", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.86.09"
    And X-BTAppVersion "2.1.26"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B0; 8500; CDS/21.10.30; API/2.9.40; PS/2.9.24) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play should success with error code as zero

  Scenario: user plays an EST SD content on an active trusted box whose device token is BT Token
    Given Product Id is "BBJ711281588", device token is "f4HGUAPXPHiNOvE69%2F67Gv4iTDuj4HjMBXPhjU29DTE%2FbrR7E6W4IFGwpDM9l%2BOyGcNSoFB33AEs18jyU8sVTvYSAOO3PadVj2OjAShqyyo7nYv%2FpaBhe5gRMHzZwk%2BDF7mEx7LKttXWkjfE6ctI2PPyg0Fo1fkoSScWKHBhWfk%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP08_00013"
    And placementId "7ad1c930-cb20-4b21-a72c-f5604b39093a", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.86.10"
    And X-BTAppVersion "2.1.26"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B0; CDS/21.9.0; API/2.9.4; PS/2.9.24) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play should success with error code as zero

  Scenario: user plays an EST HD content on an active trusted box whose device token is BT Token
    Given Product Id is "BBJ711281588", device token is "f4HGUAPXPHiNOvE69%2F67Gv4iTDuj4HjMBXPhjU29DTE%2FbrR7E6W4IFGwpDM9l%2BOyGcNSoFB33AEs18jyU8sVTvYSAOO3PadVj2OjAShqyyo7nYv%2FpaBhe5gRMHzZwk%2BDF7mEx7LKttXWkjfE6ctI2PPyg0Fo1fkoSScWKHBhWfk%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP08_00014"
    And placementId "7ad1c930-cb20-4b21-a72c-f5604b39093a", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.86.10"
    And X-BTAppVersion "2.1.26"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B0; CDS/21.9.0; API/2.9.4; PS/2.9.24) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play should success with error code as zero

  Scenario: user plays an EST UHD content on an active trusted box whose device token is BT Token
    Given Product Id is "BBJ711281588", device token is "f4HGUAPXPHiNOvE69%2F67Gv4iTDuj4HjMBXPhjU29DTE%2FbrR7E6W4IFGwpDM9l%2BOyGcNSoFB33AEs18jyU8sVTvYSAOO3PadVj2OjAShqyyo7nYv%2FpaBhe5gRMHzZwk%2BDF7mEx7LKttXWkjfE6ctI2PPyg0Fo1fkoSScWKHBhWfk%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP08_00015"
    And placementId "7ad1c930-cb20-4b21-a72c-f5604b39093a", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.86.10"
    And X-BTAppVersion "2.1.26"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B0; 8500; CDS/21.10.30; API/2.9.40; PS/2.9.24) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play should success with error code as zero

 Scenario: To verify whether CorrelationId is present in the response when user tries to play a content
    Given Product Id is "BBJ711388180", device token is "f4HGUAPXPHiNOvE69%2F67Grp%2B3l4b7BEVLXyoc31aQSQ%2FbrR7E6W4IFGwpDM9l%2BOytOndLD4YMkThePR2Mp3PcAvGi67HKL%2Bb3CbcBeP8XVvmDx2zoMuxd6sW6niK5wXlmKdlTO3ZKFU0S2aGVlBW9mRQkikGuHJbsfRnKl8%2BUnM%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP08_00016"
    And placementId "7ad1c930-cb20-4b21-a72c-f5604b39093a", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.40.55"
    And X-BTAppVersion "2.1.26"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B0; CDS/21.9.0; API/2.9.4; PS/2.9.24) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play  Response should Contain "correlationId"

  Scenario: Verifying the response from Common Token Module when user tries to play a content
    Given Product Id is "BBJ711388180", device token is "f4HGUAPXPHiNOvE69%2F67Grp%2B3l4b7BEVLXyoc31aQSQ%2FbrR7E6W4IFGwpDM9l%2BOytOndLD4YMkThePR2Mp3PcAvGi67HKL%2Bb3CbcBeP8XVvmDx2zoMuxd6sW6niK5wXlmKdlTO3ZKFU0S2aGVlBW9mRQkikGuHJbsfRnKl8%2BUnM%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP08_00017"
    And placementId "7ad1c930-cb20-4b21-a72c-f5604b39093a", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.40.55"
    And X-BTAppVersion "2.1.26"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B0; CDS/21.9.0; API/2.9.4; PS/2.9.24) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries for a play
    Then YouviewPlay should log the response from common token module

  Scenario: User trying to play the feature on STB without Client-IP
    Given Product Id is "BBJ711388180", device token is "f4HGUAPXPHiNOvE69%2F67Grp%2B3l4b7BEVLXyoc31aQSQ%2FbrR7E6W4IFGwpDM9l%2BOytOndLD4YMkThePR2Mp3PcAvGi67HKL%2Bb3CbcBeP8XVvmDx2zoMuxd6sW6niK5wXlmKdlTO3ZKFU0S2aGVlBW9mRQkikGuHJbsfRnKl8%2BUnM%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP08_00018"
    And placementId "Search-Rule", schema "1.0", form "json"
    And X-Cluster-Client-IP ""
    And X-BTAppVersion "2.1.2556982"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B08500; CDS/20.9.30; API/2.8.1; PS/2.8.18) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play should fail with error code "8001" and with error Message as "BadParameterException:X-Cluster-Client-Ip"

  Scenario: user trying to play some Content whose Product Information is not found
    Given Product Id is "BBK711388180", device token is "f4HGUAPXPHiNOvE69%2F67Grp%2B3l4b7BEVLXyoc31aQSQ%2FbrR7E6W4IFGwpDM9l%2BOytOndLD4YMkThePR2Mp3PcAvGi67HKL%2Bb3CbcBeP8XVvmDx2zoMuxd6sW6niK5wXlmKdlTO3ZKFU0S2aGVlBW9mRQkikGuHJbsfRnKl8%2BUnM%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "true", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP08_00019"
    And placementId "Search-Rule", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.40.55"
    And X-BTAppVersion "2.1.2556982"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B08500; CDS/20.9.30; API/2.8.1; PS/2.8.18) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play should fail with error code "8001" and with error Message as "BadParameterException:Invalid Product"

 
  Scenario: To Check the Error response from YouviewPlay when one or more invalid or empty input parameter is sent through ClientUI.
    Given Product Id is "", device token is "f4HGUAPXPHiNOvE69%2F67Grp%2B3l4b7BEVLXyoc31aQSQ%2FbrR7E6W4IFGwpDM9l%2BOytOndLD4YMkThePR2Mp3PcAvGi67HKL%2Bb3CbcBeP8XVvmDx2zoMuxd6sW6niK5wXlmKdlTO3ZKFU0S2aGVlBW9mRQkikGuHJbsfRnKl8%2BUnM%3D"
    And AssetDeliveryType "download", IsLicenseRequired "true", IsContentURLRequired "false"
    And SlotType "feature" ,CorrelationId "youviewP08_00021"
    And placementId "Search-Rule", schema "1.0", form "json"
     And X-Cluster-Client-IP "60.50.40.55"
    And X-BTAppVersion "2.1.26"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B0; CDS/21.9.0; API/2.9.4; PS/2.9.24) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play should fail with error code "8023" and with error Message as "Missing Parameter"

  Scenario: To Check the error Response when there is no valid form present in the request URL
    Given Product Id is "BBJ711388180", device token is "f4HGUAPXPHiNOvE69%2F67Grp%2B3l4b7BEVLXyoc31aQSQ%2FbrR7E6W4IFGwpDM9l%2BOytOndLD4YMkThePR2Mp3PcAvGi67HKL%2Bb3CbcBeP8XVvmDx2zoMuxd6sW6niK5wXlmKdlTO3ZKFU0S2aGVlBW9mRQkikGuHJbsfRnKl8%2BUnM%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "true", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP08_00022"
    And placementId "Search-Rule", schema "1.0", form "ml"
     And X-Cluster-Client-IP "60.50.40.55"
    And X-BTAppVersion "2.1.2556982"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B08500; CDS/20.9.30; API/2.8.1; PS/2.8.18) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play should fail with error code "8004" and with error Message as "Invalid Form Parameter"

  Scenario: To Check the error Response when there is no valid schema is present in the request URL
    Given Product Id is "BBJ711388180", device token is "f4HGUAPXPHiNOvE69%2F67Grp%2B3l4b7BEVLXyoc31aQSQ%2FbrR7E6W4IFGwpDM9l%2BOytOndLD4YMkThePR2Mp3PcAvGi67HKL%2Bb3CbcBeP8XVvmDx2zoMuxd6sW6niK5wXlmKdlTO3ZKFU0S2aGVlBW9mRQkikGuHJbsfRnKl8%2BUnM%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "true", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP08_00023"
    And placementId "Search-Rule", schema "2.0", form "null"
     And X-Cluster-Client-IP "60.50.40.55"
    And X-BTAppVersion "2.1.2556982"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B08500; CDS/20.9.30; API/2.8.1; PS/2.8.18) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play should fail with error code "8002" and with error Message as "Invalid Schema Version"

  Scenario: User tries to play a feature whose Device Status is UNTRUSTED
    Given Product Id is "BBJ711388180", device token is "f4HGUAPXPHiNOvE69%2F67GoNInIUM8UYk6DZ8%2B4RGr9g%2FbrR7E6W4IFGwpDM9l%2BOytOndLD4YMkThePR2Mp3PcJ0DXqWcOwylYWh%2BgvhuG1DCar6g2KP4oCP7YFcosaYJNJOJB4BI%2FJ9OO7mOAiTiAWnOHwMforJYsmzDA9RrlHM%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP08_00024"
    And placementId "Search-Rule", schema "1.0", form "json"
     And X-Cluster-Client-IP "60.50.40.13"
    And X-BTAppVersion "2.1.2556982"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B08500; CDS/20.9.30; API/2.8.1; PS/2.8.18) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play should fail with error code "8007" and with error Message as "Device Untrusted"

  Scenario: User tries to play a feature whose account Status is CEASED
    Given Product Id is "BBJ711388180", device token is "f4HGUAPXPHiNOvE69%2F67GvlJ%2F6HEtlfJRcDI4bh6v%2Bg%2FbrR7E6W4IFGwpDM9l%2BOytOndLD4YMkThePR2Mp3PcAbPf6p4d5Ftwo6lt2YP6CtzqqUCcVYnZVksqS%2FHXDbmmKdlTO3ZKFU0S2aGVlBW9mRQkikGuHJbsfRnKl8%2BUnM%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP08_00025"
    And placementId "Search-Rule", schema "1.0", form "json"
     And X-Cluster-Client-IP "60.50.40.12"
    And X-BTAppVersion "2.1.2556982"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B08500; CDS/20.9.30; API/2.8.1; PS/2.8.18) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play should fail with error code "8008" and with error Message as "Account not in Active State"

  Scenario: User tries to play a feature whose Device Status is ACTIVE-DEREGISTERED
    Given Product Id is "BBJ711388180", device token is "f4HGUAPXPHiNOvE69%2F67GoNInIUM8UYk6DZ8%2B4RGr9g%2FbrR7E6W4IFGwpDM9l%2BOytOndLD4YMkThePR2Mp3PcKG%2Bjn8A%2FCf0Z8Rfowml4gRNYQJb4MvqvkzdpNgLq7%2FHNJOJB4BI%2FJ9OO7mOAiTiAWnOHwMforJYsmzDA9RrlHM%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP08_00026"
    And placementId "Search-Rule", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.40.13"
    And X-BTAppVersion "2.1.2556982"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B08500; CDS/20.9.30; API/2.8.1; PS/2.8.18) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play should fail with error code "8033" and with error Message as "Device De-registered"



  Scenario: To check the Error Response when a user tries to play some Content on Box for which Device hardware is not supported
    Given Product Id is "BBJ711410078", device token is "f4HGUAPXPHiNOvE69%2F67Grp%2B3l4b7BEVLXyoc31aQSQ%2FbrR7E6W4IFGwpDM9l%2BOytOndLD4YMkThePR2Mp3PcAvGi67HKL%2Bb3CbcBeP8XVvmDx2zoMuxd6sW6niK5wXlmKdlTO3ZKFU0S2aGVlBW9mRQkikGuHJbsfRnKl8%2BUnM%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP08_00029"
    And placementId "Search-Rule", schema "1.0", form "json"
     And X-Cluster-Client-IP "60.50.40.55"
    And X-BTAppVersion "2.1.2556982"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5"
    When user tries to play the content
    Then play should fail with error code "30001" and with error Message as "Device hardware is not supported - criteria{UHD VOD}"

  Scenario: To check the Error Response when a user tries to play some Content on Box whose Application software version is not supported
    Given Product Id is "BBJ711410078", device token is "f4HGUAPXPHiNOvE69%2F67Grp%2B3l4b7BEVLXyoc31aQSQ%2FbrR7E6W4IFGwpDM9l%2BOytOndLD4YMkThePR2Mp3PcAvGi67HKL%2Bb3CbcBeP8XVvmDx2zoMuxd6sW6niK5wXlmKdlTO3ZKFU0S2aGVlBW9mRQkikGuHJbsfRnKl8%2BUnM%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP08_00030"
    And placementId "Search-Rule", schema "1.0", form "json"
   And X-Cluster-Client-IP "60.50.40.55"
    And X-BTAppVersion "1.1.2556982"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B0; CDS/21.9.0; API/2.9.4; PS/2.9.24) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play should fail with error code "30002" and with error Message as "Youview Software is not supported - criteria{UHD VOD}"



  Scenario: Playback of a content on Youview device should succeed even though the user is unreliable when the graceperiod validation is successful.
    Given Product Id is "BBJ711388180", device token is "f4HGUAPXPHiNOvE69%2F67Grp%2B3l4b7BEVLXyoc31aQSQ%2FbrR7E6W4IFGwpDM9l%2BOytOndLD4YMkThePR2Mp3PcAvGi67HKL%2Bb3CbcBeP8XVvmDx2zoMuxd6sW6niK5wXlmKdlTO3ZKFU0S2aGVlBW9mRQkikGuHJbsfRnKl8%2BUnM%3D"
    And AssetDeliveryType "", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP08_00032"
    And placementId "Search-Rule", schema "1.0", form "json"
     And X-Cluster-Client-IP "60.50.40.55"
    And X-BTAppVersion "2.1.2556982"
    And ISPProvider "BT"
    And User-Agent "Mozilla/5.0 (avdn/bt_cardinal.stb.2015; avkb/pc,B:1572866; avvp/ssl; avui/tv; avrt/cehtml; avst/off; Windows; U; Windows NT 5.1; en-GB; rv:1.8.1.13) CE-HTML/1.0 BT-Cardinal-G2512/4.98.26"
    When user tries to play the content
    Then play should success with error code as zero

  Scenario: User trying to play the feature for which the media is restricted playback outside of allowed territory
    Given Product Id is "BBJ711388182", device token is "f4HGUAPXPHiNOvE69%2F67GsUtJy4ctCI1rf%2BA6LhEee8%2FbrR7E6W4IFGwpDM9l%2BOyeoCOfLBelU1QBpOilKhzlMPwyICxx4g3LWJsB2qpPh8blQFr63nfl%2BVWQLK2GoStiYc1tNPmXTgxXLSN5%2FkAaw11yRitqw6R6BkwyC%2FtENQ%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP08_00033"
    And placementId "Search-Rule", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.40.60"
    And X-BTAppVersion "2.1.2556982"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B08500; CDS/20.9.30; API/2.8.1; PS/2.8.18) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play must fail with error code "17504" and with error Message as "Playback of media is not available outside of the UK"

 

  Scenario: User trying to play the feature which he is not Entitled to
    Given Product Id is "BBJ711388188", device token is "f4HGUAPXPHiNOvE69%2F67GrpK5C%2BuZ4MmNsyGu7e%2FoB0%2FbrR7E6W4IFGwpDM9l%2BOyFrKI9j2Oo%2BCwG1W0nLRG%2BBWB%2FNx2lt5ele2MlX7PuOChPAnwQ%2FfyWbjOt2K639MvmKdlTO3ZKFU0S2aGVlBW9mRQkikGuHJbsfRnKl8%2BUnM%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "true", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP08_00036"
    And placementId "Search-Rule", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.40.132"
    And X-BTAppVersion "2.1.2556982"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B08500; CDS/20.9.30; API/2.8.1; PS/2.8.18) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play must fail with error code "8016" and with error Message as "Error In Validating Entitlements"

  Scenario: user fails to play a Trailer on a You-View Device as the Device is not Registered
    Given Product Id is "14364229408", device token is "f4HGUAPXPHiNOvE69%2F67Gur%2Ft7Fvcc2dkFmj17kBzEo%2FbrR7E6W4IFGwpDM9l%2BOyGcNSoFB33AEs18jyU8sVTv3ulitZx8lEUneDGoqA6%2B5r7AjGnqX3uyVskj7s0YEkF7mEx7LKttXWkjfE6ctI2PPyg0Fo1fkoSScWKHBhWfk%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "true", IsContentURLRequired "true"
    And SlotType "trailer" ,CorrelationId "youviewP08_00037"
    And placementId "Search-Rule", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.86.00"
    And X-BTAppVersion "2.1.2556982"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B08500; CDS/20.9.30; API/2.8.1; PS/2.8.18) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play should success with error code as zero

  Scenario: User trying to play the feature whose Device(DRM)/Client Identifiers are missing
    Given Product Id is "BBJ711388180", device token is "f4HGUAPXPHiNOvE69%2F67GoNInIUM8UYk6DZ8%2B4RGr9g%2FbrR7E6W4IFGwpDM9l%2BOytOndLD4YMkThePR2Mp3PcAdTXKQdOpvq4cFV%2B7qeE0qytjMUbpmKk5eCw%2BseTMz2NJOJB4BI%2FJ9OO7mOAiTiAWnOHwMforJYsmzDA9RrlHM%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "true", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP08_00038"
    And placementId "Search-Rule", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.40.13"
    And X-BTAppVersion "2.1.2556982"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B08500; CDS/20.9.30; API/2.8.1; PS/2.8.18) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play should fail with error code "8031" and with error Message as "Invalid clientInfo"

  Scenario: user fails to play a Trailer on a You-View Device as the Device is not Registered
    Given Product Id is "14364229408", device token is "f4HGUAPXPHiNOvE69%2F67Gur%2Ft7Fvcc2dkFmj17kBzEo%2FbrR7E6W4IFGwpDM9l%2BOyGcNSoFB33AEs18jyU8sVTv3ulitZx8lEUneDGoqA6%2B5r7AjGnqX3uyVskj7s0YEkF7mEx7LKttXWkjfE6ctI2PPyg0Fo1fkoSScWKHBhWfk%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "true", IsContentURLRequired "true"
    And SlotType "trailer" ,CorrelationId "youviewP08_00039"
    And placementId "Search-Rule", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.86.00"
    And X-BTAppVersion "2.1.2556982"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B08500; CDS/20.9.30; API/2.8.1; PS/2.8.18) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play should success with error code as zero

  Scenario: Verifying the response from getLicenseWithSubjectInfo Call when user tries to play a content
    Given Product Id is "BBJ711388180", device token is "f4HGUAPXPHiNOvE69%2F67Grp%2B3l4b7BEVLXyoc31aQSQ%2FbrR7E6W4IFGwpDM9l%2BOytOndLD4YMkThePR2Mp3PcAvGi67HKL%2Bb3CbcBeP8XVvmDx2zoMuxd6sW6niK5wXlmKdlTO3ZKFU0S2aGVlBW9mRQkikGuHJbsfRnKl8%2BUnM%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP08_00040"
    And placementId "7ad1c930-cb20-4b21-a72c-f5604b39093a", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.40.55"
    And X-BTAppVersion "2.1.26"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B0; CDS/21.9.0; API/2.9.4; PS/2.9.24) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries for a play
    Then YouviewPlay should log the response from getLicenseWithSubjectInfo Call


  Scenario: User trying to play the feature for which the RightsDivison is not done
    Given Product Id is "BBJ711388188", device token is "f4HGUAPXPHiNOvE69%2F67GrpK5C%2BuZ4MmNsyGu7e%2FoB0%2FbrR7E6W4IFGwpDM9l%2BOyFrKI9j2Oo%2BCwG1W0nLRG%2BBWB%2FNx2lt5ele2MlX7PuOChPAnwQ%2FfyWbjOt2K639MvmKdlTO3ZKFU0S2aGVlBW9mRQkikGuHJbsfRnKl8%2BUnM%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "true", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP08_00041"
    And placementId "Search-Rule", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.40.132"
    And X-BTAppVersion "2.1.2556982"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B08500; CDS/20.9.30; API/2.8.1; PS/2.8.18) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play must fail with error code "8016" and with error Message as "Error In Validating Entitlements"

  Scenario: To Check the error Response when there is no valid schema is present in the request URL
    Given Product Id is "BBJ711388180", device token is "f4HGUAPXPHiNOvE69%2F67Grp%2B3l4b7BEVLXyoc31aQSQ%2FbrR7E6W4IFGwpDM9l%2BOytOndLD4YMkThePR2Mp3PcAvGi67HKL%2Bb3CbcBeP8XVvmDx2zoMuxd6sW6niK5wXlmKdlTO3ZKFU0S2aGVlBW9mRQkikGuHJbsfRnKl8%2BUnM%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "true", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP08_00042"
    And placementId "Search-Rule", schema "2.0", form "null"
     And X-Cluster-Client-IP "60.50.40.55"
    And X-BTAppVersion "2.1.2556982"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B08500; CDS/20.9.30; API/2.8.1; PS/2.8.18) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play should fail with error code "8002" and with error Message as "Invalid Schema Version"
	
	 Scenario: YouviewPlay to return 8022 error to the client when the parsing of deviceToken fails while registering a device
    Given Product Id is "BBJ711388180", device token is "f0HGUAPXPHiNOvE69%2F67Grp%2B3l4b7BEVLXyoc31aQSQ%2FbrR7E6W4IFGwpDM9l%2BOytOndLD4YMkThePR2Mp3PcAvGi67HKL%2Bb3CbcBeP8XVvmDx2zoMuxd6sW6niK5wXlmKdlTO3ZKFU0S2aGVlBW9mRQkikGuHJbsfRnKl8%2BUnM%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP08_001Neg"
    And placementId "7ad1c930-cb20-4b21-a72c-f5604b39093a", schema "1.0", form "json"
    And X-Cluster-Client-IP "60.50.40.55"
    And X-BTAppVersion "2.1.26"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B0; CDS/21.9.0; API/2.9.4; PS/2.9.24) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play should fail with error code "8022" and with an error Message as "Internal Processing Error"
    
    
Scenario: To check the Error Response when a user tries to play some Content for which the Entitlements Data Service Call fails
    Given Product Id is "BBJ711388180", device token is "f4HGUAPXPHiNOvE69%2F67GkzBiSTFh5ME%2FghsnwaSJls%2FbrR7E6W4IFGwpDM9l%2BOyhYTp%2FqINslsT6I7%2FqU0ilUDEhhX78ToQI%2FLRCZqaIcRkkNzEsxS4Em69WzqaFcXG831lEsuWobVmXwbraUMaUydtzayUQ82KMWIDc%2FeulTA%3D"
    And AssetDeliveryType "Streaming", IsLicenseRequired "false", IsContentURLRequired "true"
    And SlotType "feature" ,CorrelationId "youviewP08_002Neg"
    And placementId "Search-Rule", schema "1.0", form "json"
     And X-Cluster-Client-IP "60.50.40.02"
    And X-BTAppVersion "2.1.2556982"
    And ISPProvider "BT"
    And User-Agent "AdobeAIR/2.5 (Humax; DTRT1000; 80B08500; CDS/20.9.30; API/2.8.1; PS/2.8.18) (+DVR+FLASH+HTML+MHEG+IPCMC)"
    When user tries to play the content
    Then play should fail with error code "8012" and with error Message as "Entitlements Data Service Error"
	
	