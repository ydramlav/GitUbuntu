Feature: YouviewPlay - RegisterRequestToPlay

  Background: 
    Given YouviewPlay host "http://10.101.47.56:50100",

  Scenario: User tries to register a device with a valid BT Token
    Given deviceToken "f4HGUAPXPHiNOvE69%2F67Grp%2B3l4b7BEVLXyoc31aQSQ%2FbrR7E6W4IFGwpDM9l%2BOytOndLD4YMkThePR2Mp3PcAvGi67HKL%2Bb3CbcBeP8XVvmDx2zoMuxd6sW6niK5wXlmKdlTO3ZKFU0S2aGVlBW9mRQkikGuHJbsfRnKl8%2BUnM%3D",
    And ClientIP "60.50.40.55", correlationID "jdfasef_8715123_07june001"
    And Schema "1.0", form "json"
    When user registers for play
    Then play should succeed with error Code zero and with the error Message as "Success"

  Scenario: YouviewPlay to return 9013 error to the client when RRTP request is invoked with an invalid BT Token
    Given deviceToken "f4HGUAPXPHiNOvE69%2F67Gt7eHU%2FaRlTBk1Pq%2FOVSkys%2FbrR7E6W4IFGwpDM9l%2BOytOndLD4YMkThePR2Mp3PcAvGi67HKL%2Bb3CbcBeP8XVtZZsC6repokMrBByQC9rrRv7ekm5%2BZ3YbnogkCDm1TRhVCUKTjDYhOAyybVHLGwjI%3D",
    And ClientIP "60.50.40.23", correlationID "jdfasef_8715123_07june002"
    And Schema "1.0", form "json"
    When user registers for play
    Then play should fail with error code "9013" and with the error Message as "Invalid Device Token"

  Scenario: YouviewPlay to return 9013 error to the client when RRTP request is invoked with an expired BT Token
    Given deviceToken "f4HGUAPXPHiNOvE69%2F67Gt7eHU%2FaRlTBk1Pq%2FOVSkys%2FbrR7E6W4IFGwpDM9l%2BOytOndLD4YMkThePR2Mp3PcAvGi67HKL%2Bb3CbcBeP8XVtZZsC6repokMrBByQC9rrRv7ekm5%2BZ3YbnogkCDm1TRhVCUKTjDYhOAyybVHLGwjI%3D",
    And ClientIP "60.50.40.23", correlationID "jdfasef_8715123_07june003"
    And Schema "1.0", form "json"
    When user registers for play
    Then play should fail with error code "9013" and with the error Message as "Invalid Device Token"

  Scenario: To verify the response log from Common Token Module when user tries to register a Youview device
    Given deviceToken "f4HGUAPXPHiNOvE69%2F67Grp%2B3l4b7BEVLXyoc31aQSQ%2FbrR7E6W4IFGwpDM9l%2BOytOndLD4YMkThePR2Mp3PcAvGi67HKL%2Bb3CbcBeP8XVvmDx2zoMuxd6sW6niK5wXlmKdlTO3ZKFU0S2aGVlBW9mRQkikGuHJbsfRnKl8%2BUnM%3D",
    And ClientIP "60.50.40.55", correlationID "jdfasef_8715123_07june005"
    And Schema "1.0", form "json"
    When user register for play
    Then the youviewPlay should log the response from common token module

  Scenario: YouviewPlay to return 9001 error to the client when RRTP request is invoked with empty client IP.
    Given deviceToken "f4HGUAPXPHiNOvE69%2F67Grp%2B3l4b7BEVLXyoc31aQSQ%2FbrR7E6W4IFGwpDM9l%2BOytOndLD4YMkThePR2Mp3PcAvGi67HKL%2Bb3CbcBeP8XVvmDx2zoMuxd6sW6niK5wXlmKdlTO3ZKFU0S2aGVlBW9mRQkikGuHJbsfRnKl8%2BUnM%3D",
    And ClientIP "", correlationID "jdfasef_8715123_07june007"
    And Schema "1.0", form "json"
    When user registers for play
    Then play should fail with error code "9001" and with the error Message as "BadParameterException:X-Cluster-Client-Ip"

  Scenario: YouviewPlay to return 9002 error to the client when RRTP request is invoked with schema as null.
    Given deviceToken "f4HGUAPXPHiNOvE69%2F67Grp%2B3l4b7BEVLXyoc31aQSQ%2FbrR7E6W4IFGwpDM9l%2BOytOndLD4YMkThePR2Mp3PcAvGi67HKL%2Bb3CbcBeP8XVvmDx2zoMuxd6sW6niK5wXlmKdlTO3ZKFU0S2aGVlBW9mRQkikGuHJbsfRnKl8%2BUnM%3D",
    And ClientIP "60.50.40.55", correlationID "jdfasef_8715123_07june008"
    And Schema "null", form "json"
    When user registers for play
    Then play should fail with error code "9002" and with the error Message as "Invalid Schema Version"

  Scenario: YouviewPlay to return 9004 error to the client when RRTP request is invoked with invalid form.
    Given deviceToken "f4HGUAPXPHiNOvE69%2F67Grp%2B3l4b7BEVLXyoc31aQSQ%2FbrR7E6W4IFGwpDM9l%2BOytOndLD4YMkThePR2Mp3PcAvGi67HKL%2Bb3CbcBeP8XVvmDx2zoMuxd6sW6niK5wXlmKdlTO3ZKFU0S2aGVlBW9mRQkikGuHJbsfRnKl8%2BUnM%3D",
    And ClientIP "60.50.40.55", correlationID "jdfasef_8715123_07june009"
    And Schema "1.0", form "xmj"
    When user registers for play
    Then play should fail with error code "9004" and with the error Message as "Invalid Form Parameter"

  Scenario: YouviewPlay to return an error "8008" to the client when the user who is not in ACTIVE state, reset his box.
    Given deviceToken "f4HGUAPXPHiNOvE69%2F67GvlJ%2F6HEtlfJRcDI4bh6v%2Bg%2FbrR7E6W4IFGwpDM9l%2BOytOndLD4YMkThePR2Mp3PcAbPf6p4d5Ftwo6lt2YP6CtzqqUCcVYnZVksqS%2FHXDbmmKdlTO3ZKFU0S2aGVlBW9mRQkikGuHJbsfRnKl8%2BUnM%3D",
    And ClientIP "60.50.40.12", correlationID "jdfasef_8715123_07june012"
    And Schema "1.0", form "json"
    When user registers for play
    Then play should succeed with error Code "8008" and with error Message as "Account not in Active State"
  Scenario: YouviewPlay to return 8022 error to the client when the parsing of deviceToken fails while registering a device
    Given deviceToken "f0HGUAPXPHiNOvE69%2F67Grp%2B3l4b7BEVLXyoc31aQSQ%2FbrR7E6W4IFGwpDM9l%2BOytOndLD4YMkThePR2Mp3PcAvGi67HKL%2Bb3CbcBeP8XVvmDx2zoMuxd6sW6niK5wXlmKdlTO3ZKFU0S2aGVlBW9mRQkikGuHJbsfRnKl8%2BUnM%3D",
    And ClientIP "60.50.40.55", correlationID "jdfasef_8715123_07june004"
    And Schema "1.0", form "json"
    When user registers for play
    Then play should fail with error code "8022" and with the error Message as "Internal Processing Error"



	
	
	