package com.tavi.tavi_mrs.entities.momo.shared.sharedmodels;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.tavi.tavi_mrs.entities.momo.shared.exception.MoMoException;
import com.tavi.tavi_mrs.entities.momo.shared.utils.Execute;


public abstract class AbstractProcess<T, V> {

    protected PartnerInfo partnerInfo;
    protected Environment environment;
    protected Execute execute = new Execute();

    public AbstractProcess(Environment environment) {
        this.environment = environment;
        this.partnerInfo = environment.getPartnerInfo();
    }

    public static Gson getGson() {
        return new GsonBuilder()
                .disableHtmlEscaping()
                .create();
    }

    public static void errorMoMoProcess(int errorCode) throws MoMoException {

        switch (errorCode) {
            case 0:
                // O is meaning success
                break;
            case 1:
                throw new MoMoException("Empty access key or partner code");
        }
    }

    public abstract V execute(T request) throws MoMoException;
}
