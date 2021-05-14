package it.gov.pagopa.hubpa.api.privacy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
public class PrivacyController {

    @Autowired
    private PrivacyService privacyService;

    private Logger logger = LoggerFactory.getLogger(PrivacyController.class);

    @ApiOperation(value = "Verifica se un Ref-P ha accettato la privacy", response = BooleanResponseDto.class)
    @GetMapping(value = "/privacy/refp/{codiceFiscaleRefP}")
    public BooleanResponseDto getEnteCreditoreByRefP(@PathVariable("codiceFiscaleRefP") String codiceFiscaleRefP) {
	logger.info("GET privacy");
	long count = privacyService.countByRefP(codiceFiscaleRefP);
	return new BooleanResponseDto(count > 0);
    }

    @PostMapping(value = "privacy/{codiceFiscaleRefP}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @ApiOperation(value = "Crea un record di accettazione provacy", notes = "Crea un record di accettazione provacy", response = BooleanResponseDto.class)
    public BooleanResponseDto createPrivacy(@PathVariable("codiceFiscaleRefP") String codiceFiscaleRefP) {
	PrivacyEntity privacy = new PrivacyEntity();
	privacy.setCodiceFiscaleRefP(codiceFiscaleRefP);
	PrivacyEntity privacyCreated = privacyService.create(privacy);

	return new BooleanResponseDto(privacyCreated != null);
    }

}