package it.gov.pagopa.hubpa.support.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.client.RestTemplate;

import it.gov.pagopa.hubpa.support.SupportApplication;
import it.gov.pagopa.hubpa.support.config.DevCorsConfiguration;
import it.gov.pagopa.hubpa.support.config.MappingsConfiguration;
import it.gov.pagopa.hubpa.support.entity.Support;
import it.gov.pagopa.hubpa.support.mock.SupportMock;
import it.gov.pagopa.hubpa.support.mock.SupportModelMock;
import it.gov.pagopa.hubpa.support.model.BooleanResponseModel;
import it.gov.pagopa.hubpa.support.model.SupportModel;
import it.gov.pagopa.hubpa.support.service.SupportService;
import springfox.documentation.spring.web.plugins.Docket;

@ExtendWith(MockitoExtension.class)
class SupportControllerTest {
    @InjectMocks
    SupportController supportController;

    @Mock
    SupportService supportService;

    @Mock
    private ModelMapper modelMapperMock;

    @Test
    void saveServiceTest(){
        SupportModel supportModel = SupportModelMock.getMock();
        Support support = SupportMock.getMock();
        when(supportService.save(any(Support.class))).thenReturn(Boolean.TRUE);
        when(modelMapperMock.map(any(SupportModel.class), any())).thenReturn(support);
        BooleanResponseModel response = supportController.saveService(supportModel);
        assertThat(response.getResult()).isTrue();
    }

    @Test
    void mapperTest() {
        MappingsConfiguration mm = new MappingsConfiguration();
        assertThatNoException().isThrownBy(mm::modelMapper);
    }

    @Test
    void applicationTest() {
        SupportApplication mm = new SupportApplication();
        Docket api = mm.api();
        assertThat(api).isNotNull();
    }

    @Test
    void devCorsTest() {
        DevCorsConfiguration mm = new DevCorsConfiguration();
        RestTemplate restTemplate = mm.restTemplate();
        assertThat(restTemplate).isNotNull();
    }

    @Test
    void getSupport() {
        SupportModel support = SupportModelMock.getMock();

        assertThat(support.getFiscalCodeRp()).isNotNull();
        assertThat(support.getMunicipality()).isNotNull();
        assertThat(support.getRegion()).isNotNull();
        assertThat(support.getProvince()).isNotNull();
        assertThat(support.getCertifiedMail()).isNotNull();
        assertThat(support.getTelephoneMunicipality()).isNotNull();
        assertThat(support.getFullName()).isNotNull();
        assertThat(support.getEmail()).isNotNull();
        assertThat(support.getTelephone()).isNotNull();
        assertThat(support.getTypeContact()).isNotNull();
        assertThat(support.getPlatformCall()).isNotNull();
        assertThat(support.getDateRequest()).isNotNull();
        assertThat(support.getTimeRequest()).isNotNull();
    }
}
