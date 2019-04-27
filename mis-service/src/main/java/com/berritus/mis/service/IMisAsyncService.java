package com.berritus.mis.service;

import java.util.concurrent.Future;

public interface IMisAsyncService {
    Future<String> getActionData();

    Future<String> getActionData2();
}
