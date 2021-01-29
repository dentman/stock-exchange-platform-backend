package com.codecool.stockexchange.apimodel.video;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VideoItems {

	@JsonProperty("kind")
	private String kind;

	@JsonProperty("etag")
	private String etag;

	@JsonProperty("id")
	private Id id;

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getKind() {
		return kind;
	}

	public void setEtag(String etag) {
		this.etag = etag;
	}

	public String getEtag() {
		return etag;
	}

	public void setId(Id id) {
		this.id = id;
	}

	public Id getId() {
		return id;
	}

	@Override
	public String toString() {
		return "ItemsItem{" + "kind = '" + kind + '\'' + ",etag = '" + etag + '\'' + ",id = '" + id + '\'' + "}";
	}
}