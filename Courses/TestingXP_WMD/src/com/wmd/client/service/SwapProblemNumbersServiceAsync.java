package com.wmd.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SwapProblemNumbersServiceAsync
{

	void swap(int probId1, int probId2, AsyncCallback<Void> callback);

}
