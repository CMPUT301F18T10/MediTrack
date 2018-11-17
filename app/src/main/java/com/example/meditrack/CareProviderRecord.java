package com.example.meditrack;

import com.example.meditrack.AbstractRecord;

import java.util.Date;

public class CareProviderRecord extends AbstractRecord implements ElasticsearchStorable{

    private String careProviderComment;
    private String careProviderID;

    public CareProviderRecord(String problemId, String careProviderComment, String careProviderID) {
        super(problemId);
        this.careProviderComment = careProviderComment;
        this.careProviderID = careProviderID;
    }

    public String getCareProviderComment() {
        return careProviderComment;
    }

    public String getCareProviderID() {
        return careProviderID;
    }

    @Override
    public String getElasticsearchType(){
        return "care_provider_records";
    }

    @Override
    public String getId() {
        return super.getId();
    }
}
