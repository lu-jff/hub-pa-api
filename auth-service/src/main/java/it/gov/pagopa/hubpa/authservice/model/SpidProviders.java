package it.gov.pagopa.hubpa.authservice.model;

import java.util.List;

import it.gov.pagopa.hubpa.spid.integration.model.IdpEntry;

public class SpidProviders {

	private List<IdpEntry> identityProviders;
	private List<ExtraInfo> extraInfo;

	public List<IdpEntry> getIdentityProviders() {
		return identityProviders;
	}
	public void setIdentityProviders(final List<IdpEntry> identityProviders) {
		this.identityProviders = identityProviders;
	}
	public List<ExtraInfo> getExtraInfo() {
		return extraInfo;
	}
	public void setExtraInfo(final List<ExtraInfo> extraInfo) {
		this.extraInfo = extraInfo;
	}
}
